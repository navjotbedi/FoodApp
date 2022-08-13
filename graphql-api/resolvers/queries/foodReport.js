module.exports = async (_, {}, {models}) => {
    let weekDays = 7;
    let currentDate = new Date().getTime()
    let currentWeek = await models.Food.find({
        "intakeDate":
            {
                $gte: new Date(currentDate - (weekDays * 24 * 60 * 60 * 1000))
            }
    })

    let lastWeek = await models.Food.find({
        "intakeDate":
            {
                $gte: new Date(currentDate - (2 * weekDays * 24 * 60 * 60 * 1000)),
                $lte: new Date(currentDate - (weekDays * 24 * 60 * 60 * 1000))
            }
    })

    let z = await models.Food.find()

    return {currentWeek: currentWeek.length, lastWeek: lastWeek.length};
}
