<template>
    <main class="grid grid-cols-1 -mt-5 mx-auto md:grid-cols-2 md:max-w-3xl xl:grid-cols-3 xl:max-w-5xl">
        <section class="col-span-2">
            <Stories/>
        </section>
    </main>
</template>

<script>
import Stories from "../components/StoriesComp.vue";

export default {
    name: 'Feed-main',
    components: {
        Stories,
    },
    data() {
        return {
            postsData: [],
        }
    },
}
</script>
