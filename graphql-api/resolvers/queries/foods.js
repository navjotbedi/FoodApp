const mongoose = require("mongoose");
module.exports = async (_, args, {models, token}) => {
    var userId = token;
    if(args.id) { userId = args.id }
    return models.Food.find({user: mongoose.Types.ObjectId(userId)}).sort({intakeDate:-1});
}
