export default {
  setUser(state, user) {
    console.log("set to user " + user.customerId);
    state.user = user;
  },
  allUsers(state, users) {
    state.allUsers = users;
  },
  setPort(state, port) {
    state.Port = port;
  },
  flightsInRange(state, flights) {
    state.flightsInRange = flights;
  },
  bookings(state, book) {
    state.bookings = book;
  },
  allAirports(state, ports) {
    state.airports = ports;
  },

};
