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
            let datas = res.data.rows.data.data;
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
