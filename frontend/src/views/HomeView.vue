<template>
    <MainLayout>
        <template #left>
            <div class="home">
                <h1>Home-left</h1>
                <p>This is the Home page.</p>
            </div>
        </template>

        <template #right>
            <div class="home">
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

</style>
