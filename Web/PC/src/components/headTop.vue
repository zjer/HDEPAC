<template>
  <van-nav-bar :title="topTitle" :left-arrow="showArrow"></van-nav-bar>
</template>

<script>
  export default {
    name: 'headTop',
    props: ['topTitle', 'showArrow']
  }
</script>

<style scoped>

</style>
