<template>
    <div class="login">
        <h1>Login</h1>
        <form @submit.prevent="login2">
            <label for="username">Username:</label>
            <input type="text" v-model="username" id="username" required />

            <label for="password">Password:</label>
            <input type="password" v-model="password" id="password" required />

            <button type="submit">Login</button>
        </form>
        <p v-if="errorMessage">{{ errorMessage }}</p>

        <router-link :to="{ name : 'Signup' }">Sign Up</router-link>
    </div>
</template>

<script>
import { mapActions } from 'vuex';

export default {
    data() {
        return {
            username: "",
            password: "",
            errorMessage: "",
        };
    },
    methods: {
        ...mapActions(['login']),
        login2() {
            this.login({ "username": this.username, "password": this.password })
                .then(() => {
                    this.$router.push('/');
                })
                .catch((error) => {
                    console.log(error);
                    this.errorMessage = 'Login failed'
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
