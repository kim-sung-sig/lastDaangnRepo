<template>
    <div>
        <h1>Signup</h1>
        <form @submit.prevent="signup">
            <label for="username">Username:</label>
            <input type="text" id="username" v-model="username" required>
            
            <label for="email">Email:</label>
            <input type="email" id="email" v-model="email" required>
            
            <label for="password">Password:</label>
            <input type="password" id="password" v-model="password" required>
            
            <button type="submit">Sign Up</button>
        </form>
        <p v-if="errorMessage">{{ errorMessage }}</p>
    </div>
</template>

<script>
import axios from 'axios';

export default {
    data() {
        return {
            username: '',
            email: '',
            password: '',
            errorMessage: '',
        };
    },
    methods: {
        async signup() {
            try {
                const response = await axios.post('/api/v1/users', {
                    username: this.username,
                    password: this.password,
                    email: this.email,
                });
                console.log(response);
                if(response.status === 200) {
                    alert('Signup successful');
                    this.$router.push({ name: 'Login' });
                } else {
                    this.errorMessage = 'Sign Up failed';
                }
            } catch (error) {
                console.error(error);
                this.errorMessage = 'Sign Up failed';
            }
        }
    }
};
</script>

<style scoped>

</style>