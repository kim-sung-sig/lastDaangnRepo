<template>
    <div class="login">
        <h2>Login</h2>
        <form @submit.prevent="login">
            <div>
                <label for="username">Username:</label>
                <input type="text" id="username" v-model="username" required />
            </div>
            <div>
                <label for="password">Password:</label>
                <input type="password" id="password" v-model="password" required />
            </div>
            <button type="submit">Login</button>
        </form>
    </div>
</template>

<script>
import axios from "axios";
import { mapActions } from "vuex";

export default {
    data() {
        return {
            username: "",
            password: "",
        };
    },
    methods: {
        ...mapActions(["resetSessionTimeout"]),
        async login() {
            let userName = this.username;
            console.log(userName);
            console.log(this.username);
            console.log(this.password);
            axios.post("/api/login", {
                "username" : this.username,
                "password" : this.password,
            })
            .then((res) => {
                if (res.data === true) {
                    this.$store.commit("setLoggedIn", true);
                    this.resetSessionTimeout();
                    this.$router.push("/");
                } else {
                    alert("Login failed");
                }
            })
            .catch((error) => {
                console.error("Login error:", error);
                alert("An error occurred during login.");
            });
        },
    },
};
</script>

<style scoped>
.login {
  max-width: 400px;
  margin: 0 auto;
  padding: 1em;
  border: 1px solid #ccc;
  border-radius: 4px;
}
.login h2 {
  text-align: center;
}
.login div {
  margin-bottom: 1em;
}
.login label {
  display: block;
  margin-bottom: 0.5em;
}
.login input {
  width: 100%;
  padding: 0.5em;
  box-sizing: border-box;
}
.login button {
  width: 100%;
  padding: 0.5em;
}
</style>
