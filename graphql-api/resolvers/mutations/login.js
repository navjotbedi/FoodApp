module.exports = async (_, {id}, {models}) => {
    try {
        const user = await models.User.findById(id);
        return { token: user.id, user: user };
    } catch (e) {
        throw new Error("Unable to login")
    }
}