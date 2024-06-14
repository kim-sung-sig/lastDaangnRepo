<!-- Layout.vue -->
<template>
    <MainHeader />
    <div class="layout">
        <MainNavbar />
        <slot></slot>
    </div>
</template>

<script>
import MainHeader from "@/components/MainHeader.vue";
import MainNavbar from "@/components/MainNavbar.vue"; // MainNavbar 컴포넌트 import
export default {
    components: {
        MainHeader,
        MainNavbar, // MainNavbar 컴포넌트 등록
    },
};
</script>

<style scoped>
.layout {
    display: flex;
    height: 100vh;
}
</style>
