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
            <img class="coinIcon" :src="scope.row.icon_url" alt="">
            <span class="coinName">{{ scope.row.cn_name }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="price" :label="$t('message.price')">
        </el-table-column>
        <el-table-column prop="change" :label="$t('message.change24H')">
        </el-table-column>
        <el-table-column prop="trade_24h" :label="$t('message.trade24H')">
        </el-table-column>
        <el-table-column prop="market_cap" :label="$t('message.cap')">
        </el-table-column>
        <el-table-column prop="supply" :label="$t('message.supply')">
        </el-table-column>
      </el-table>
      <iframe src="https://cn.widgets.investing.com/top-cryptocurrencies?theme=darkTheme" width="100%" height="480px" frameborder="0" allowtransparency="true" marginwidth="0" marginheight="0"></iframe>
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
        tableData: []
      }
    },
    created() {
      this.getCoinPrice();
    },
    mounted() {
      if (this.timer) {
        clearInterval(this.timer);
      } else {
        this.timer = setInterval(() => {
          this.getCoinPrice();
        }, 300000);
      }
    },
    destroyed() {
      clearInterval(this.timer);
    },
    methods: {
      getCoinPrice() {
        axios.get(baseUrl + '/httpclient/getCoinPrice').then(res => {
          this.isloading = true;
          this.tableData = [];
          if (res.data.state) {
            let datas = res.data.rows;
            console.log(datas);
            datas.forEach(ele => {
              ele.change = ele.change > 0 ? '+' +ele.change + '%' : ele.change + '%';
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
