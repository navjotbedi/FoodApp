const mongoose = require("mongoose");
const { Schema } = mongoose;

const foodSchema = new Schema({
    name: {
        type: String,
        trim: true
    },
    calorie: Number,
    intakeDate: {
        type: Date,
        default: Date.now
    },
    user: {
        type:mongoose.Schema.Types.ObjectId,
        ref:'User'
    }
});

const Food = mongoose.model("Food", foodSchema);
module.exports = { Food };