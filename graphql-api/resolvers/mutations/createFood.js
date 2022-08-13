module.exports = async (_, {userId, input}, {models, token}) => {
    var userToken = token;
    if(userId) { userToken = userId }
    const user = await models.User.findById(userToken);

    if(user) {
        input.user = user;
        const food = await models.Food.create(input);
        user.foods.push(food)
        await user.save();

        return food
    }

}