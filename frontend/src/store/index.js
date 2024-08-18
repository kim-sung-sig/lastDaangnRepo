// src/store/index.js
import { createStore } from 'vuex';
import axios from '../axios';

export default createStore({

    state : {
        isAuthenticated: false,
    },

    mutations: {
        setIsAuthenticated(state, status) {
            state.isAuthenticated = status;
        }
    },

    actions : {
        async login({ commit }, {username, password}) {
            try {
                const formData = new FormData();
                formData.append('username', username);
                formData.append('password', password);
                const res = await axios.post('/login', formData);
                if (res.status === 200 && res.data.success) {
                    commit('setIsAuthenticated', true);
                    return true;
                } else {
                    return false;
                }

            } catch (error) {
                console.error(error);
                return false;
            }
        },

        async logout({ commit }) {
            try {
                if (this.user === null) {
                    return false;
                }

                const res = await axios.post('/logout');
                if (res.status === 200) {
                    commit('setIsAuthenticated', false);
                    return true;
                }
            
            } catch (error) {
                console.error(error);
                return false;
            }
        },

        async checkLoginStatus({ commit }) {
            try {
                const res = await axios.get('/api/user/status');

                if (res.status === 200 && res.data.status) {
                    commit('setIsAuthenticated', true);
                    return true;
                } else {
                    commit('setIsAuthenticated', false);
                    return false;
                }

            } catch (error) {
                console.error(error);
                return false;
            }
        },

        

    },

    getters : {
        isAuthenticated: state => state.isAuthenticated,
    }

});
