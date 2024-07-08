import { createRouter, createWebHistory } from 'vue-router';
import store from '../store';

const routes = [
    {
        path: '/login',
        name: 'Login',
        component: () => import(/* webpackChunkName: "about" */ '../views/LoginView.vue'),
    },
    {
        path: '/signup',
        name: 'Signup',
        component: () => import(/* webpackChunkName: "about" */ '../views/SignupView.vue'),
    },
    {
        path: '/',
        name: 'Home',
        component: () => import(/* webpackChunkName: "about" */ '../views/HomeView.vue'),
        meta: { requiresAuth: true }
    },
    {
        path: '/chat',
        name: 'chat',
        component: () => import(/* webpackChunkName: "about" */ '../views/ChatView.vue'),
        meta: { requiresAuth: true }
    },
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
});

router.beforeEach(async (to, from, next) => {
    // 디버그
    console.debug('to:', to.path);
    console.debug('from:', from.path);
    console.debug('isLoggedIn:', store.state.isLoggedIn);

    if (to.matched.some(record => record.meta.requiresAuth)) {
        console.debug('requiresAuth:', true); // 추가 디버깅 로그
        if (!store.state.isLoggedIn) {
            try {
                const res = await store.dispatch('checkLoginStatus');
                console.debug('checkLoginStatus result:', res);
                if (res) {
                    next();
                } else {
                    next('/login');
                }
            } catch (error) {
                next('/login');
            }
        } else {
            next();
        }
    } else {
        next();
    }
});

export default router
