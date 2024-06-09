<template>
  <div id="app">
    <MainHeader />
    <div class="main-container">
      <MainNavbar />
      <div class="content">
        <router-view />
      </div>
    </div>
  </div>
</template>

<script>
import MainHeader from "./components/MainHeader.vue";
import MainNavbar from "./components/MainNavbar.vue";

export default {
  name: "App",
  components: {
    MainHeader,
    MainNavbar,
  },
};
</script>

<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
}

.main-container {
  display: flex;
}
</style>
