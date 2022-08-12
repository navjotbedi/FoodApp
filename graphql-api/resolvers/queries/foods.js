module.exports = async (_, {}, {models}) => {
    return models.Food.find();
}
