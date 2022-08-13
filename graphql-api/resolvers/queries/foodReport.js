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

    let avgCalorie = await models.Food.aggregate([
        {
            $match:
                {
                    "intakeDate": {$gte: new Date(currentDate - (weekDays * 24 * 60 * 60 * 1000))}
                }
        },
        {
            $group: {
                _id: "$user",
                avg_cal: {$avg: "$calorie"}
            }
        }])

    let avgCaloriePerDay = await models.Food.aggregate([
        {
            $match:
                {
                    $expr: {
                        $gt: ["$intakeDate", {
                            $dateSubtract:
                                {
                                    startDate: "$$NOW",
                                    unit: "week",
                                    amount: 1
                                }
                        }
                        ]
                    }
                }
        },
        {
            $group: {
                _id: {
                    $dateToString: {
                        date: "$intakeDate",
                        format: "%Y-%m-%d"
                    }
                },
                avg_cal: {$avg: "$calorie"}
            }
        }
    ])

    let z = await models.Food.find()

    return {currentWeek: currentWeek.length, lastWeek: lastWeek.length};
}
