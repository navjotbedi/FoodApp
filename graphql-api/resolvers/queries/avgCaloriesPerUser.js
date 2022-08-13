module.exports = async (_, {}, {models}) => {
    return models.Food.aggregate([
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
                _id: "$user",
                avg_cal: {$avg: "$calorie"}
            }
        }
        , {
            $project: {
                _id: 0, user: "$_id", avg_cal: 1
            }
        }
    ]);
}
