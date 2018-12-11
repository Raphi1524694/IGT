import Vue from "vue";
import Router from "vue-router";
import Flights from "./views/Flights.vue";
import Users from "./views/Users.vue";
import Store from './store/index.js';

Vue.use(Router);

const router = new Router({
  routes: [
    { path: "*", redirect: "/user" },
    {
      path: "/flights",
      name: "home",
      component: Flights
    },
    {
      path: "/user",
      name: "User",
      component: Users
    }
  ]
});

router.beforeEach((to, _, next)=>{
  if (to.path === "/user") {
    Store.commit("setUser", null);
  }
  next();
})

export default router;