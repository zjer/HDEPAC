<template>
  <div class="indexMain">
    <el-row :gutter="24">
      <el-col :span="24">
        <div class="grid-content">
          {{ $t('message.welcome')}} <span class="blue">{{ user }}</span> {{ $t('message.in')}} <span class="blue">{{ logintime }}</span> {{ $t('message.enterIn')}}
        </div>
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :span="16">
        <div class="grid-content">
          <news></news>
        </div>
      </el-col>
      <el-col :span="8">
        <div class="grid-content">
          <weather></weather>
        </div>
      </el-col>
    </el-row>
    <el-row :gutter="24">
      <el-col :span="24">
        <div class="grid-content">
          <coins></coins>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  import { getLocal, getStore } from "../../config/mUtils";
  import Weather from "./weather";
  import News from "./news";
  import Coins from "./coins";

  export default {
    name: 'homeMain',
    data () {
      return {
        user: getStore('chinesename'),
        logintime: getLocal('logintime')
      }
    },
    components: { Coins, News, Weather },
    created() {

    },
    mounted() {

    },
    methods: {

    }
  }
</script>

<style scoped>

</style>
