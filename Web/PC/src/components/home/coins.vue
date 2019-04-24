<template>
  <div>
    <div class="title">
      <span>{{ $t('message.coinPrice') }}</span>
    </div>
    <div class="content">
      <el-table
        v-loading='isloading'
        :data="tableData"
        border
        style="width: 100%">
        <el-table-column :label="$t('message.coinName')">
          <template slot-scope="scope">
            <img class="coinIcon" :src="'https://static.niuyan.com' + scope.row[3]" alt="">
            <span class="coinName">{{ scope.row[1] }} - {{ scope.row[4] }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="price" :label="$t('message.price')">
          <template slot-scope="scope">
            <div class="up" v-bind:class="{down: scope.row.isDown}">{{ scope.row.price }}</div>
          </template>
        </el-table-column>
        <el-table-column prop="trade_24h" :label="$t('message.trade24H')">
        </el-table-column>
        <el-table-column prop="market_cap" :label="$t('message.cap')">
        </el-table-column>
        <el-table-column prop="supply" :label="$t('message.supply')">
        </el-table-column>
        <el-table-column prop="change" :label="$t('message.change24H')">
          <template slot-scope="scope">
            <div class="positive" v-bind:class="{negative: scope.row.isNegative}">{{ scope.row.change }}</div>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
  import { baseUrl } from "../../config/env";
  import axios from 'axios';

  export default {
    name: 'coins',
    data () {
      return {
        isloading: false,
        curIndex: -1,
        tableData: [],
        isDown: true,
        isNegative: true,
        oldPrice: [],
        nowPrice: [],
        webSock: null
      }
    },
    created() {
      this.getCoinPrice();
      this.getNowPrice();
      this.initWebSocket();
    },
    mounted() {
      if (this.timer) {
        clearInterval(this.timer);
      } else {
        this.timer = setInterval(() => {
          this.getNowPrice();
          this.getCoinPrice();
        }, 1000);
      }
    },
    destroyed() {
      clearInterval(this.timer);
      this.initWebSocket();
    },
    methods: {
      getNowPrice() {
        axios.get(baseUrl + '/httpclient/getNowPrice').then(res => {
          if (res.data.state) {
            let coins = res.data.rows.data.coin_rate;
            let legals = res.data.rows.data.legal_rate;
            this.oldPrice = [];
            this.nowPrice.forEach(ele => {
              this.oldPrice.push(ele);
            });
            this.nowPrice = [];
            // for (let legal in legals) {
            //   let msg = {};
            //   msg[legal] = legals[legal];
            //   this.nowPrice.push({
            //     name: legal,
            //     price: legals[legal]
            //   });
            // }
            this.nowPrice.push({name: 'BTC', price: coins.BTC});
            this.nowPrice.push({name: 'ETH', price: coins.ETH});
            console.log(this.oldPrice);
            console.log(this.nowPrice);
          }
        });
      },
      getCoinPrice() {
        axios.get(baseUrl + '/httpclient/getCoinPrice').then(res => {
          this.isloading = true;
          this.tableData = [];
          if (res.data.state) {
            let datas = res.data.rows.data.data;
            console.log(datas);
            datas.forEach(ele => {
              ele.supply = ele[10];
              this.nowPrice.forEach((item, index) => {
                if (ele[1] === item.name) {
                  ele.price = parseFloat((parseFloat(item.price) * 6.72).toFixed(2)).toLocaleString();
                  ele.market_cap = parseInt(parseFloat(item.price) * 6.72 * ele.supply).toLocaleString();
                  ele.isDown = parseFloat(item.price) < parseFloat(this.oldPrice[index].price) ? true : false;
                  console.log('old:' + this.oldPrice[index].price);
                  console.log('old:' + item.price);
                }
              });
              ele.change = ele[13] > 0 ? '+' + (ele[13] * 100).toFixed(2) + '%' : (ele[13] * 100).toFixed(2) + '%';
              ele.isNegative = ele[13] < 0 ? true : false;
              this.tableData.push(ele);
              this.isloading = false;
            });
          }
        });
      },
      initWebSocket() { //初始化
        const wsUrl = 'wss://ws.niuyan.com/api/v2/web/coin/ws';
        this.webSock = new WebSocket(wsUrl);
        this.webSock.onopen = this.webSocketOnopen;
        this.webSock.onerror = this.webSocketOnerror;
        this.webSock.onmessage = this.webSocketOnmessage;
        this.webSock.onclose = this.webSocketClose;
      },
      webSocketOnopen() { //连接
        console.log("WebSocket连接成功");
      },
      webSocketOnerror(e) { //错误
        console.log("WebSocket连接发生错误");
      },
      webSocketOnmessage(e){ //数据接收
        const redata = JSON.parse(e.data);
        console.log(redata.value);
      },
      webSocketSend(agentData){//数据发送
        this.webSock.send(agentData);
      },
      webSocketClose(e){ //关闭
        console.log("connection closed (" + e.code + ")");
      }
    }
  }
</script>

<style scoped>

</style>
