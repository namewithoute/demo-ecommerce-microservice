const client = require('../config/connectRedis')
const itemModel =require('../models/item')

async function getItemsFromRedisOrDB(skipPage){
    var notIsEmpty = await client.get(`items:${skipPage}`)
    if(notIsEmpty){
        console.log('get from cache')
        return JSON.parse(notIsEmpty)
    }
    var items = await itemModel.find({}).skip(skipPage * 8).limit(8)
    console.log('get from db')
    await client.set(`items:${skipPage}`,JSON.stringify(items))
    return items
}

// async function getAItemInRedis(id){
//     var isEmpty = await client.get(id)
//     if(!isEmpty){
//         return 
//     }
// }

module.exports={getItemsFromRedisOrDB}