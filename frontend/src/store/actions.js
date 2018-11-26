export default {
    async createCustomer({ getters, commit }, userConfig) {
        commit("setUser", (await Axios.post(getters.getURL + "/customer/new", userConfig)).data);
    },
    async deleteCustomer({ dispatch, getters }, customerId) {
        const response = await Axios.delete(getters.getURL + "/customer/" + customerId);
        console.log(response.data.message);
        dispatch("allCustomers");
    },
    async setCustomer({ commit, getters }, userId) {
        commit("setUser", (await Axios.get(getters.getURL + "/customer/" + userId)).data);
    },
    async updateCustomer({ commit, getters }, userConfig) {
        commit("setUser", (await Axios.post(getters.getURL + "/customer/" + userConfig.customerId, userConfig)).data);
    },
    async allCustomers({ commit, getters }) {
        commit("allUsers", (await Axios.get(getters.getURL + "/customer/all")).data.customers);
    }
}