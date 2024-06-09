import axios from 'axios';
import { createStore } from 'vuex';

const store = createStore({
    state: {
        isLoggedIn: false,
    },
    mutations: {
        setLoggedIn(state, status) {
            state.isLoggedIn = status;
        },
    },
    actions: {
        async checkLoginStatus({ commit }) {
            const response = await axios.get('/api/status');
            commit('setLoggedIn', response.data);
        },
        async login({ commit }, username) {
            await axios.post('/api/login', { username });
            commit('setLoggedIn', true);
        },
        async logout({ commit }) {
            await axios.post('/api/logout');
            commit('setLoggedIn', false);
        },
    },
});

export default store;
