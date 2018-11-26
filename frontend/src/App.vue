<template>
  <v-app>
    <div id="app">
      <div id="nav">
        <v-toolbar id="toolbar">
          <v-toolbar-title>Flightmanagement</v-toolbar-title>
          <v-spacer></v-spacer>

          <v-menu :nudge-width="100">
            <v-toolbar-title slot="activator">
              <span>{{ dbSelected }}</span>
              <v-icon>arrow_drop_down</v-icon>
            </v-toolbar-title>

            <v-list>
              <v-list-tile v-for="item in items" :key="item" @click="dbSelected = item">
                <v-list-tile-title v-text="item"></v-list-tile-title>
              </v-list-tile>
            </v-list>
          </v-menu>
        </v-toolbar>
      </div>

      <router-view/>
      <br>

      <v-footer height="auto" id="footer">
        <v-layout justify-center row wrap>
          <v-btn color="gray" flat round to="/flights">Flüge</v-btn>
          <v-btn color="gray" flat round to="/user">User</v-btn>
          <v-flex py-3 text-xs-center xs12>
            &copy;2018 —
            <strong>Raphi + Philip</strong>
          </v-flex>
        </v-layout>
      </v-footer>
    </div>
  </v-app>
</template>

<script>
export default {
  data: () => ({
    dbSelected: "MySQL",
    items: ["MySQL", "MongoDB"]
  }),
  mounted() {
    this.$store.dispatch("allCustomers");
  },
  methods: {}
};
</script>

<style lang="scss" scoped>
#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
#footer {
  color: darkgray !important;
  position: absolute;
  bottom: 0;
  height: 40px;
  margin-top: 40px;
  width: 100%;
}

</style>
