<!-- MainLayout.vue -->
<template>
    <MainHeader />
    <div class="layout">
        <MainNavbar />
        <div id="content">
            <SplitLayout>
                <template #left>
                    <slot name="left"></slot>
                </template>

                <template #right>
                    <slot name="right"></slot>
                </template>
            </SplitLayout>
        </div>
    </div>
</template>

<script>
import MainHeader from "@/components/MainHeader.vue";
import MainNavbar from "@/components/MainNavbar.vue"; // MainNavbar 컴포넌트 import
import SplitLayout from "@/layout/SplitLayout.vue"; // SplitLayout 컴포넌트 import

export default {
    components: {
        MainHeader,
        MainNavbar, // MainNavbar 컴포넌트 등록
        SplitLayout,
    },
};
</script>

<style scoped>
.layout {
    display: flex;
    height: calc(100vh - 40px);
}
#content {
    width: calc(100vw - 70px);
    flex: 1;
    display: flex;
}
</style>
