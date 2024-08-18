<template>
    <nav id="main_nav">
        <div v-for="(item, index) in navItems" :key="index">
            <router-link :to="item.url" class="navBtn" :class="{ active: item.url === currentUrl }">
                <span class="material-symbols-outlined">{{ item.icon }}</span>
                <span class="main_nav-text">{{ item.text }}</span>
            </router-link>
        </div>
    </nav>
</template>

<script>
export default {
    name: 'GlobalHeader',
    data() {
        return {
            currentUrl: window.location.pathname,
            navItems: [
                { url: '/', text: '홈', icon: 'home' },
                { url: '/test1', text: '동네생활', icon: 'tab_group' },
                { url: '/test2', text: '내 근처', icon: 'file_map' },
                { url: '/test3', text: '채팅', icon: 'chat' },
                { url: '/test4', text: '나의 당근', icon: 'person' },
            ]
        }
    },
    methods: {
        loadPage(url, event) {
            event.preventDefault();
            this.currentUrl = url;
            window.history.pushState(null, null, url);
        }
    }
}
</script>

<style scoped>
    @import url('https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@40,200,0,0');
    #main_nav {
        width: 600px;
        display: flex;
        justify-content: space-between;
        position: fixed;
        bottom: 0;
        left: 50%;
        transform: translateX(-50%);
        border-radius: 16px 16px 0 0;
        background: #fff;
        border-left: 1px solid rgba(204, 204, 204, 0.5);
        border-top: 1px solid rgba(204, 204, 204, 0.5);
        border-right: 1px solid rgba(204, 204, 204, 0.5);

    }

    #main_nav > div {
        width: 100%;
    }
    #main_nav > div > a{
        width: 100%;
        display: flex;
        flex-direction: column;
        text-align: center;
        padding: 6px 0;
        height: 55px;

    }

    #main_nav {
        .navBtn {
            font-size: 12px;
        }
        .active {
            color: red;
        }
        .active .material-symbols-outlined {
            font-variation-settings:
            'FILL' 100
        }
    }
</style>