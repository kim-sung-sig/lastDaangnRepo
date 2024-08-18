// Home.js
export const Home = {
    template: `
        <div id="wrap">
            <h1>main Page</h1>
            <p>This is the content of main page.</p>
            <div id="text-container1">
                <span v-for="item in items" :key="item.id" :data-id="item.id" style="display: block">{{ item.value }}</span>
            </div>
            <div @click="addItem" style="cursor: pointer;"> + 더하기 </div>
        </div>
    `,
    data() {
        return {
            items: [
                { id: 1, value: 'Item 1' },
                { id: 2, value: 'Item 2' },
                { id: 3, value: 'Item 3' }
            ],
        }
    },
    methods: {
        addItem() {
            const newItem = { id: this.items.length + 1, value: `Item ${this.items.length + 1}` };
            this.items.push(newItem);
        }
    },
};