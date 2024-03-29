const mutations = require('./mutations');
const queries = require('./queries');
const {mongoose} = require("mongoose");
const {Food} = require('../models/food');
const {User} = require('../models/user');
const avgCaloriesPerUser = require("./queries/avgCaloriesPerUser");

module.exports = {
    Mutation: {
        ...mutations
    },
    Query: {
        ...queries
    },
    Food: {
        async user(parent) {
            return User.findById(parent.user);
        }
    },
    User: {
        async foods(parent) {
            return Food.find({user: parent._id});
        }
    },
    AvgCaloriesPerUser: {
        async user(parent) {
            return User.findById(parent.user._id);
        }
    }
}