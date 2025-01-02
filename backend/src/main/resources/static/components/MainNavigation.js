export default {
    template: `
        <nav id="main_nav" v-if="isNavItemVisible()">
            <div v-for="(item, index) in navItems" :key="index">
                <router-link
                    :to="{ name : item.name }"
                    class="navBtn"
                    :class="{ active: item.name === currentRouteName  }">
                    <span class="material-symbols-outlined">{{ item.icon }}</span>
                    <span class="main_nav-text">{{ item.text }}</span>
                </router-link>
            </div>
        </nav>
    `,
    data() {
        return {
            navItems: [
                { name: 'home', text: '홈', icon: 'home' },
                // { url: '/test1', text: '동네생활', icon: 'tab_group' },
                // { url: '/test2', text: '내 근처', icon: 'file_map' },
                // { url: '/test3', text: '채팅', icon: 'chat' },
                { name: 'my', text: '나의 당근', icon: 'person' },
            ],
        };
    },
    computed: {
        currentRouteName() {
            return this.$route.name;
        },
    },
    methods: {
        isNavItemVisible() {
            const visibleRoutes = ['home', 'my'];
            const name = this.$route.name;
            return visibleRoutes.includes(name);
        },
    },
};