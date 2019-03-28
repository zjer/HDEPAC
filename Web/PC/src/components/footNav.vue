<template>
  <van-tabbar v-model="active" :currentIndex="currentIndex">
    <van-tabbar-item icon="wap-home" to="/">{{ $t('home') }}</van-tabbar-item>
    <van-tabbar-item icon="apps-o" to="lists">{{ $t('list') }}</van-tabbar-item>
    <van-tabbar-item icon="bar-chart-o" to="charts">{{ $t('chart') }}</van-tabbar-item>
    <van-tabbar-item icon="setting-o" to="settings">{{ $t('setting') }}</van-tabbar-item>
  </van-tabbar>
</template>

<script>
export default {
  name: 'footNav',
  props: ['currentIndex'],
  data () {
    return {
      active: this.currentIndex
    }
  }
}
</script>

<style scoped>

</style>
