<template>
  <div>
    <head-top :topTitle="headerTitle" :showArrow="showArrow"></head-top>
  </div>
</template>

<script>
  import HeadTop from '../components/headTop';

  export default {
    name: 'charts',
    components: { HeadTop },
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
