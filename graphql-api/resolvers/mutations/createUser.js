module.exports = async (_, {input}, {models}) => {
    return models.User.create(input);
}