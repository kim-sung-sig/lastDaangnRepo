<template>
    <main class="grid grid-cols-1 -mt-5 mx-auto md:grid-cols-2 md:max-w-3xl xl:grid-cols-3 xl:max-w-5xl">
        <section class="col-span-2">
            <Stories/>
            <div v-for="post in postsData" :key="post.id">
                <Posts
                :id="post.id"
                :username="post.username"
                :profile="post.profile"
                :text="post.text"
                :media="post.media"
                :timeStamp="post.timeStamp"
                />
            </div>
        </section>
    </main>
</template>

<script>
import Posts from "../components/PostsComp.vue";
import Stories from "../components/StoriesComp.vue";

export default {
    name: 'Feed-main',
    components: {
        Posts,
        Stories,
    },
    data() {
        return {
            postsData: [],
        }
    },
    mounted() {
        this.fetchPosts();
    },
    methods: {
        fetchPosts() {
            this.$firestore.collection('posts').orderBy('timeStamp', 'desc').onSnapshot((snapshot) => {
                this.postsData = snapshot.docs.map(doc => ({
                    id: doc.id,
                    ...doc.data()
                }));
            });
        }
    }
}
</script>
