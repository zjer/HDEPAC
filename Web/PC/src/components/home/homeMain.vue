<template>
  <div class="indexMain" id="height">
    <el-row :gutter="20">
      <el-col :span="16">
        <div class="grid-content bg-purple">
          <weather v-if="$store.state.showWeather"></weather>
        </div>
      </el-col>
      <el-col :span="8">
        <div class="grid-content bg-purple">
          <news v-if="$store.state.showNews"></news>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  import Weather from "./weather";
  import News from "./news";

  export default {
    name: 'homeMain',
    components: { News, Weather },
    created() {
      this.watchHeight();
    },
    mounted() {
      setTimeout(() => {
        let winHeight = document.documentElement.clientHeight - 172;
        document.getElementById("height").style.height = winHeight + 'px';
      }, 1);
    },
    methods: {
      watchHeight() {
        setTimeout(() => {
          window.onresize = function temp() {
            let winHeight = document.documentElement.clientHeight - 172;
            document.getElementById("height").style.height = winHeight + 'px';
          };
        }, 1);
      }
    }
  }
</script>

<style scoped>

</style>
