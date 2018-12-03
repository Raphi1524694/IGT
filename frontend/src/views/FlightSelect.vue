<template>
  <div>
    <v-tabs slot="extension" v-model="mode" centered>
      <v-tab>Outbound Flight</v-tab>
      <v-tab>Return Flight</v-tab>
      <v-tab>Overview</v-tab>
    </v-tabs>
    <v-layout justify-center v-if="mode < 2">
      <v-flex xs5 ma-2>
        <v-menu
          :close-on-content-click="false"
          v-model="picker1"
          :nudge-right="40"
          offset-y
          full-width
          min-width="290px"
        >
          <v-text-field
            slot="activator"
            v-model="date"
            :label="`Select ${mode === 0? 'departure': 'return'} date`"
            prepend-icon="event"
            readonly
          ></v-text-field>
          <v-date-picker v-model="date" @input="picker1 = false"></v-date-picker>
        </v-menu>
      </v-flex>
    </v-layout>
    <v-tabs-items v-model="mode">
      <v-tab-item>
        <v-card flat>
          <v-card-text>Select outbound flight</v-card-text>
          <v-layout justify-center row wrap id="list">
            <v-flex v-for="flight in $store.getters.inboundFlight" :key="flight.flightId" ma-2>
              <flight :flight="flight" @selected="choose(0, $event)"/>
            </v-flex>
          </v-layout>
        </v-card>
      </v-tab-item>
      <v-tab-item>
        <v-card flat>
          <v-card-text>Select return flight</v-card-text>
          <v-layout justify-center row wrap id="list">
            <v-flex v-for="flight in $store.getters.returnFlight" :key="flight.flightId" ma-2>
              <flight :flight="flight" @selected="choose(1, $event)"/>
            </v-flex>
          </v-layout>
        </v-card>
      </v-tab-item>
      <v-tab-item>
        <v-card flat>
          <v-card-text>Overview</v-card-text>
          <v-layout row wrap id="list">
            <v-flex>
              <div class="fat">
                <b v-if="selected[0]">Outbound flight:</b>
              </div>
              <flight :flight="selected[0]" text="Remove" @selected="remove(0)"/>
              <div class="fat">
                <b v-if="selected[1]">Return flight:</b>
              </div>
              <flight :flight="selected[1]" text="Remove" @selected="remove(1)"/>
              <br>
              <v-btn v-if="selected.length === 2" color="primary" @click="book()">Book now</v-btn>
              <br>
            </v-flex>
          </v-layout>
        </v-card>
      </v-tab-item>
    </v-tabs-items>
  </div>
</template>

<script>
import Flight from "./FlightCard.vue";

export default {
  name: "FlightSelect",
  props: {
    startAirport: String,
    goalAirport: String
  },
  components: {
    Flight
  },
  data: () => ({
    date: new Date().toISOString().substr(0, 10),
    mode: undefined,
    picker1: false,
    picker2: false,
    startDate: null,
    arrivalDate: null,
    selected: []
  }),
  methods: {
    choose(idx, flight) {
      this.selected[idx] = flight;
      this.mode++;
    },
    remove(idx) {
      const temp = this.selected[idx];
      this.selected = [];
      this.selected[idx] = temp;
      console.log(this.selected);
    },
    book() {
      this.$store.dispatch("bookFlight", {
        customerId: this.$store.getters.getUser.customerId,
        flightId: this.selected[0].flightId
      });
      this.$store.dispatch("bookFlight", {
        customerId: this.$store.getters.getUser.customerId,
        flightId: this.selected[1].flightId
      });
    }
  },
  computed: {
    getFlight() {
      return [this.date, this.startAirport, this.goalAirport].join();
    }
  },
  watch: {
    getFlight() {
      if (!this.date || !this.startAirport || !this.goalAirport) {
        return;
      }
      this.$store.dispatch(this.mode ? "getReturnFlight" : "getInboundFlight", {
        date: this.mode === 0 ? this.startDate : this.arrivalDate,
        range: 1,
        startAirport: this.startAirport.airportId,
        arrivalAirport: this.goalAirport.airportId
      });
    },
    date() {
      if (this.mode === 0) {
        this.startDate = this.date;
      } else {
        this.arrivalDate = this.date;
      }
    },
    mode() {
      if (this.mode === 0) {
        this.date = this.startDate;
      } else {
        this.date = this.arrivalDate;
      }
    }
  }
};
</script>

<style lang="scss" scoped>
#list {
  margin-bottom: 90px;
  overflow: auto;
}
.fat {
  width: 90%;
  text-align: left;
  font-size: 150%;
  margin: 30px 10px 5px;
}
</style>
