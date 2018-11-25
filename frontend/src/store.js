//@ts-check
import Vue from "vue";
import Vuex from "vuex";
import Axios from "axios";
Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    user: null,
    allUsers: [],
    URL: "https://3c629d3e-b56f-4767-a394-bd2eed26a93c.mock.pstmn.io/api", // "http://localhost:6000/api",
    Port: 6000
  },
  mutations: {
    setUser(state, user) {
      console.log("set to user " + user.customerId);
      state.user = user;
    },
    allUsers(state, users) {
      console.log(`got ${users.length} users`);
      state.allUsers = users;
    }
  },
  getters: {
    getUser: state => state.user,
    getAllUsers: state => state.allUsers,
    getURL: state => state.URL //+ ':' + state.Port,
  },
  actions: {
    async createCustomer({ getters, commit }, userConfig) {
      commit(
        "setUser",
        (await Axios.post(getters.getURL + "/customer/new", userConfig)).data
      );
    },
    async deleteCustomer({ dispatch, getters }, customerId) {
      const response = await Axios.delete(
        getters.getURL + "/customer/" + customerId
      );
      console.log(response.data.message);
      dispatch("allCustomers");
    },
    async setCustomer({ commit, getters }, userId) {
      commit(
        "setUser",
        (await Axios.get(getters.getURL + "/customer/" + userId)).data
      );
    },
    async updateCustomer({ commit, getters }, userConfig) {
      commit(
        "setUser",
        (await Axios.post(
          getters.getURL + "/customer/" + userConfig.customerId,
          userConfig
        )).data
      );
    },
    async allCustomers({ commit, getters }) {
      commit(
        "allUsers",
        (await Axios.get(getters.getURL + "/customer/all")).data.customers
      );
    }
  }
});
