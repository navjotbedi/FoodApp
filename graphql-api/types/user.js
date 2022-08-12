const {gql} = require("apollo-server");

module.exports = gql`
    enum Role {
        ADMIN
        USER
    }

    type User {
        id: ID!
        name: String!
        role: Role!
        foods: [Food]
    }

    type Query {
        users(id: ID): [User]
    }

    input CreateUserInput {
        name: String!
        role: Role!
    }

    input UpdateUserInput {
        name: String
        role: Role
    }

    type DeleteUser {
        id: ID!
    }
    
    type Login {
        token: String!
        user: User!
    }

    type Mutation {
        createUser(input: CreateUserInput!): User!
        updateUser(id: ID, input: UpdateUserInput!): User!
        deleteUser(id: ID): DeleteUser!
        login(id: ID!): Login!
    }
`;