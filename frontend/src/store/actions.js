import Axios from "axios";

export const createCustomer = async ({ getters, commit }, userConfig) => {
    commit("setUser", (await Axios.post(getters.getURL + "/customer/new", userConfig)).data);
}
export const deleteCustomer = async ({ dispatch, getters }, customerId) => {
    const response = await Axios.delete(getters.getURL + "/customer/" + customerId);
    console.log(response.data.message);
    dispatch("allCustomers");
}
export const setCustomer = async ({ commit, getters }, userId) => {
    commit("setUser", (await Axios.get(getters.getURL + "/customer/" + userId)).data);
}
export const updateCustomer = async ({ commit, getters }, userConfig) => {
    commit("setUser", (await Axios.post(getters.getURL + "/customer/" + userConfig.customerId, userConfig)).data);
}
export const allCustomers = async ({ commit, getters }) => {
    commit("allUsers", (await Axios.get(getters.getURL + `/customer/all`)).data);
}