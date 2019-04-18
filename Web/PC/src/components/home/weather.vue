<template>
  <div>
    <div class="title">
      <span>{{ $t('message.weather') }}</span>
    </div>
    <div class="content" v-loading='isloading'>
      <div class="weather">
        <span>{{ $t('message.curWea') }} {{ weaMsg.wea }}</span>
        <span class="ml10">{{ $t('message.curTem') }} {{ weaMsg.tem }}</span>
        <span class="iconfont icon-weizhi-kong fr">{{ weaMsg.curCity }}</span>
      </div>
      <div class="weather">
        <span>{{ $t('message.tem') }} {{ weaMsg.tems }}</span>
      </div>
      <div class="weather">
        <span>{{ $t('message.wind') }} {{ weaMsg.win }}</span>
      </div>
      <div class="weather">
        <span>{{ $t('message.air') }} {{ weaMsg.air }}</span>
      </div>
      <div class="weather">
        <span>{{ $t('message.airTips') }} {{ weaMsg.airTips }}</span>
      </div>
      <div class="weather">
        <span>{{ $t('message.updateIn') }} {{ weaMsg.updateTime }}</span>
      </div>
      <el-carousel trigger="click" indicator-position="none" height="90px">
        <el-carousel-item v-for="(weather, index) in weatherLists" :key="index">
          <div class="weather">
            <span>{{ $t('message.dayDate') }} {{ weather.day }}</span>
            <span>{{ $t('message.dayWea') }} {{ weather.wea }}</span>
          </div>
          <div class="weather">
            <span>{{ $t('message.dayTem') }} {{ weather.tems }}</span>
          </div>
          <div class="weather">
            <span>{{ $t('message.dayWind') }} {{ weather.wind }}</span>
          </div>
        </el-carousel-item>
      </el-carousel>
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
        weaMsg: {
          curCity: '',
          tems: '',
          wea: '',
          tem: '',
          air: '',
          airTips: '',
          win: '',
          updateTime: ''
        }
      }
    },
    created() {
      this.getWeather();
    },
    mounted() {
      if (this.timer) {
        clearInterval(this.timer);
      } else {
        this.timer = setInterval(() => {
          this.getWeather();
        }, 3600000);
      }
    },
    destroyed() {
      clearInterval(this.timer);
    },
    methods: {
      getWeather() {
        axios.get(baseUrl + '/httpclient/getWeather').then(res => {
          this.isloading = true;
          this.weatherLists = [];
          if (res.data.state) {
            let datas = res.data.rows;
            console.log(datas);
            this.isloading = false;
            datas.data.forEach((ele, index) => {
              if (index !== 0) {
                ele.tems = ele.tem2 + ' ~ ' + ele.tem1;
                ele.wind = ele.win + ' ' + ele.win_speed;
                this.weatherLists.push(ele);
              }
            });
            let msg = datas.data[0];
            this.weaMsg = {
              curCity: getLocal('lang') === 'zh_CN' ? datas.city : datas.cityEn,
              tems: msg.tem2 + ' ~ ' + msg.tem1,
              wea: msg.wea,
              tem: msg.tem,
              air: msg.air + ' ' + msg.air_level,
              airTips: msg.air_tips,
              win: msg.win + ' ' + msg.win_speed,
              updateTime: datas.update_time,
            }
          }
        });
      },
    }
  }
</script>

<style scoped>

</style>
