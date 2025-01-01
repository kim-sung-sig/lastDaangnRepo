import Child from './childTest.js';

export default {
    template: `
        <div>
            <h1><input type="text" v-model="message"/></h1>
            <p>This is dynamically imported content.</p>
            <button @click="sayHello">Say Hello</button>
        </div>
        <ul>
            <li v-for="item in items" :key="item.id">
                <button @click="selectItem(item)">{{ item.name }}</button>
            </li>
        </ul>

        <!-- 자식 컴포넌트로 selectedItem 전달 -->
        <Child v-if="selectedItem" :result="selectedItem" />
    `,
    components: {
        Child
    },
    data() {
        return {
            message: 'Hello World!',
            items : [
                { id: 1, name: 'Item 1' , description: 'Description 1'},
                { id: 2, name: 'Item 2' , description: 'Description 2'},
                { id: 3, name: 'Item 3' , description: 'Description 3'},
                { id: 4, name: 'Item 4' , description: 'Description 4'},
                { id: 5, name: 'Item 5' , description: 'Description 5'},
            ],
            selectedItem: null,
        }
    },
    methods: {
        sayHello() {
            alert(this.message);
        },
        selectItem(item) {
            // 선택된 아이템을 selectedItem에 저장
            this.selectedItem = item;
        }
    },
}