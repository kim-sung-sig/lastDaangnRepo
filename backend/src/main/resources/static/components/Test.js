export default {
    template: `
        <div>
            <h1>{{ message }}</h1>
            <p>This is dynamically imported content.</p>
            <button @click="sayHello">Say Hello</button>
        </div>
    `,
    data() {
        return {
            message: 'Hello World!'
        }
    },
    methods: {
        sayHello() {
            alert(this.message);
        }
    }
}