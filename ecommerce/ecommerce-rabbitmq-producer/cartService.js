const express = require('express')
const app = express()
const db = require('./config/connectDB')
const cartService =require('./service/cart.consume')


// cartService.updateItemInCart

app.listen(3001,function(){
    console.log('cart service listen at port 3001')
})