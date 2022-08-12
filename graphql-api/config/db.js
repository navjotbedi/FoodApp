const mongoose = require('mongoose');

const DATABASE_URL = "mongodb+srv://admin:admin@cluster0.dleinrs.mongodb.net/?retryWrites=true&w=majority"; // process.env.DATABASE_URL;

const connectDB = () => {
    return mongoose.connect(DATABASE_URL, {useUnifiedTopology: true, useNewUrlParser: true}, err => {
        if (err){
            console.error('Connection to DB failed');
        } else{
            console.log('Connection to DB was successful');
        }
    })
}

const db = mongoose.connection;

db.on('error', console.error.bind(console, "MongoDB connection failed"));

module.exports = connectDB;