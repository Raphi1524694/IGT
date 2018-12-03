<template>
  <div>
    <v-speed-dial bottom right>
      <v-btn slot="activator" @click="dialog = true" dark fab>
        <v-icon>flight_takeoff</v-icon>
      </v-btn>
    </v-speed-dial>

    <v-dialog v-model="dialog" max-width="500px">
      <v-card>
        <v-card-title class="headline grey lighten-2" primary-title>Create a new Flight</v-card-title>
        <v-card-text>
          <v-text-field v-model="miles" type="number" label="Miles"/>
          <v-container>
            <v-layout row wrap>
              <v-flex xs12 sm6 md6>
                <v-menu
                  ref="menu"
                  v-model="menu"
                  :nudge-right="40"
                  :return-value.sync="date"
                  lazy
                  transition="scale-transition"
                  offset-y
                  full-width
                  min-width="290px"
                >
                  <v-text-field
                    slot="activator"
                    v-model="date"
                    label="Picker in menu"
                    prepend-icon="event"
                    readonly
                  ></v-text-field>
                  <v-date-picker v-model="date" no-title scrollable>
                    <v-spacer></v-spacer>
                    <v-btn flat color="primary" @click="menu = false">Cancel</v-btn>
                    <v-btn flat color="primary" @click="$refs.menu.save(date)">OK</v-btn>
                  </v-date-picker>
                </v-menu>
              </v-flex>

              <v-flex xs12 sm6 md6>
                <v-text-field label="Regular" placeholder="Placeholder"></v-text-field>
              </v-flex>
            </v-layout>
          </v-container>
        </v-card-text>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
export default {
  data: () => ({
    dialog: false,
    date: new Date().toISOString().substr(0, 10),
    menu: false,
    miles: 100
  }),
  computed: {
      ...mapGetters(["airports"])
  }
}
</script>

<style scoped>

</style>
