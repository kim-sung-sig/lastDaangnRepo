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
        async checkLoginStatus({ commit }) {
            try {
                const response = await axios.get('/api/status');
                console.log(response.data);
                commit('setLoggedIn', response.data.isLoggedIn);
                if (!response.data.isLoggedIn) {
                    window.location.href = '/login'; // 세션 만료 시 로그인 페이지로 이동
                }
            } catch (error) {
                console.error('Error checking login status:', error);
                window.location.href = '/login'; // 오류 발생 시 로그인 페이지로 이동
            }
        },
        resetSessionTimeout({ commit, dispatch }) {
            commit('clearSessionTimeout');
            const timeout = setTimeout(() => {
                dispatch('checkLoginStatus');
            }, 1000 * 60 * 30 + 1000); // 30분 1초 뒤에 세션 상태 확인
            commit('setSessionTimeout', timeout);
        },
        logout({ commit }) {
            commit('setLoggedIn', false);
        }
    },
    getters : {
        isLoggedIn: state => state.isLoggedIn
    }
});

export default store;
