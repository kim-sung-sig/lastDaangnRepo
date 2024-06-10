<template>
  <div id="app" @mousemove="resetSessionTimeout" @keydown="resetSessionTimeout">
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
import { mapActions, mapState } from "vuex";
import MainHeader from "./components/MainHeader.vue";
import MainNavbar from "./components/MainNavbar.vue";

export default {
  name: "App",
  components: {
    MainHeader,
    MainNavbar,
  },
  computed: {
    ...mapState(["isLoggedIn"]),
  },
  created() {
    this.startPollingSession();
    this.resetSessionTimeout();
  },
  methods: {
    ...mapActions(["startPollingSession", "resetSessionTimeoutAction"]),
    resetSessionTimeout() {
      this.resetSessionTimeoutAction();
    },
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
