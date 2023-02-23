const amqplib = require('amqplib')
const amqp_URL = 'amqps://yhlzjzcp:Oj-E_DYc9xJIK389PJZbHyPM7wyRyxR_@armadillo.rmq.cloudamqp.com/yhlzjzcp'
const cartModel=require('../models/cart')
const receive=require('../config/consumer');
const producer=require('../config/producer')

async function addToCartQueue(msg){
    var { id, size, color, quantity,email,price } = msg
    var itemList = {
        id,size,color,quantity
    }
    var cartInfo = await cartModel.findOneAndUpdate({ email: email},{upsert:true})
    console.log(cartInfo)
    if (!cartInfo || cartInfo.item.length == 0) {
        await cartModel.findOneAndUpdate({ email: email }, { item: itemList }, { upsert: true })
        return 
    }
    var isSame = 0
    for (var i = 0; i < cartInfo.item.length; i++) {
        if (cartInfo.item[i].id == id && cartInfo.item[i].color == color && cartInfo.item[i].size == size) {
            cartInfo.item[i].quantity += quantity
            isSame += 1
        }
    }
    if (isSame != 0) {
        await cartModel.updateOne({ email: email }, { item: cartInfo.item }, { upsert: true })
        return 
    }
    await cartModel.updateOne({ email: email }, { $push: { item: itemList } })
}
async function updateCart(msg){
    await cartModel.updateOne({email:msg.email},{item:msg.items})
    console.log('done')
}

async function deleteItemInCart(msg){

    var itemList=await cartModel.findOne({email:msg.email})
    console.log(itemList)
    var container=[]
    var {size,color,id}=msg
    itemList.item.forEach((item)=>{
        if(!(item.id==id && item.color==color && item.size == size)){
            container.push(item)
        }
    })
    console.log(container)
    await cartModel.updateOne({email:msg.email},{item:container})
}

async function getItemInCart(msg){
    var res
    var cartUser = await cartModel.findOne({ email: msg.email })
    if (!cartUser) {
        // return res.render('cart', { resData: JSON.stringify([]) })
        res=  {resData: JSON.stringify([])} 
    }
    res=  cartUser.item
    console.log('get data success')
    producer('cartData',res)
    // var concatData = cartUser.item
    // for (var i = 0; i < concatData.length; i++) {
    //     var getImg = await itemModel.findOne({ id: concatData[i].id })
    //     concatData[i].img = getImg.img.cover
    //     concatData[i].price = getImg.price
    //     concatData[i].name = getImg.name
    // }
    // var resData
    // resData = concatData.map((item) => {
    //     // return { id: item.id, name: item.name, color: item.color, size: item.size, img: item.img, quantity: item.quantity, price: item.price }
    //     return
    // })
}


receive('addToCart',addToCartQueue)
receive('updateCart',updateCart)
receive('deleteItemInCart',deleteItemInCart)
receive('getCart',getItemInCart)
// const addToCartQueue = async function () {
//     //create connect
//     const conn = await amqplib.connect(amqp_URL)
//     //create channel
//     const channel = await conn.createChannel()
//     //create queue name
//     await channel.assertQueue('addToCart', {
//         durable: false, //tinh ben bi
//     })
//     //create queue
//     await channel.consume('addToCart', async (msg) => {
//     //     try{
//         var msg = JSON.parse(msg.content.toString());
//         var { id, size, color, quantity,email,price } = msg
//         var itemList = {
//             id,size,color,quantity
//         }
//         var cartInfo = await cartModel.findOne({ email: email})
//         console.log(cartInfo)
//         if (!cartInfo || cartInfo.item.length == 0) {
//             await cartModel.findOneAndUpdate({ email: email }, { item: itemList }, { upsert: true })
//             return 
//         }
//         var isSame = 0
//         for (var i = 0; i < cartInfo.item.length; i++) {
//             if (cartInfo.item[i].id == id && cartInfo.item[i].color == color && cartInfo.item[i].size == size) {
//                 cartInfo.item[i].quantity += quantity
//                 isSame += 1
//             }
//         }
//         if (isSame != 0) {
//             await cartModel.updateOne({ email: email }, { item: cartInfo.item }, { upsert: true })
//             return 
//         }
//         await cartModel.updateOne({ email: email }, { $push: { item: itemList } })
//     // }
//     // catch(e){
//     //     console.log(e)
//     // }
//     console.log(msg)
//     },{noAck:true})
    
// }

// async function updateItemInCart(){
//     const conn = await amqplib.connect(amqp_URL)
//     //create channel
//     const channel = await conn.createChannel()
//     //create queue name
//     await channel.assertQueue('updateCart', {
//         durable: false, //tinh ben bi
//     })
//     await channel.consume('updateCart',async (msg)=>{
//         var data = await JSON.parse(msg.content.toString())
//         var itemInCart = {id:data.id,color:data.color,size:data.size,quantity:data.quantity,price:data.price}
//         await cartModel.updateOne({email:data.email},{item:itemInCart})
//         return
//     },{noAck:true})

// }

