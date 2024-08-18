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

* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
ul, li {
  list-style: none;
}
a {
  text-decoration: none;
  color: inherit;
}
#app {
  width: 600px;
  margin: 0 auto;
  height: 100vh;
  font-family: 'Noto Sans KR', Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}
</style>
