export default {
    setUser(state, user) {
        console.log("set to user " + user.customerId);
        state.user = user;
    },
    allUsers(state, users) {
        console.log(`got ${users.length} users`);
        state.allUsers = users;
    }
}