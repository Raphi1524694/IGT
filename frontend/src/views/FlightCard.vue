<template>
  <div>
    <v-card elevation-6>
      <br>
      <span style="color:gray;fload:right;">id: {{flightId}}</span><br>
      <v-layout row wrap>
        <v-flex v-for="(airport, index) in airports" :key="airport.airportId">
          <v-icon v-if="index > 0 && index < airports.length" class="arrow">keyboard_arrow_right</v-icon>
          <v-icon v-if="index == 0" class="arrow">flight_takeoff</v-icon>
          {{airport.name}} ({{airport.short}})
          <v-icon v-if="index == airports.length - 1" class="arrow">flight_land</v-icon>
        </v-flex>
      </v-layout>
      <br>
      <v-layout row id="info">
        <v-flex xs12 sm6 md4><b>Start: </b>{{startTime}}</v-flex>
        <v-flex xs12 sm6 md4><b>Miles: </b>{{miles}}</v-flex>
        <v-flex xs12 sm6 md4><b>Arrival: </b>{{arrivalTime}}</v-flex>
      </v-layout>
      <br>
      <v-btn flat color="orange">Book now</v-btn>
      <br>
    </v-card>
  </div>
</template>

<script>
export default {
  props: {
    flightId: Number,
    miles: Number,
    startTime: String,
    arrivalTime: String,
    airportsList: Array
  },
  computed: {
    airports() {
      const all = this.$store.getters.airports;
      return this.airportsList.map(id =>
        all.find(airport => airport.airportId === id)
      );
    }
  }
};
</script>

<style lang="scss" scoped>
.arrow {
  margin-right: 15px;
}
#info {
  color: #000000;
  background: #adadad38;
  padding: 8px 0px;
  border-radius: 8px;
  margin: 5px;
}
</style>
