export default {
    "getUser": state => state.user,
    "getAllUsers": state => state.allUsers,
    "getURL": state => state.URL + ":" + state.Port + "/api"
} 