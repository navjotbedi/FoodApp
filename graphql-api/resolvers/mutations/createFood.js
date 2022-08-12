module.exports = async (_, {input}, {models, token}) => {
    const user = await models.User.findById(token);

    if(user) {
        input.user = user;
        const food = await models.Food.create(input);
        user.foods.push(food)
        await user.save();

        return food
    }

}