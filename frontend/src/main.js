import Vue from "vue";
import App from "./App.vue";
import store from "./store";
import router from "./router";

import "vuetify/src/stylus/app.styl";
import "material-design-icons-iconfont/dist/material-design-icons.css";
import colors from "vuetify/es5/util/colors";

import Vuetify from "vuetify/lib";
import * as Components from "vuetify/lib";

Vue.config.productionTip = false;

new Vue({
  store,
  router,
  render: h => h(App)
}).$mount("#app");

Vue.use(Vuetify, {
  theme: {
    primary: colors.red.darken1, // #E53935
    secondary: colors.red.lighten4, // #FFCDD2
    accent: colors.indigo.base // #3F51B5
  },
  components: { ...Components }
});
