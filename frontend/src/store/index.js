// src/store/index.js
import { createStore } from 'vuex';
import axios from '../axios';

export default createStore({
    state : {
        isAuthenticated: false,
        user: null
    },

    mutations: {
        setUser(state, user) {
            state.user = user;
        },

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
                    commit('setUser', res.data);
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
                    commit('setUser', null);
                    return true;
                }
            
            } catch (error) {
                console.error(error);
                return false;
            }
        }

        
    },

    getters : {
        isAuthenticated: state => state.isAuthenticated,
        user: state => state.user
    }

});