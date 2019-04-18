<template>
  <div>
    <div class="title">
      <span>{{ $t('message.coinNews') }}</span>
      <el-tooltip class="item" effect="dark" :content="$t('message.refresh')" placement="top">
        <i class="iconfont icon-shuaxin" @click="refreshNews"></i>
      </el-tooltip>
    </div>
    <div class="content" v-loading='isloading'>
      <div class="news" v-for="(news, index) in newsLists">
        <el-popover
          placement="bottom-start"
          width="500"
          trigger="click"
          @after-leave="resetStyle"
          :content="news.content">
          <span class="newsTitle" slot="reference" @click="setStyle(index)" v-bind:class="{active: curIndex === index}">{{ news.title }}</span>
        </el-popover>
        <span class="newsCreatetime">{{ news.createtime }}</span>
      </div>
    </div>
  </div>
</template>

<script>
  import { baseUrl } from "../../config/env";
  import { formatDate } from "../../config/mUtils";
  import axios from 'axios';

  export default {
    name: 'news',
    data () {
      return {
        isloading: false,
        curIndex: -1,
        newsLists: []
      }
    },
    created() {
      this.getNews();
    },
    methods: {
      getNews() {
        axios.get(baseUrl + '/httpclient/getNews').then(res => {
          this.isloading = true;
          this.newsLists = [];
          if (res.data.state) {
            let datas = JSON.parse(res.data.rows).list[0].lives;
            console.log(datas);
            datas.forEach(ele => {
              ele.title = ele.content.substring(0, ele.content.indexOf('】')+1);
              ele.createtime = formatDate(ele.created_at * 1000);
              ele.content = ele.content.replace(ele.title, '');
              this.newsLists.push(ele);
              this.isloading = false;
            });
          }
        });
      },
      setStyle(index) {
        this.curIndex = index;
      },
      resetStyle() {
        this.curIndex = -1;
      },
      refreshNews() {
        this.getNews();
      }
    }
  }
</script>

<style scoped>

</style>
