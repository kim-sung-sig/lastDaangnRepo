import axios from 'axios';
import { createStore } from 'vuex';

const store = createStore({
    state: {
        isLoggedIn: false,
        sessionTimeout: null,
    },
    mutations: {
        setLoggedIn(state, status) {
            state.isLoggedIn = status;
        },
        setSessionTimeout(state, timeout) {
            state.sessionTimeout = timeout;
        },
        clearSessionTimeout(state) {
            clearTimeout(state.sessionTimeout);
            state.sessionTimeout = null;
        },
    },
    actions: {
        async login({ commit }, formData) {
            try {
                const formDataToSend = new FormData();
                formDataToSend.append('username', formData.username);
                formDataToSend.append('password', formData.password);
                const response = await axios.post('/api/login', formDataToSend);
                if (response.status === 200) {
                    commit('setLoggedIn', true);
                    return response;
                } else {
                    throw new Error('Login failed');
                }
            } catch (error) {
                throw new Error('Login failed');
            }
        },

        // TODO 로그아웃 안들어야담!
        logout({ commit }) {
            commit('setLoggedIn', false);
        },

        resetSessionTimeout({ commit, dispatch }) {
            commit('clearSessionTimeout');
            const timeout = setTimeout(() => {
                dispatch('checkLoginStatus');
            }, 1000 * 60 * 30 + 1000); // 30분 1초 뒤에 세션 상태 확인
            commit('setSessionTimeout', timeout);
        },
        
        async checkLoginStatus({ commit, dispatch }) {
            try {
                const response = await axios.get('/api/status');
                if (response.status === 200) {
                    if(JSON.parse(response.data.msg) === true){
                        commit('setLoggedIn', true);
                        dispatch('resetSessionTimeout');
                        return true;
                    } else {
                        commit('setLoggedIn', false);
                        return false;
                    }
                } else {
                    throw new Error('Login failed');
                }
            } catch (error) {
                commit('setLoggedIn', false);
                return false;
            }
        },

        async initializeLogin({ dispatch }) {
            await dispatch('checkLoginStatus');
        }
    },
    getters : {
        isLoggedIn: state => state.isLoggedIn
    }
});

export default store;
