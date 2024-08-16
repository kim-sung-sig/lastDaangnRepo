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

// 네비게이션 가드 설정
router.beforeEach((to, from, next) => {
    // 라우트에 'requiresAuth' 메타 필드가 있는 경우
    if (to.matched.some(record => record.meta.requiresAuth)) {
        // 사용자 인증 상태를 확인
        if (!store.getters.isAuthenticated) {
            // 인증되지 않은 경우 로그인 페이지로 리디렉션
            next('/login');
        } else {
            next(); // 인증된 경우 원래의 경로로 이동
        }
    } else {
      next(); // 인증이 필요 없는 경로는 그냥 이동
    }
});


export default router;