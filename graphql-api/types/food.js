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
        foods: [Food]
        foodReport: FoodReport
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

    type Mutation{
        createFood(input: CreateFoodInput!): Food!
        updateFood(id: ID, input: UpdateFoodInput!): Food!
        deleteFood(id: ID): DeleteFood!
    }
`;