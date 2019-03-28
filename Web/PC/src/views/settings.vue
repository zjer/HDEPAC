<template>
  <div>
    <head-top :topTitle="headerTitle" :showArrow="showArrow"></head-top>
  </div>
</template>

<script>
import HeadTop from '../components/headTop';

export default {
  name: 'settings',
  components: { HeadTop },
  data () {
    return {
      headerTitle: this.$t('setting'),
      active: 3,
      showArrow: false
    }
  }
}
</script>

<style scoped>

</style>
