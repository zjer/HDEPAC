<template>
  <div>
    <div class="title">
      <span>{{ $t('message.weather') }}</span>
    </div>
    <div class="content" v-loading='isloading'>
      <div class="curCity">
        {{ $t('message.curCity') }} {{ curCity }}
      </div>
    </div>
  </div>
</template>

<script>
  import { baseUrl } from "../../config/env";
  import { getLocal } from "../../config/mUtils";
  import axios from 'axios';

  export default {
    name: 'weather',
    data () {
      return {
        isloading: false,
        weatherLists: [],
        curCity: ''
      }
    },
    created() {
      this.getWeather();
    },
    methods: {
      getWeather() {
        axios.get(baseUrl + '/httpclient/getWeather').then(res => {
          this.isloading = true;
          this.weatherLists = [];
          if (res.data.state) {
            let datas = JSON.parse(res.data.rows);
            console.log(datas);
            this.curCity = getLocal('lang') === 'zh_CN' ? datas.city : datas.cityEn;
            datas.data.forEach(ele => {
              this.weatherLists.push(ele);
              this.isloading = false;
            });
          }
        });
      },
    }
  }
</script>

<style scoped>

</style>
