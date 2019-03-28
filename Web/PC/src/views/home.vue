<template>
  <div>
    <head-top :topTitle="headerTitle" :showArrow="showArrow"></head-top>
    <guide v-if="$store.state.showGuide"></guide>
    <home-main></home-main>
  </div>
</template>

<script>
  import { getLocal } from "../config/mUtils";
  import HeadTop from '../components/headTop';
  import Guide from "../components/home/guide";
  import HomeMain from "../components/home/homeMain";

  export default {
    name: 'home',
    components: { HomeMain, Guide, HeadTop },
    data () {
      return {
        headerTitle: this.$t('home'),
        active: 0,
        showArrow: false
      }
    },
    computed: {
      listenGuid() {
        return this.$store.state.showGuide
      }
    },
    watch: {
      listenGuid: function (val) {
        console.log(val)
        this.$store.state.showGuide = val;
      }
    },
  }
</script>

<style scoped>

</style>
