<template>
  <el-container>
    <div class="menu-btn iconfont" :class="isShowMenu=='true'?'icon-caidan-shousuo':'icon-caidan-dakai'" @click="hideMenu()"></div>
    <el-aside>
      <el-menu :default-active="defaultActive" style="min-height: 100%;" background-color="#1d2b36" text-color="#fff" active-text-color="#3890df" router :collapse="isShowMenu=='false'">
        <div class="logo"></div>
        <el-menu-item index="home">
          <i class="el-icon-menu"></i>
          <span slot="title">home</span>
        </el-menu-item>
        <el-menu-item index="lists">
          <i class="el-icon-menu"></i>
          <span slot="title">home</span>
        </el-menu-item>
        <el-menu-item index="charts">
          <i class="el-icon-menu"></i>
          <span slot="title">home</span>
        </el-menu-item>
        <el-menu-item index="settings">
          <i class="el-icon-menu"></i>
          <span slot="title">home</span>
        </el-menu-item>
      </el-menu>
    </el-aside>
    <el-main>
      <router-view></router-view>
    </el-main>
  </el-container>
</template>

<script>
  import { getStore, setStore } from '../config/mUtils';
  export default {
    data() {
      return {
        menuList: [],
        defaultActive:"/home",
        isShowMenu:'true'
      }
    },
    computed: {
    },
    created() {
      if (getStore('isShowMenu') && getStore('isShowMenu') != null && getStore('isShowMenu') != "") {
        this.isShowMenu = getStore('isShowMenu')
      } else {
        this.isShowMenu = 'true'
      }
    },
    methods: {
      hideMenu(){
        if(this.isShowMenu=='true'){
          this.isShowMenu='false';
        }
        else{
          this.isShowMenu='true';
        }
        setStore('isShowMenu',this.isShowMenu);
        if(this.isShowMenu=='true'){
          setTimeout(function() {
            document.getElementsByClassName('breadcrumb')[0].style.left = '200px';
            document.getElementsByClassName('menu-btn')[0].style.left = '225px';
            document.getElementsByClassName('el-main')[0].style.marginLeft = '200px';
            document.getElementsByClassName('top-head')[0].style.marginLeft = '200px';
            document.getElementsByClassName('el-dropdown')[0].style.marginRight = '220px';
            document.getElementsByClassName('el-aside')[0].style.maxWidth = '200px';
          }, 280);
        } else {
          document.getElementsByClassName('breadcrumb')[0].style.left = '54px';
          document.getElementsByClassName('menu-btn')[0].style.left = '60px';
          document.getElementsByClassName('el-main')[0].style.marginLeft = '44px';
          document.getElementsByClassName('top-head')[0].style.marginLeft = '44px';
          document.getElementsByClassName('el-dropdown')[0].style.marginRight = '20px';
          document.getElementsByClassName('el-aside')[0].style.maxWidth = '44px';
        }
      }
    }
  }
</script>

<style scoped>

</style>
