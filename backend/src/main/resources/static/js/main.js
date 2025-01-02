
const { createApp } = Vue;
const { createRouter, createWebHistory } = VueRouter;

import MainNavigation from "../components/MainNavigation.js";
import routes from "./route/route.js";

const router = createRouter({
    history: createWebHistory(),
    routes,
});

const app = createApp({
    template: `
        <MainNavigation />
        <div id = "content">
            <router-view></router-view>
        </div>
    `,
    components: {
        MainNavigation,
    },
    data() {
        return {
            message: 'Hello Vue!',
        };
    },
});

app.use(router);

app.mount('#app');