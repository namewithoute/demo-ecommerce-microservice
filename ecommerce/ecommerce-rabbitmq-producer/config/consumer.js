const amqplib = require('amqplib')
const amqp_URL='amqps://yhlzjzcp:Oj-E_DYc9xJIK389PJZbHyPM7wyRyxR_@armadillo.rmq.cloudamqp.com/yhlzjzcp'


const receive=async function (nameQueue,cb){
    const conn = await amqplib.connect(amqp_URL)
    //create channel
    const channel = await conn.createChannel()
    //create queue name
    await channel.assertQueue(nameQueue, {
        durable: false, //tinh ben bi
    })
    await channel.consume(nameQueue,(msg)=>{
        var temp = JSON.parse(msg.content.toString());
        cb(temp)
    },{noAck:true})

}

module.exports=receive