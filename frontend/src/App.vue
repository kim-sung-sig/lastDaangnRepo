<template>
  <div id="app">
    <GlobalHeader />
    <router-view></router-view>
  </div>
</template>

<script>
import GlobalHeader from './components/GlobalHeader.vue';

export default {
  name: 'App',
  components: {
    GlobalHeader
  }
}
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@400;700&display=swap');

#app {
  font-family: 'Noto Sans KR', Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
