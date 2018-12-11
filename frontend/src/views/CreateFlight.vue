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
          <v-layout row wrap>
            <v-flex xs12 pl-2 v-for="(airport, index) in airports" :key="airport.airportId">
              <v-autocomplete
                v-model="airportsList[index]"
                :items="$store.getters.airports"
                item-text="name"
                item-value="airportId"
                :label="`Select ${index === 0 ? 'start' : index === airports.length - 1 ? 'goal' : 'layover' }`"
                no-data-text="Airport not found"
                persistent-hint
              />
            </v-flex>
            <v-flex xs12 sm6 md6 pr-2>
              <v-menu
                v-model="menuDate"
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
                  label="Date"
                  prepend-icon="event"
                  readonly
                ></v-text-field>
                <v-date-picker v-model="date" no-title scrollable></v-date-picker>
              </v-menu>
            </v-flex>
            <v-flex xs12 sm6 md6 pl-2>
              <v-menu
                v-model="menuTime"
                :close-on-content-click="false"
                transition="scale-transition"
                offset-y
                full-width
                min-width="290px"
              >
                <v-text-field slot="activator" v-model="time" label="Time" readonly></v-text-field>
                <v-time-picker v-model="time" format="24hr"></v-time-picker>
              </v-menu>
            </v-flex>
            <v-flex xs6 pr-2>
              <v-text-field v-model="miles" type="number" label="Miles"/>
            </v-flex>
            <v-flex xs6 pl-2>
              <v-text-field v-model="duration" type="number" label="Duration (in minutes)"/>
            </v-flex>
            <v-flex xs12 mt-3 mr-2>
              <span>Seats</span>
            </v-flex>
            <v-flex xs6 pr-2>
              <v-text-field v-model="seats.first" type="number" label="first Class"/>
            </v-flex>
            <v-flex xs6 pl-2>
              <v-text-field v-model="seats.economy" type="number" label="Economy Class"/>
            </v-flex>
            <v-flex xs12 mt-3 mr-2>
              <span>Prices</span>
            </v-flex>
            <v-flex xs6 pr-2>
              <v-text-field
                v-model="prices.first"
                label="first Class"
                type="number"
                append-icon="attach_money"
              />
            </v-flex>
            <v-flex xs6 pl-2>
              <v-text-field
                v-model="prices.economy"
                label="Economy Class"
                type="number"
                append-icon="attach_money"
              />
            </v-flex>
          </v-layout>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn flat color="orange" @click="create()">create</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
export default {
  data: () => ({
    dialog: false,
    date: new Date().toISOString().substr(0, 10),
    time: null,
    menuDate: false,
    menuTime: false,
    miles: 100,
    duration: 60,
    airportsList: [],
    seats: {
      first: 50,
      economy: 350
    },
    prices: {
      first: 1500,
      economy: 500
    }
  }),
  methods: {
    create() {
      this.$store.dispatch("newFlight", {
        miles: this.miles,
        date: this.date,
        time: this.time,
        duration: this.duration,
        airportsList: this.airportsList.filter(airport =>
          Number.isInteger(airport)
        ),
        seats: this.seats,
        prices: this.prices
      });
      this.dialog = false;
    }
  },
  computed: {
    airports() {
      if (this.airportsList.length === 0) {
        // eslint-disable-next-line
        this.airportsList.push({ airportId: -1 });
      }
      if (this.airportsList.length > 2) {
        // eslint-disable-next-line
        this.airportsList = this.airportsList.filter(airport =>
          Number.isInteger(airport)
        );
      }
      // eslint-disable-next-line
      const start = this.airportsList.pop();
      // eslint-disable-next-line
      this.airportsList.push({ airportId: -1 });
      // eslint-disable-next-line
      this.airportsList.push(start);
      return this.airportsList;
    }
  }
};
</script>

<style scoped>
</style>
