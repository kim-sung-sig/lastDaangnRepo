import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';

const app = createApp(App)
app.use(router)
app.use(store)
app.mount('#app');

const isAuthenticated = store.getters.isAuthenticated;

if(!isAuthenticated) {
    router.push({name: 'Login'});
}

