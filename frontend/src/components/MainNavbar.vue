<template>
  <div class="navbar-container">
    <div class="navbar">
      <router-link to="/" exact>
        <div style="display: flex; justify-content: center">
          <div class="nav-icon">
            <svg
              data-2goj="true"
              data-qa="home"
              aria-hidden="true"
              viewBox="0 0 20 20"
              class=""
              style="--s: 20px"
            >
              <path
                fill="currentColor"
                fill-rule="evenodd"
                d="M10.14 3.001a.25.25 0 0 0-.28 0L4.5 6.631v8.12A2.25 2.25 0 0 0 6.75 17h6.5a2.25 2.25 0 0 0 2.25-2.25V6.63zm-7.47 4.87L3 7.648v7.102a3.75 3.75 0 0 0 3.75 3.75h6.5A3.75 3.75 0 0 0 17 14.75V7.648l.33.223a.75.75 0 0 0 .84-1.242l-7.189-4.87a1.75 1.75 0 0 0-1.962 0l-7.19 4.87a.75.75 0 1 0 .842 1.242m9.33 2.13a1 1 0 0 0-1 1v1a1 1 0 0 0 1 1h1a1 1 0 0 0 1-1v-1a1 1 0 0 0-1-1z"
                clip-rule="evenodd"
              ></path>
            </svg>
          </div>
          <span>홈</span>
        </div>
      </router-link>
      <router-link to="/about">
        <div style="display: flex; justify-content: center">
          <div class="nav-icon">
            <svg
              data-2goj="true"
              data-qa="direct-messages"
              aria-hidden="true"
              viewBox="0 0 20 20"
              class=""
              style="--s: 20px"
            >
              <path
                fill="currentColor"
                fill-rule="evenodd"
                d="M7.675 6.468a4.75 4.75 0 1 1 8.807 3.441.75.75 0 0 0-.067.489l.379 1.896-1.896-.38a.75.75 0 0 0-.489.068 5 5 0 0 1-.648.273.75.75 0 1 0 .478 1.422q.314-.105.611-.242l2.753.55a.75.75 0 0 0 .882-.882l-.55-2.753A6.25 6.25 0 1 0 6.23 6.064a.75.75 0 1 0 1.445.404M6.5 8.5a5 5 0 0 0-4.57 7.03l-.415 2.073a.75.75 0 0 0 .882.882l2.074-.414A5 5 0 1 0 6.5 8.5m-3.5 5a3.5 3.5 0 1 1 1.91 3.119.75.75 0 0 0-.49-.068l-1.214.243.243-1.215a.75.75 0 0 0-.068-.488A3.5 3.5 0 0 1 3 13.5"
                clip-rule="evenodd"
              ></path>
            </svg>
          </div>
          <span>챗</span>
        </div>
      </router-link>
    </div>
    <div class="me">
      <button v-if="!isLoggedIn" @click="goToLogin">로그인</button>
      <button v-else @click="logout">로그아웃</button>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";

export default {
  name: "MainNavbar",
  computed: {
    ...mapState(["isLoggedIn"]),
  },
  created() {
    this.$store.dispatch("checkLoginStatus");
  },
  methods: {
    ...mapActions(["logout"]),
    goToLogin() {
      window.location.href = "/login"; // 로그인 페이지로 이동
    },
  },
};
</script>

<style scoped>
.navbar-container {
  width: 70px;
  background: #2f0932;
  color: white;
  height: calc(100vh - 40px);
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}
.navbar {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding-top: 20px;
  gap: 10px;
}
.navbar a {
  color: white;
  text-decoration: none;
  text-align: center;
  display: block;
  width: 52px;
}
.navbar a > div {
  padding: 5px;
  display: flex;
  align-items: center;
  flex-direction: column;
}
.navbar a > div .nav-icon {
  width: 36px;
  height: 36px;
  border-radius: 5px;
  margin-bottom: 5px;
  display: flex;
  justify-content: center;
  align-items: center;
}
.navbar a > div .nav-icon svg {
  width: 20px;
  height: 20px;
}
.navbar a > div > span {
  font-size: 14px;
}

.navbar a.router-link-active {
  div .nav-icon {
    background-color: #575757;
  }
  div .nav-icon svg {
    filler: invert(1) brightness(1.5);
  }
}
.navbar a:hover {
  div .nav-icon {
    background-color: #575757;
  }
  div .nav-icon svg {
    transform: scale(1.1);
  }
}
</style>
