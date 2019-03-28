<template>
  <div>
    <head-top :topTitle="headerTitle" :showArrow="showArrow"></head-top>
    <foot-nav :currentIndex="active"></foot-nav>
  </div>
</template>

<script>
  import HeadTop from '../components/headTop';
  import FootNav from '../components/footNav';

  export default {
    name: 'charts',
    components: { FootNav, HeadTop },
    data () {
      return {
        headerTitle: this.$t('chart'),
        active: 2,
        showArrow: false
      }
    }
  }
</script>

<style scoped>

</style>
