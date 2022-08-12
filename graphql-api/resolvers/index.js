const mutations = require('./mutations');
const queries = require('./queries');
const {model} = require("mongoose");
const {Food} = require('../models/food');
const {User} = require('../models/user');

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
    }
}