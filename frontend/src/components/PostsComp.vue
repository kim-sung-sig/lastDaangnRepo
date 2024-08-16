<template>
    <main>
        <div class="bg-white my-6 border border-gray-200 rounded-md">
            <div class="flex justify-between items-center p-2 border-b border-gray-200">
                <div class="flex items-center">
                    <img :src="props.profile" alt="Image" class="h-10 w-10 rounded-full">
                    <h4 class="font-bold pl-3">{{ props.username }}</h4>
                </div>
                <!-- <DotsHorizontalIcon class="h-6 w-6 text-gray-500"/> -->
            </div>
            <div>
                <img :src="props.media" :alt="props.username" class="w-full object-cover">
            </div>
            
        </div>
    </main>
</template>

<script>

export default {
    name: 'Posts-main',
    props: {
        id: String,
        username: String,
        profile: String,
        text: String,
        media: String,
        timeStamp: Object,
    },
    data() {
        return {
            comment: '',
            comments: [],
        }
    },
    mounted() {
        this.fetchComments();
    },
    methods: {
        fetchComments() {
            this.$firestore.collection('posts').doc(this.id).collection('comments').orderBy('timeStamp', 'desc').onSnapshot((snapshot) => {
                this.comments = snapshot.docs.map(doc => ({
                    id: doc.id,
                    ...doc.data()
                }));
            });
        },
        postComments() {
            this.$firestore.collection('posts').doc(this.id).collection('comments').add({
                profile: this.$store.state.user.photoURL,
                username: this.$store.state.user.displayName,
                comment: this.comment,
                timeStamp: new Date(),
            });
            this.comment = '';
        },
    }
}
</script>
