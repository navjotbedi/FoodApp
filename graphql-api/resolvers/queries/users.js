module.exports = async (_, args, {models}) => {
    if(args.id) return models.User.find({"_id": args.id});
    else return models.User.find();
}