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
    name: 'lists',
    components: { FootNav, HeadTop },
    data () {
      return {
        headerTitle: this.$t('list'),
        active: 1,
        showArrow: false
      }
    }
  }
</script>

<style scoped>

</style>
