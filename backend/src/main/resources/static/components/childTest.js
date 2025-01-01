export default {
    props : ['result'],
    template: `
        <div>
            <h1>{{ result.name }}</h1>
            <p>{{ result.description }}</p>
            <button @click="sayHello">Say Hello</button>
            <button @click="changeName">Change Name</button>
        </div>
    `,
    // data() {
    //     return {
    //         result: { ...this.result },
    //     }
    // },
    methods: {
        sayHello() {
            const name = this.result.name;
            const description = this.result.description;

            const message = `Hello ${name}! ${description}`;
            alert(message);
        },
        changeName() {
            this.result.name = 'New Name';
        }
    }
}