<template>
  <div>
    <v-card>
      <v-layout row wrap>
        <v-flex xs12 sm6>
          <v-menu>
            <v-toolbar-title slot="activator">
              <span v-if="startAirport">{{ startAirport.name }} ({{startAirport.short}})</span>
              <span v-else>Select your origin</span>
              <v-icon>arrow_drop_down</v-icon>
            </v-toolbar-title>
            <v-list>
              <v-list-tile v-for="item in $store.getters.airports" :key="item.airportId" @click="startAirport = item">
                <v-list-tile-title>{{item.name}} ({{item.short}})</v-list-tile-title>
              </v-list-tile>
            </v-list>
          </v-menu>
        </v-flex>
        <v-flex xs12 sm6>
          <v-menu>
            <v-toolbar-title slot="activator">
              <span v-if="goalAirport">{{ goalAirport.name }} ({{goalAirport.short}})</span>
              <span v-else>Select your goal</span>
              <v-icon>arrow_drop_down</v-icon>
            </v-toolbar-title>
            <v-list>
              <v-list-tile v-for="item in $store.getters.airports" :key="item.airportId" @click="goalAirport = item">
                <v-list-tile-title>{{item.name}} ({{item.short}})</v-list-tile-title>
              </v-list-tile>
            </v-list>
          </v-menu>
        </v-flex>
        <v-flex xs12 sm6>
          <v-menu :close-on-content-click="false" v-model="picker1" :nudge-right="40" lazy transition="scale-transition" offset-y full-width min-width="290px">
              <v-text-field slot="activator" v-model="startDate" label="Select arrival date" prepend-icon="event" readonly></v-text-field>
            <v-date-picker v-model="startDate" @input="picker1 = false"></v-date-picker>
          </v-menu>
        </v-flex>
        <v-flex xs12 sm6>
          <v-menu :close-on-content-click="false" v-model="picker2" :nudge-right="40" lazy transition="scale-transition" offset-y full-width min-width="290px">
            <v-text-field slot="activator" v-model="arrivalDate" label="Select arrival date" prepend-icon="event" readonly ></v-text-field>
            <v-date-picker v-model="arrivalDate" @input="picker1 = false"></v-date-picker>
          </v-menu>
        </v-flex>
      </v-layout>
    </v-card>
    <v-layout justify-center row wrap id="list">
      <v-flex xs12 sm8 md6 my-3 mx-1 v-for="flight in $store.getters.flightsInRange" :key="flight.flightId">
        <flight v-bind="flight" />
      </v-flex>
    </v-layout>
    <create-flight id="createFlightDial"/>
  </div>
</template>

<script>
import Flight from "./FlightCard.vue";
import CreateFlight from "./CreateFlight.vue";

export default {
  name: "Flights",
  components: {
    Flight,
    CreateFlight
  },
  data: () => ({
    startAirport: "",
    goalAirport: "",
    startDate: new Date().toISOString().substr(0, 10),
    arrivalDate: new Date().toISOString().substr(0, 10),
    picker1: false,
    picker2: false
  }),
  methods: {},
  computed: {
    getFlight() {
      return [
        this.startDate,
        this.arrivalDate,
        this.startAirport,
        this.goalAirport
      ].join();
    }
  },
  watch: {
    getFlight() {
      if (
        !this.startDate ||
        !this.arrivalDate ||
        !this.startAirport ||
        !this.goalAirport
      ) {
        return;
      }
      this.$store.dispatch("getFlightsInRange", {
        startDate: this.startDate,
        arrivalDate: this.arrivalDate,
        range: 1,
        startAirport: this.startAirport.airportId,
        arrivalAirport: this.goalAirport.airportId
      });
    }
  }
};
</script>

<style lang="scss" scoped>
.v-menu {
  display: block;
  margin: 5px 12% !important;
}
#list {
  margin-bottom: 90px;
  overflow: auto;
}
#createFlightDial {
  position: fixed;
  right: 20px;
  bottom: 100px;
  z-index: 500;
}
</style>
