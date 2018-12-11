<template>
  <div>
    <v-card elevation-6 v-if="flight">
      <br>
      <v-layout row justify-space-around px-4 class="header">
        <v-icon class="arrow">flight_takeoff</v-icon>
        <template v-for="(airport, index) in airports">
          <v-flex :key="airport.airportId * 100">
            <v-icon v-if="index > 0 && index < airports.length" class="arrow">keyboard_arrow_right</v-icon>
          </v-flex>
          <v-flex :key="airport.airportId">{{airport.name}} ({{airport.short}})</v-flex>
        </template>
        <v-icon class="arrow">flight_land</v-icon>
      </v-layout>
      <br>
      <v-layout row break id="info">
        <v-flex xs12 sm6 md4>
          <b>Start:</b>
          {{flight.time}}
        </v-flex>
        <v-flex xs12 sm6 md4>
          <b>Duration:</b>
          {{duration}}
        </v-flex>
        <v-flex xs12 sm6 md4>
          <b>Arrival:</b>
          {{arrival}}
        </v-flex>
      </v-layout>
      <v-layout row>
        <v-flex xs12 sm6 md4>
          <b>Miles:</b>
          {{flight.miles}}
        </v-flex>
        <v-flex xs12 sm6 md4>
          <b>Seats First Class:</b>
          {{flight.seats.first}}
        </v-flex>
        <v-flex xs12 sm6 md4>
          <b>Seats Economy Class:</b>
          {{flight.seats.economy}}
        </v-flex>
      </v-layout>
      <br>
      <v-layout row>
        <v-flex xs6>
          <h3>
            Prices First Class:
            {{flight.prices.first}}$
          </h3>
        </v-flex>
        <v-flex xs6>
          <h3>
            Prices Economy Class:
            {{flight.prices.economy}}$
          </h3>
        </v-flex>
      </v-layout>
      <br>
      <v-btn flat color="orange" @click="$emit('selected', flight)">{{text || 'Select'}}</v-btn>
      <br>
    </v-card>
  </div>
</template>

<script>
import moment from "moment";

export default {
  props: {
    flight: Object,
    text: String
  },
  mounted() {
    moment.locale("de");
  },
  computed: {
    airports() {
      const all = this.$store.getters.airports;
      return this.flight.airportsList.map(id =>
        all.find(airport => airport.airportId === id)
      );
    },
    arrival() {
      return moment()
        .hour(this.flight.time.slice(0, 2))
        .minutes(this.flight.time.slice(3))
        .add(this.flight.duration, "m")
        .format("LT");
    },
    duration() {
      const duration = moment.duration(this.flight.duration, "minutes");
      return duration.hours() + "h, " + duration.minutes() + "m";
    }
  }
};
</script>

<style lang="scss" scoped>
.header {
  font-size: 115%;
}
*{
  color: #000000ea;
  b {
    color: #000000b9;
  }
}
#info {
  background: #adadad38;
  padding: 8px 0px;
  border-radius: 8px;
  margin: 5px;
}
</style>
