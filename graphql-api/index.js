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
        // Note: This example uses the `req` argument to access headers,
        // but the arguments received by `context` vary by integration.
        // This means they vary for Express, Koa, Lambda, etc.
        //
        // To find out the correct arguments for a specific integration,
        // see https://www.apollographql.com/docs/apollo-server/api/apollo-server/#middleware-specific-context-fields

        // Get the user token from the headers.
        var token;
        if(req.headers.authorization) {
            token = req.headers.authorization.trim().split(/\s+/)[1] || '';
            console.log("token -> " + token)
        }
        // Try to retrieve a user with the token
        // const user = getUser(token);

        // Add the user to the context
        return { models, token };
    },
    plugins: [
        ApolloServerPluginLandingPageLocalDefault({ embed: true }),
    ]
});

server.listen({port: process.env.PORT || 4000}).then(({url}) => {
    console.log(`Server is ready at ${url}`);
})