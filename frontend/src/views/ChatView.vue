<template>
	<MainLayout>
		<template #left>
            <div class="chat-left">
                <div class="chat-header">
                    <div class="chat-header_title">다이렉트 메시지</div>
                </div>
                <div class="chat-search"></div>
                <div class="chat-rooms">
                    <div class="chat-room_info">
                        <div class="chat-room_info_avatar"></div>
                        <div class="chat-room_info_content">
                            <div class="chat-room_info_content_name">이름</div>
                            <div class="chat-room_info_content_message">메시지</div>
                        </div>
                    </div>
                </div>
                <p>This is the Home page.</p>
                <p>This is the Home page.</p>
                <p>This is the Home page.</p>
                <p>This is the Home page.</p>
                <p>This is the Home page.</p>
                <p>This is the Home page.</p>
                <p>This is the Home page.</p>
                <p>This is the Home page.</p>
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
    components: {
        MainLayout, // 컴포넌트 등록
    },
};
</script>

<style scoped>
	.chat-left {
        height: calc(100vh - 40px);
        background: #444;
        color: white;
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

    }
</style>
