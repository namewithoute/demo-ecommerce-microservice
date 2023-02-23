var mongoose =require('mongoose')

var schema= mongoose.Schema({
    email:String,
    item:[{
        id:String,
        name:String,
        size:String,
        color:String,
        quantity:Number,
        price:Number,
        img:String,
        _id:false,
    }],
    shippingFee:Number,
    total:Number
})

module.exports=mongoose.model('cart',schema)