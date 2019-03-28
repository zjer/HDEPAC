<template>
  <el-header>
    <div class="top-head">
      <el-dropdown @command="handleCommand" trigger="click" class="navDash">
        <span class="el-dropdown-link" >
          <span>{{userName}}<i class="el-icon-caret-bottom el-icon--right"></i></span>
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item  command="changePW" divided style="border-top:0">{{$t('message.ModifyPW')}}</el-dropdown-item>
          <el-dropdown-item  command="singout" divided>{{$t('message.LogonExit')}}</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
      <el-select v-model="curLanguage" placeholder=" " class="checkLanguage" @change="setLanguage">
        <el-option
          v-for="item in languageOptions"
          :key="item.code"
          :label="item.text"
          :value="item.code"></el-option>
      </el-select>
    </div>
    <el-dialog class="changePW" :title="$t('message.ModifyPW')" :visible.sync="dialogVisible" width="550px">
      <el-form :label-width="labelW" :model="ruleForm" ref="ruleForm" :rules="rules">
        <el-form-item :label="$t('message.NewPassword')" prop="newPassword">
          <el-input clearable type="password" v-model="ruleForm.newPassword"></el-input>
        </el-form-item>
        <el-form-item :label="$t('message.ConfirmNewPassword')" prop="newPasswordagain">
          <el-input clearable type="password" v-model="ruleForm.newPasswordagain"></el-input>
        </el-form-item>
        <el-alert  :title="$t('message.FirstLogin')" type="error" style="width:98%;margin-left:2%"> </el-alert>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-tooltip class="item" effect="dark" :content="$t('message.Cancel')" placement="top">
          <el-button @click="cancel" icon="iconfont icon-guanbi-wukuang"></el-button>
        </el-tooltip>
        <el-tooltip class="item" effect="dark" :content="$t('message.Modify')" placement="top">
          <el-button type="primary" icon="iconfont icon-gou-wukuang" @click="modify('ruleForm')"></el-button>
        </el-tooltip>
      </span>
    </el-dialog>
    <div class="breadcrumb">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item v-for="(item, index) in $route.meta" separator="/" v-bind:key="index">{{$t('message.'+item)}}</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
  </el-header>
</template>

<script>
  import { signout, ModPassword } from "@/api/api";
  import { baseImgPath } from "@/config/env";
  import { mapActions, mapState } from "vuex";
  import { getStore, setStore, getLocal, setLocal } from "../config/mUtils";
  import en from '../i18n/langs/en';
  import cn from '../i18n/langs/cn';

  export default {
    name: 'headTop',
    data() {
      let validatePass2 = (rule, value, callback) => {
        if (value === '') {
          callback(new Error(this.$t('message.PasswordAgain')));
        } else if (value !== this.ruleForm.newPassword) {
          callback(new Error(this.$t('message.PasswordDifferent')));
        } else {
          callback();
        }
      };
      return {
        baseImgPath,
        userName: "",
        dialogVisible:false,
        labelW: 0+'px',
        ruleForm: {
          newPassword:"",
          newPasswordagain:""
        },
        rules:{
          newPassword: [{ required: true, message:this.$t('message.Input')+this.$t('message.NewPassword'), trigger: "change" }],
          newPasswordagain: [{required: true, validator: validatePass2, trigger: 'change' }]
        },
        languageOptions: [
          {code: 'zh_CN', text: this.$t('message.chinese')},
          {code: 'en_US', text: this.$t('message.english')}
        ],
        isShowMenu: getStore('isShowMenu'),
        curLanguage: getLocal('language')
      };
    },
    created() {
      this.resetLabelWidth();
      if (!this.adminInfo.id) {
        this.getAdminData();
        this.userName = getStore("Name");
      }
    },
    computed: {
      ...mapState(["adminInfo"])
    },
    watch:{
      cn:function(){
        //this.getLanguageResource();
      },
      en:function(){
        // this.getLanguageResource();
      },
      deep:true
    },
    mounted() {
      this.getMenuStatus(this.isShowMenu);
    },
    methods: {
      ...mapActions(["getAdminData"]),
      resetLabelWidth() {
        if (getLocal('language') == 'en_US') {
          this.labelW = 160+'px';
        } else {
          this.labelW = 85+'px';
        }
      },
      getMenuStatus(isShowMenu){
        if(isShowMenu=='true'||isShowMenu==null){
          // document.getElementsByClassName('kpi-container')[0].style.marginLeft = '0'
          document.getElementsByClassName('breadcrumb')[0].style.left = '200px';
          document.getElementsByClassName('menu-btn')[0].style.left = '225px';
          document.getElementsByClassName('el-aside')[0].style.maxWidth = '200px';
        } else {
          // document.getElementsByClassName('kpi-container')[0].style.marginLeft = '200px';
          document.getElementsByClassName('breadcrumb')[0].style.left = '54px';
          document.getElementsByClassName('menu-btn')[0].style.left = '60px';
          document.getElementsByClassName('container')[0].style.marginLeft = '64px';
          document.getElementsByClassName('top-head')[0].style.marginLeft = '64px';
          document.getElementsByClassName('el-aside')[0].style.maxWidth = '64px';
        }
      },
      async handleCommand(command) {
        if (command == "singout") {
          sessionStorage.removeItem("token");
          this.$message({
            type: "success",
            message: this.$t('message.Success')
          });
          this.$router.push("/");
        } else if (command == "changePW") {
          this.dialogVisible=true;
        }
      },
      modify(formname) {
        this.$refs[formname].validate((valid) => {
          if (valid) {
            this.dialogVisible = false;
            this.changePwd(formname);
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      setLanguage() {
        setLocal('language', this.curLanguage);
        if (getLocal('language') == 'zh_CN') {
          this.$i18n.locale = "cn";
        } else if (getLocal('language') == 'en_US') {
          this.$i18n.locale = "en";
        } else {
          this.$i18n.locale = "cn";
        }
      },
      async changePwd(formname){
        const res = await ModPassword({
          userId:sessionStorage.getItem("Id"),
          password:this.ruleForm.newPassword
        });
        if(res.Flag){
          this.$message({
            type: "success",
            message: this.$t('message.Success')
          });
          this.$nextTick(function () {
            this.$refs[formname].resetFields();
          });
        }
      },
      cancel(){
        this.dialogVisible = false;
        this.$nextTick(function () {
          this.$refs[formname].resetFields();
        });
      }
    }
  }
</script>

<style scoped>

</style>
