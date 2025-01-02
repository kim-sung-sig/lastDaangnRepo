export default {
    template: `
        <div>
            <h1>My Page</h1>
            <p>This is My Page.</p>
            click <router-link :to="{name : 'test'}">here</router-link> to test page.
        </div>
    `,
    data() {
        return {
        }
    },
    methods: {
        
    },
}