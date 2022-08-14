const {ApolloServer} = require('apollo-server');
const connectDB = require('./config/db');
const typeDefs = require('./types');
const models = require('./models');
const resolvers = require('./resolvers');
const {ApolloServerPluginLandingPageLocalDefault} = require('apollo-server-core');

connectDB();

const server = new ApolloServer({
    typeDefs,
    resolvers,
    csrfPrevention: true,
    cache: 'bounded',
    context: ({ req }) => {
        var token;
        if(req.headers.authorization) {
            token = req.headers.authorization.trim().split(/\s+/)[1] || '';
        }
        return { models, token };
    },
    plugins: [
        ApolloServerPluginLandingPageLocalDefault({ embed: true }),
    ]
});

server.listen({port: process.env.PORT || 4000}).then(({url}) => {
    console.log(`Server is ready at ${url}`);
})