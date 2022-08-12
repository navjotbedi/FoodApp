module.exports = async (_, {id}, {models}) => {
    const deleteFood = await models.Food.deleteOne({_id: id});

    if(deleteFood.deletedCount) return{id: id}
}