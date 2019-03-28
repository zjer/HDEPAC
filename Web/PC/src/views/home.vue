<template>
  <div>
    <head-top></head-top>
    <el-main>
      <guide v-if="$store.state.showGuide"></guide>
      <home-main></home-main>
    </el-main>
  </div>
</template>

<script>
  import { getLocal } from "../config/mUtils";
  import Guide from "../components/home/guide";
  import HomeMain from "../components/home/homeMain";
  import HeadTop from "../components/headTop";

  export default {
    name: 'home',
    components: {HeadTop, HomeMain, Guide },
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
