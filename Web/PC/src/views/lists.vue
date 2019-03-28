<template>
  <div>
    <head-top :topTitle="headerTitle" :showArrow="showArrow"></head-top>
  </div>
</template>

<script>
  import HeadTop from '../components/headTop';

  export default {
    name: 'lists',
    components: { HeadTop },
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
