<template>
  <header class="header">
    <div class="h-1"></div>
    <div class="h-2">
      <input type="search" name="search">
    </div>
    <div class="h-3"></div>
  </header>
</template>

<script>
export default {
  name: 'MainHeader'
};
</script>

<style scoped>
.header {
  background: linear-gradient(90deg, #3B0F40, #27052A);
  color: white;
  width: 100%;
  height: 40px;
  display: flex;
}
.h-1 {
  width: 25%;
  height: 100%;
  display: flex;
  align-items: center;
}
.h-2 {
  width: 50%;
  height: 100%;
  display: flex;
  align-items: center;
}
.h-3 {
  width: 25%;
  height: 100%;
}
</style>
