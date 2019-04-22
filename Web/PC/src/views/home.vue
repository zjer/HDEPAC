<template>
  <div>
    <head-top></head-top>
    <home-main id="height"></home-main>
  </div>
</template>

<script>
  import HomeMain from "../components/home/homeMain";
  import HeadTop from "../components/headTop";

  export default {
    name: 'home',
    components: { HomeMain, HeadTop },
    data () {
      return {
        headerTitle: this.$t('home'),
        active: 0,
        showArrow: false
      }
    },
    created() {
      this.watchHeight();
    },
    mounted() {
      setTimeout(() => {
        let winHeight = document.documentElement.clientHeight - 170;
        document.getElementById("height").style.maxHeight = winHeight + 'px';
        document.getElementById("height").style.height = winHeight + 'px';
      }, 1);
    },
    methods: {
      watchHeight() {
        setTimeout(() => {
          window.onresize = function temp() {
            let winHeight = document.documentElement.clientHeight - 170;
            document.getElementById("height").style.maxHeight = winHeight + 'px';
            document.getElementById("height").style.height = winHeight + 'px';
          };
        }, 1);
      }
    }
  }
</script>

<style scoped>

</style>
