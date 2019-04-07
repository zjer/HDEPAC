<template>
  <el-container>
    <div class="menu-btn iconfont" :class="isShowMenu == 'true' ? 'icon-caidan-shousuo' : 'icon-caidan-dakai'" @click="hideMenu()"></div>
    <el-aside>
      <el-menu :default-active="defaultActive" background-color="#1d2b36" text-color="#fff" active-text-color="#3890df" router :collapse="isShowMenu == 'false'">
        <div class="logo"></div>
        <el-menu-item index="home">
          <i class="iconfont icon-shouye-kong"></i>
          <span slot="title">{{ $t('message.home') }}</span>
        </el-menu-item>
        <el-submenu index="1">
          <template slot="title">
            <i class="iconfont icon-zhushuju"></i>
            <span slot="title">{{ $t('message.masterData') }}</span>
          </template>
          <el-menu-item index="userManage">
            <i class="iconfont icon-renyuanzu-kong"></i>
            <span slot="title">{{ $t('message.userData') }}</span>
          </el-menu-item>
        </el-submenu>
        <el-menu-item index="charts">
          <i class="iconfont icon-tubiaofenxi"></i>
          <span slot="title">{{ $t('message.chart') }}</span>
        </el-menu-item>
        <el-submenu index="2">
          <template slot="title">
            <i class="iconfont icon-shezhi-kong"></i>
            <span slot="title">{{ $t('message.systemSetting') }}</span>
          </template>
          <el-menu-item index="settings">
            <i class="iconfont icon-jiaoseshezhi"></i>
            <span slot="title">{{ $t('message.systemSetting') }}</span>
          </el-menu-item>
        </el-submenu>
      </el-menu>
    </el-aside>
    <el-container class="container">
      <router-view></router-view>
  </el-container>
  </el-container>
</template>

<script>
  import { getLocal, setLocal } from '../config/mUtils';
  export default {
    data() {
      return {
        menuList: [],
        defaultActive:"home",
        isShowMenu:'true'
      }
    },
    computed: {
    },
    created() {
      if (getLocal('isShowMenu') && getLocal('isShowMenu') != null && getLocal('isShowMenu') != "") {
        this.isShowMenu = getLocal('isShowMenu')
      } else {
        this.isShowMenu = 'true'
      }
      this.$router.push('home')
    },
    methods: {
      hideMenu(){
        if(this.isShowMenu == 'true'){
          this.isShowMenu = 'false';
        }
        else{
          this.isShowMenu = 'true';
        }
        setLocal('isShowMenu',this.isShowMenu);
        if(this.isShowMenu == 'true'){
          setTimeout(function() {
            document.getElementsByClassName('breadcrumb')[0].style.left = '180px';
            document.getElementsByClassName('menu-btn')[0].style.left = '205px';
            document.getElementsByClassName('container')[0].style.marginLeft = '180px';
            document.getElementsByClassName('top-head')[0].style.marginLeft = '180px';
            document.getElementsByClassName('el-aside')[0].style.maxWidth = '180px';
          }, 280);
        } else {
          document.getElementsByClassName('breadcrumb')[0].style.left = '64px';
          document.getElementsByClassName('menu-btn')[0].style.left = '90px';
          document.getElementsByClassName('container')[0].style.marginLeft = '64px';
          document.getElementsByClassName('top-head')[0].style.marginLeft = '64px';
          document.getElementsByClassName('el-aside')[0].style.maxWidth = '64px';
        }
      }
    }
  }
</script>

<style scoped>

</style>
