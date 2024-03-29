const {gql} = require("apollo-server");

module.exports = gql`
    type Food{
        id: ID!
        name: String!
        calorie: Int!
        intakeDate: String!
        user: User!
    }

    type Query{
        foods(id: ID): [Food]
        foodReport: FoodReport
        avgCaloriesPerUser: [AvgCaloriesPerUser]
    }

    input CreateFoodInput{
        name: String!
        calorie: Int!
    }

    input UpdateFoodInput{
        name: String
        calorie: Int
    }

    type DeleteFood{
        id: ID!
    }
    
    type FoodReport{
        currentWeek: Int!
        lastWeek: Int!
    }
    
    type AvgCaloriesPerUser{
        avg_cal: Float!
        user: User!
    }

    type Mutation{
        createFood(userId: ID, input: CreateFoodInput!): Food!
        updateFood(id: ID, input: UpdateFoodInput!): Food!
        deleteFood(id: ID): DeleteFood!
    }
`;