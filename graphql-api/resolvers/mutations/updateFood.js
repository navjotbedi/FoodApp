module.exports = async (_, {id, input}, {models}) => {
    const FoodToUpdate = await models.Food.findOne({_id: id});

    Object.keys(input).forEach(value => {
        FoodToUpdate[value] = input[value];
    });

    return await FoodToUpdate.save();
}