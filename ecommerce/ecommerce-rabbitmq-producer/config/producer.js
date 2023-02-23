const amqplib = require('amqplib')
const amqp_URL='amqps://yhlzjzcp:Oj-E_DYc9xJIK389PJZbHyPM7wyRyxR_@armadillo.rmq.cloudamqp.com/yhlzjzcp'


const sendQueue=async function (nameQueue,msg){
    //create connect
    const conn=await amqplib.connect(amqp_URL)
    //create channel
    const channel = await conn.createChannel()
    //create queue name
    await channel.assertQueue(nameQueue,{
        durable:false, //tinh ben bi
    })
    //create queue
    await channel.sendToQueue(nameQueue,Buffer.from(JSON.stringify(msg)))
    //close connect
  
}

module.exports=sendQueue