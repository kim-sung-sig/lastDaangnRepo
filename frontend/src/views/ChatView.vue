<template>
	<MainLayout>
		<template #left>
            <div class="chat-left">
                <div class="chat-header">
                    <div class="chat-header_title">다이렉트 메시지</div>
                </div>
                <div class="chat-search">검색창 넣을꺼임</div>
                <div class="chat-rooms" ref="chatRooms" @mouseover="showScrollbar" @mouseleave="hideScrollbar">
                    <div class="chat-room_info" v-for="(item, index) in items" :key="index">
                        <div class="chat-room_info_avatar" :style="{background : 'url(' + item.thumbnail +') center/cover'}"></div>
                        <div class="chat-room_info_content">
                            <div class="chat-room_info_content_name">{{ item.name }}</div>
                            <div class="chat-room_info_content_message">{{ item.message }}</div>
                        </div>
                    </div>
                </div>
            </div>
        </template>

        <template #right>
            <div class="chat-right">
                <h1>Home-right</h1>
                <p>This is the Home page.</p>
            </div>
        </template>
	</MainLayout>
</template>

<script>
import MainLayout from '@/layout/MainLayout.vue';

export default {
    name: 'ChatRoom',
    components: {
        MainLayout, // 컴포넌트 등록
    },
    data() {
        return {
            items: [
                {
                    name: 'User 1',
                    message: 'Hello!',
                    thumbnail: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTc7DQLvV9fhbW8wO08qnT-pjkonD6fJm07og&s'
                },
                {
                    name: 'User 2',
                    message: 'Hi there!',
                    thumbnail: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTc7DQLvV9fhbW8wO08qnT-pjkonD6fJm07og&s'
                },
                {
                    name: 'User 2',
                    message: 'Hi there!',
                    thumbnail: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTc7DQLvV9fhbW8wO08qnT-pjkonD6fJm07og&s'
                },
                {
                    name: 'User 2',
                    message: 'Hi there!',
                    thumbnail: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTc7DQLvV9fhbW8wO08qnT-pjkonD6fJm07og&s'
                },
                {
                    name: 'User 2',
                    message: 'Hi there!',
                    thumbnail: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTc7DQLvV9fhbW8wO08qnT-pjkonD6fJm07og&s'
                },
                {
                    name: 'User 2',
                    message: 'Hi there!',
                    thumbnail: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTc7DQLvV9fhbW8wO08qnT-pjkonD6fJm07og&s'
                },
                {
                    name: 'User 2',
                    message: 'Hi there!',
                    thumbnail: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTc7DQLvV9fhbW8wO08qnT-pjkonD6fJm07og&s'
                },
                {
                    name: 'User 2',
                    message: 'Hi there!',
                    thumbnail: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTc7DQLvV9fhbW8wO08qnT-pjkonD6fJm07og&s'
                },
                {
                    name: 'User 2',
                    message: 'Hi there!',
                    thumbnail: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTc7DQLvV9fhbW8wO08qnT-pjkonD6fJm07og&s'
                },
                {
                    name: 'User 2',
                    message: 'Hi there!',
                    thumbnail: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTc7DQLvV9fhbW8wO08qnT-pjkonD6fJm07og&s'
                },
                {
                    name: 'User 2',
                    message: 'Hi there!',
                    thumbnail: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTc7DQLvV9fhbW8wO08qnT-pjkonD6fJm07og&s'
                },
                {
                    name: 'User 2',
                    message: 'Hi there!',
                    thumbnail: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTc7DQLvV9fhbW8wO08qnT-pjkonD6fJm07og&s'
                },
            ]
        };
    },
    methods: {
        showScrollbar() {
            this.$refs.chatRooms.classList.add('show-scrollbar');
        },
        hideScrollbar() {
            this.$refs.chatRooms.classList.remove('show-scrollbar');
        }
    }
};
</script>

<style scoped>
	.chat-left {
        height: calc(100vh - 40px);
        background: #444;
        color: white;
        overflow: hidden;
	}
	.chat-right {
	}

    .chat-header {
        height: 49px;
        padding: 0 20px;
        display: flex;
        align-items: center;
        justify-content: space-between;
        flex-direction: row;
        font-size: 18px;
    }

    .chat-header_title {
        text-overflow: ellipsis;
        white-space: nowrap;
        flex: 0 auto;
        overflow: hidden;
        font-weight: 700;
        color: #fff;
    }

    .chat-rooms{
        overflow-y : hidden;
        height: calc(100vh - 89px - 40px);
        transition: all 0.3s ease-in-out;
    }

    .chat-rooms.show-scrollbar {
        overflow-y: scroll;
    }

    .chat-rooms::-webkit-scrollbar {
        width: 10px;
    }

    .chat-rooms::-webkit-scrollbar-thumb {
        background-color: #FF8A3D;
        border-radius: 20px;
    }
    .chat-rooms::-webkit-scrollbar-track {
        background-color: #ccc;
        border-radius: 20px;
        box-shadow: inset 0px 0px 5px white;
    }
    .chat-search{
        height: 40px;
    }
    .chat-room_info{
        display: flex;
        padding: 15px;
        cursor: pointer;
        gap: 20px;
        justify-content: space-between;
    }
    .chat-room_info:hover{
        background: #555;
    }
    .chat-room_info_avatar{
        width: 50px;
        height: 50px;
        border-radius: 50%;
        overflow: hidden;
        display: flex;
        align-items: center;
        justify-content: center;
    }
    .chat-room_info_content{
        width: calc(100% - 70px);
    }
</style>
