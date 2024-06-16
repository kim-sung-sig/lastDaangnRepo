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
        
    },
    getters : {
        isLoggedIn: state => state.isLoggedIn
    }
});

export default store;
