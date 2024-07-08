<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: 'App',
  components: {
  },
};
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap');

* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
body {
  font-family: 'Noto Sans KR', sans-serif;
}
</style>
