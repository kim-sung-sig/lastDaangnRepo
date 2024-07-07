<template>
	<MainLayout>
		<template #left>
            <div class="chat-left">
                <h1>채팅방목록</h1>
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
		padding: 20px;
		background: #ccc;
	}
	.chat-right {
		padding: 20px;
		background: #eee;
	}
</style>
