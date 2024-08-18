// src/router/index.js

import Home from '@/views/Home.vue';
import Login from '@/views/Login.vue';
import { createRouter, createWebHistory } from 'vue-router';
import store from '../store';

const routes = [
    {
        path: '/',
        name: 'Home',
        component: Home,
        meta : { requiresAuth: true }
    },
    {
        path: '/login',
        name: 'Login',
        component: Login,
    }
];

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
});

router.beforeEach(async (to, from, next) => {
    const isLoggedIn = store.getters.isAuthenticated; // Vuex에서 로그인 상태 확인

    if (!isLoggedIn) {
        const loginStatus = await store.dispatch('checkLoginStatus'); // 로그인 상태를 API로 확인

        if (loginStatus) {
            if (to.path === '/login') {
                next('/');  // 이미 로그인한 경우 홈으로 리다이렉트
            } else {
                next();  // 다음 라우트로 이동
            }

        } else {
            if (to.matched.some(record => record.meta.requiresAuth)) {
                next('/login');  // 로그인 필요 라우트 접근 시 로그인 페이지로 리다이렉트
            } else {
                next();  // 로그인 필요하지 않은 페이지로 이동
            }
        }

    } else {
      next();  // 이미 로그인된 경우
    }
});


export default router;