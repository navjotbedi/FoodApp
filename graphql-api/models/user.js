const mongoose = require("mongoose");
const {Schema} = mongoose;

const userSchema = new Schema({
    name: {
        type: String,
        trim: true
    },
    role: {
        type: String,
        enum: ['USER', 'ADMIN'],
        default: 'ADMIN'
    },
    foods: [{
        type: mongoose.Schema.Types.ObjectId,
        ref: "Food"
    }]
});

const User = mongoose.model("User", userSchema);
module.exports = {User};