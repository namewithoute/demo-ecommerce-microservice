const redis = require('redis');
const client = redis.createClient({
    url: "redis://:wKtkGUaPQE3H2Bli1PlEreRf21FKY8HA@redis-16890.c1.ap-southeast-1-1.ec2.cloud.redislabs.com:16890"

});
client.connect().catch(console.error)

client.on('error', err => {
    console.log('Error ' + err);
});

module.exports= client
