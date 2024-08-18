<template>
  <main >
      <div class="flex space-x-2 p-6 bg-white border border-gray-200 mt-24 rounded-sm overflow-x-scroll lg:mt-28">
        <div v-for="story in stories" :key="story.id">
          <Story
            :id="story.id"
            :username="story.username"
          />
        </div>
      </div>
  </main>
</template>

<script>
import Story from "../components/StoryComp.vue";

export default {
  name: 'Stories-main',
  components: {
    Story,
  },
  data() {
    return {
      stories: [
        {
          id: '1',
          username: 'qwer',
        },
        {
          id: '2',
          username: 'asdf',
        },
        {
          id: '3',
          username: 'zxcv',
        }
      ]
    }
  },
}
</script>