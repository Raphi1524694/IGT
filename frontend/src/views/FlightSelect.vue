<template>
  <div>
    <v-stepper v-model="mode" centered>
      <v-stepper-header>
        <v-stepper-step :complete="mode > 1" step="1">Outbound Flight</v-stepper-step>
        <v-divider></v-divider>
        <v-stepper-step :complete="mode > 2" step="2">Return Flight</v-stepper-step>
        <v-divider></v-divider>
        <v-stepper-step step="3">Overview</v-stepper-step>
      </v-stepper-header>
      <v-layout justify-center v-if="mode < 3">
        <v-flex xs5 ma-2>
          <v-menu
            :close-on-content-click="false"
            v-model="picker"
            :nudge-right="40"
            offset-y
            full-width
            min-width="290px"
          >
            <v-text-field
              slot="activator"
              v-model="date"
              :label="`Select ${mode === '1'? 'departure': 'return'} date`"
              prepend-icon="event"
              readonly
            ></v-text-field>
            <v-date-picker v-model="date" @input="picker = false"></v-date-picker>
          </v-menu>
        </v-flex>
      </v-layout>
      <v-stepper-items>
        <v-stepper-content step="1">
          <h3 v-if="$store.getters.inboundFlight.length > 0">Select return flight</h3>
          <span v-else-if="date && !$store.getters.loading">no flights found</span>
          <span v-else-if="!$store.getters.loading">select date</span>
          <v-progress-circular indeterminate color="primary" v-if="$store.getters.loading"></v-progress-circular>
          <v-layout justify-center row wrap id="list">
            <v-flex v-for="flight in $store.getters.inboundFlight" :key="flight.flightId" ma-2>
              <flight :flight="flight" @selected="choose(0, $event)"/>
            </v-flex>
          </v-layout>
        </v-stepper-content>
        <v-stepper-content step="2">
          <h3 v-if="$store.getters.inboundFlight.length > 0">Select return flight</h3>
          <span v-else-if="date && !$store.getters.loading">no flights found</span>
          <span v-else-if="!$store.getters.loading">select date</span>
          <v-layout justify-center row wrap id="list">
            <v-flex v-for="flight in $store.getters.returnFlight" :key="flight.flightId" ma-2>
              <flight :flight="flight" @selected="choose(1, $event)"/>
            </v-flex>
          </v-layout>
          <v-progress-circular indeterminate color="primary" v-if="$store.getters.loading"></v-progress-circular>
        </v-stepper-content>
        <v-stepper-content step="3">
          <v-card flat>
            <v-card-text>Overview</v-card-text>
            <v-layout row wrap id="list">
              <v-flex>
                <div class="fat">
                  <b v-if="selected[0]">Outbound flight:</b>
                </div>
                <flight :flight="selected[0]" text="Remove" @selected="mode = '1'"/>
                <div class="fat">
                  <b v-if="selected[1]">Return flight:</b>
                </div>
                <flight :flight="selected[1]" text="Remove" @selected="mode = '2'"/>
                <br>
                <v-btn color="primary" @click="bookFlight()">Book now</v-btn>
                <br>
              </v-flex>
            </v-layout>
          </v-card>
        </v-stepper-content>
      </v-stepper-items>
    </v-stepper>
  </div>
</template>

<script>
import Flight from "./FlightCard.vue";

export default {
  name: "FlightSelect",
  props: {
    startAirport: Number,
    goalAirport: Number
  },
  components: {
    Flight
  },
  data: () => ({
    date: new Date().toISOString().substr(0, 10),
    mode: undefined,
    picker: false,
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
    },
    async bookFlight() {
      if(!this.$store.getters.getUser){
        this.$store.commit("error", 'No user selected');
      }
      console.table(this.selected);
      this.selected.forEach(flight => {
        console.log(flight.flightId);
        this.$store.dispatch("bookFlight", {
          customerId: this.$store.getters.getUser.customerId,
          flightId: flight.flightId
        });
      });
      await this.$store.dispatch("allCustomers");
      this.$router.push({ path: 'user' });
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
      const action = this.mode === "1" ? "getInboundFlight" : "getReturnFlight";
      this.$store.dispatch(action, {
        date: this.date,
        range: 1,
        startAirport: this.startAirport,
        arrivalAirport: this.goalAirport
      });
    },
    date() {
      if (this.mode === "1") {
        this.startDate = this.date;
      } else {
        this.arrivalDate = this.date;
      }
    },
    mode() {
      if (this.mode === "1") {
        this.date = this.startDate;
      } else {
        this.date = this.arrivalDate;
      }
    }
  }
};
</script>

<style lang="scss" scoped>
.fat {
  width: 90%;
  text-align: left;
  font-size: 150%;
  margin: 30px 10px 5px;
}
</style>
