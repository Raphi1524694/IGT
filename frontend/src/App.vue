<template>
  <div id="app">
    <div id="nav">
      <v-toolbar id="toolbar">
        <v-toolbar-title>Flightmanagement</v-toolbar-title>
        <v-spacer></v-spacer>
        <v-toolbar-items class="hidden-sm-and-down">
          <v-overflow-btn :items="dropdown_icon" target="#toolbar" label="Datenbank"></v-overflow-btn>
        </v-toolbar-items>
      </v-toolbar>
    </div>

    <router-view />
    <br>
    
    <v-footer height="auto" color="primary lighten-1" id="footer">
      <v-layout justify-center row wrap>
        <v-btn color="gray" flat round to="/flights"> Flüge </v-btn>
        <v-btn color="gray" flat round to="/user"> User </v-btn>
        <v-flex primary lighten-3 py-3 text-xs-center xs12>
          &copy;2018 — <strong>Raphi + Philip</strong>
        </v-flex>
      </v-layout>
    </v-footer>
  </div>
</template>

<script>
export default {
  data: () => {
    return {
      dropdown_icon: [
        { text: "MongoDB", callback: () => console.log("list") },
        { text: "MySQL", callback: () => console.log("favorite") },
        { text: "delete", callback: () => console.log("delete") }
      ]
    };
  },
  // mounted() { },
  methods: {}
};
</script>

<style lang="scss">
#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
#footer > * {
  color: darkgray !important;
}
</style>
