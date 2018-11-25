<template>
  <div>
    <v-layout>
      <v-flex xs12 sm6 offset-sm3>
        <v-card class="user" v-for="user in $store.getters.getAllUsers" :key="user.customerId">
          <v-card-title primary-title>
            <h3 class="headline mb-0">{{user.name}}</h3>
          </v-card-title>
          <table>
            <tr class="field" v-for="(field, index) in getFields(user)" :key="index">
              <td><b>{{field}}:</b></td>
              <td>{{user[field]}}</td>
            </tr>
          </table>
          <v-card-actions>
            <v-dialog v-model="dialog" persistent max-width="600px">
              <v-btn slot="activator" flat>edit</v-btn>
              <v-card>
                <v-card-title>
                  <span class="headline">User Profile</span>
                </v-card-title>
                <v-card-text>
                  <v-container grid-list-md>
                    <v-layout wrap>
                      <v-flex xs12 sm6 md4>
                        <v-text-field label="Legal first name*" required></v-text-field>
                      </v-flex>
                      <v-flex xs12 sm6 md4>
                        <v-text-field label="Legal middle name" hint="example of helper text only on focus"></v-text-field>
                      </v-flex>
                      <v-flex xs12 sm6 md4>
                        <v-text-field label="Legal last name*" hint="example of persistent helper text" persistent-hint required></v-text-field>
                      </v-flex>
                      <v-flex xs12>
                        <v-text-field label="Email*" required></v-text-field>
                      </v-flex>
                      <v-flex xs12>
                        <v-text-field label="Password*" type="password" required></v-text-field>
                      </v-flex>
                      <v-flex xs12 sm6>
                        <v-select :items="['0-17', '18-29', '30-54', '54+']" label="Age*" required></v-select>
                      </v-flex>
                      <v-flex xs12 sm6>
                        <v-autocomplete :items="['Skiing', 'Ice hockey', 'Soccer', 'Basketball', 'Hockey', 'Reading', 'Writing', 'Coding', 'Basejump']" label="Interests" multiple></v-autocomplete>
                      </v-flex>
                    </v-layout>
                  </v-container>
                  <small>*indicates required field</small>
                </v-card-text>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn color="blue darken-1" flat @click="dialog = false">Close</v-btn>
                  <v-btn color="blue darken-1" flat @click="dialog = false">Save</v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>
            <v-btn flat @click="$store.dispatch('deleteCustomer', user.customerId)">delete</v-btn>
            <v-btn flat color="orange" @click="$store.dispatch('setCustomer', user.customerId)">use</v-btn>
          </v-card-actions>
        </v-card>
      </v-flex>
    </v-layout>
  </div>
</template>

<script>
export default {
  data: () => {
    return {
      dialog: false
    };
  },
  mounted() {
    this.$store.dispatch("allCustomers");
  },
  methods: {
    getFields(user) {
      const fields = Object.keys(user);
      console.log(fields);
      return fields;
    }
  }
};
</script>

<style lang="scss" scoped>
.user {
  margin: 10px;
}
.field {
  text-align: left;
  padding: 4px;
  outline: 1px solid #8080803d;
  text-transform: capitalize;
}
table {
  margin-left: 20px;
}
</style>
