export default {
    props : ['input'],
    template: `
        <div>
            <h1>{{ input.name }}</h1>
            <p>{{ input.description }}</p>
            <button @click="sayHello">Say Hello</button>
            <button @click="changeName">Change Name</button>
            <button @click="showLocalData">Show Local Data</button>
        </div>
    `,
    data() {
        return {
            localData: { ...this.input },
        }
    },
    methods: {
        sayHello() {
            const name = this.localData.name;
            const description = this.localData.description;

            const message = `Hello ${name}! ${description}`;
            alert(message);
        },
        changeName() {
            this.localData.name = 'New Name';
            this.$emit('update:data', this.localData);
        },
        showLocalData() {
            console.log(this.localData);
        }
    }
}