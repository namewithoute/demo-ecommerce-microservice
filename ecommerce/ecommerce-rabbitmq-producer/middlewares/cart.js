var voucher = require('../models/voucher')
var cartModel = require('../models/cart')
var itemModel = require('../models/item')


const amqplib = require('amqplib')
const amqp_URL = 'amqps://yhlzjzcp:Oj-E_DYc9xJIK389PJZbHyPM7wyRyxR_@armadillo.rmq.cloudamqp.com/yhlzjzcp'
const sendToQueue = require('../config/producer')
const receive = require('../config/consumer')

async function cartGET(req, res) {
    var cartUser = await cartModel.findOne({ email: req.data.email })
    if (!cartUser) {
        return res.render('cart', { resData: JSON.stringify([]) })
    }

    var concatData = cartUser.item
    for (var i = 0; i < concatData.length; i++) {
        var getImg = await itemModel.findOne({ id: concatData[i].id })
        concatData[i].img = getImg.img.cover
        concatData[i].price = getImg.price
        concatData[i].name = getImg.name
    }
    var resData
    resData = concatData.map((item) => {
        return { id: item.id, name: item.name, color: item.color, size: item.size, img: item.img, quantity: item.quantity, price: item.price }
    })
    res.render('cart',{resData})
    //  sendToQueue('getCart', { email: req.data.email })
    //  receive('cartData', async (msg) => {
    //     console.log(msg)
    // })
}

async function cartPUT(req, res) {
    console.log(req.body.update)
    sendToQueue('updateCart', { items: req.body.update, email: req.data.email })
    // await cartModel.updateOne({email:req.data.email},{item:req.body.update})
    res.json({ status: 1 })
}

async function cartDeleteItem(req, res) {
    sendToQueue('deleteItemInCart', { email: req.data.email, size: req.query.size, color: req.query.color, id: req.query.id })
    // var itemList=await cartModel.findOne({email:req.data.email})
    // console.log(itemList)
    // var container=[]
    // var {size,color,id}=req.query
    // itemList.item.forEach((item)=>{
    //     if(!(item.id==id && item.color==color && item.size == size)){
    //         container.push(item)
    //     }
    // })
    // console.log(container)
    // await cartModel.updateOne({email:req.data.email},{item:container})
    res.json({ status: 1 })
}

async function cartDeleteAll(req, res) {
    await cartModel.deleteOne({ email: req.data.email })
    res.json({ status: 1 })
}




async function addToCartPOST(req, res) {
    // const conn=await amqplib.connect(amqp_URL)
    // //create channel
    // const channel = await conn.createChannel()
    // //create queue name
    // const nameQueue='addToCart'
    // await channel.assertQueue(nameQueue,{
    //     durable:false, //tinh ben bi
    // })
    var { id, size, color, price, quantity } = req.body.cart
    var msg = { id, size, color, price, quantity, email: req.data.email }
    // console.log(msg)
    sendToQueue('addToCart', msg)
    return res.json({ status: 1 })

    //create queue
    // await channel.sendToQueue(nameQueue,Buffer.from(JSON.stringify(msg)))
    // //close connect
    // await channel.close()
    // await conn.close()


    // var { id, size, color, quantity } = req.body.cart

    // var cartInfo = await cartModel.findOne({ email: req.data.email })
    // if (!cartInfo || cartInfo.item.length == 0) {
    //     await cartModel.findOneAndUpdate({ email: req.data.email }, { item: req.body.cart }, { upsert: true })
    //     return res.json({ status: 1 })
    // }
    // var isSame=0
    // for(var i =0;i<cartInfo.item.length;i++){
    //     if(cartInfo.item[i].id==id && cartInfo.item[i].color==color && cartInfo.item[i].size==size){
    //         cartInfo.item[i].quantity+=quantity
    //         isSame+=1
    //     }
    // }
    // if(isSame!=0){
    //     await cartModel.updateOne({ email: req.data.email }, { item: cartInfo.item}, { upsert: true })
    //     return res.json({status:1})
    // }
    // console.log(req.body.cart)
    // await cartModel.updateOne({email:req.data.email},{$push:{item:req.body.cart}})

}


async function checkPromoCode(req, res) {
    console.log(req.params.code)
    var promocode = await voucher.findOne({ voucherCode: req.params.code })
    if (!promocode) {
        return res.json({ status: 0 })
    }
    var startTimeStamp = new Date(promocode.startAt).getTime()
    var endTimeStamp = new Date(promocode.expireDate).getTime
    var now = new Date().getTime()
    console.log(now)
    if (now < startTimeStamp || now > endTimeStamp) {
        return res.json({ status: 0 })
    }
    if (promocode.quantity <= 0) {
        return res.json({ status: 0 })
    }
    res.json({ status: 1, discountvalue: promocode.discount })

}


module.exports = {
    cartGET,
    checkPromoCode, addToCartPOST, cartPUT, cartDeleteItem, cartDeleteAll
}