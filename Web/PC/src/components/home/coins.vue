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
        oldPrice: [],
        nowPrice: [],
      }
    },
    created() {
      this.getCoinPrice();
      this.getNowPrice();
    },
    mounted() {
      if (this.timer) {
        clearInterval(this.timer);
      } else {
        this.timer = setInterval(() => {
          this.getNowPrice();
        }, 30000);
      }
    },
    destroyed() {
      clearInterval(this.timer);
    },
    methods: {
      getNowPrice() {
        axios.get(baseUrl + '/httpclient/getNowPrice').then(res => {
          if (res.data.state) {
            let coins = res.data.rows.data.coin_rate;
            let legals = res.data.rows.data.legal_rate;
            this.$set(this.oldPrice, 0, this.nowPrice);
            this.nowPrice = [];
            for (let legal in legals) {
              let msg = {};
              msg[legal] = legals[legal];
              this.nowPrice.push({
                name: legal,
                price: legals[legal]
              });
            }
            this.nowPrice.push({name: 'BTC', price: coins.BTC});
            this.nowPrice.push({name: 'BTC', price: coins.ETH});
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
                console.log(item.name);
                console.log(ele[1]);
                if (item.name === ele[1]) {
                  console.log(item.name);
                  console.log(ele[1]);
                  ele.price = item.price * 6.7;
                  ele.isDown = parseFloat(item.price) < this.oldPrice[index] ? true : false;
                }
              });
              ele.change = ele[13] > 0 ? '+' + ele[13] + '%' : ele[13] + '%';
              this.tableData.push(ele);
              this.isloading = false;
            });
          }
        });
      }
    }
  }
</script>

<style scoped>

</style>
