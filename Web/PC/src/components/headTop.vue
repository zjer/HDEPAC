<template>
  <el-header>
    <div class="top-head">
      <el-dropdown @command="handleCommand" trigger="click" class="navDash">
        <span class="el-dropdown-link" >
          <span>{{ userName }}<i class="el-icon-caret-bottom el-icon--right"></i></span>
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item  command="changePW" divided>{{$t('message.modifyPW')}}</el-dropdown-item>
          <el-dropdown-item  command="signOut" divided>{{$t('message.logout')}}</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
      <el-select v-model="curLanguage" class="checkLanguage" @change="setLanguage">
        <el-option
          v-for="item in languageOptions"
          :key="item.code"
          :label="item.text"
          :value="item.code"></el-option>
      </el-select>
    </div>
    <el-dialog :title="$t('message.modifyPW')" :visible.sync="dialogVisible" width="500px">
      <el-form :label-width="labelW" :model="ruleForm" ref="ruleForm" :rules="rules">
        <el-form-item :label="$t('message.newPassword')" prop="newPassword">
          <el-input clearable type="password" v-model="ruleForm.newPassword"></el-input>
        </el-form-item>
        <el-form-item :label="$t('message.confirmPassword')" prop="newPasswordagain">
          <el-input clearable type="password" v-model="ruleForm.newPasswordagain"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-tooltip class="item" effect="dark" :content="$t('message.cancel')" placement="top">
          <el-button icon="iconfont icon-guanbi-wukuang" @click="cancel"></el-button>
        </el-tooltip>
        <el-tooltip class="item" effect="dark" :content="$t('message.save')" placement="top">
          <el-button type="primary" icon="iconfont icon-baocun1" @click="modify('ruleForm')"></el-button>
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
  import { setLocal, getLocal, removeLocal, getStore } from "../config/mUtils";

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
        isShowMenu: getLocal('isShowMenu'),
        curLanguage: getLocal('lang'),
        userName: getStore('chinesename'),
        dialogVisible: false,
        labelW: 0 + 'px',
        ruleForm: {
          newPassword: '',
          newPasswordagain: ''
        },
        rules:{
          newPassword: [{ required: true, message:this.$t('message.Input')+this.$t('message.NewPassword'), trigger: 'change' }],
          newPasswordagain: [{required: true, validator: validatePass2, trigger: 'change' }]
        },
        languageOptions: [
          {code: 'zh_CN', text: this.$t('message.chinese')},
          {code: 'en_US', text: this.$t('message.english')}
        ],
      };
    },
    created() {
      this.resetLabelWidth();
      if (getLocal('lang') === 'en_US') {
        this.$i18n.locale = 'en_US';
      } else {
        this.$i18n.locale = 'zh_CN';
      }
      if (!getStore('token')) {
        this.$router.push('/');
      }
    },
    watch:{
    },
    mounted() {
      this.getMenuStatus(this.isShowMenu);
    },
    methods: {
      resetLabelWidth() {
        if (getLocal('lang') === 'en_US') {
          this.labelW = 130+'px';
        } else {
          this.labelW = 70+'px';
        }
      },
      getMenuStatus(isShowMenu){
        if(isShowMenu === 'true' || isShowMenu === null){
          document.getElementsByClassName('breadcrumb')[0].style.left = '180px';
          document.getElementsByClassName('menu-btn')[0].style.left = '205px';
          document.getElementsByClassName('container')[0].style.marginLeft = '180px';
          document.getElementsByClassName('el-aside')[0].style.maxWidth = '180px';
        } else {
          document.getElementsByClassName('breadcrumb')[0].style.left = '64px';
          document.getElementsByClassName('menu-btn')[0].style.left = '60px';
          document.getElementsByClassName('container')[0].style.marginLeft = '64px';
          document.getElementsByClassName('top-head')[0].style.marginLeft = '64px';
          document.getElementsByClassName('el-aside')[0].style.maxWidth = '64px';
        }
      },
      async handleCommand(command) {
        if (command === 'signOut') {
          removeLocal('token');
          this.$message({
            message: this.$t('message.logoutSuccess'),
            type: 'success'
          });
          this.$router.push('/');
        } else if (command === 'changePW') {
          this.dialogVisible=true;
        }
      },
      modify(formname) {
        this.$refs[formname].validate((valid) => {
          if (valid) {
            this.modifyPWD(this.ruleForm.newPassword);
            this.dialogVisible = false;
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      setLanguage() {
        if (this.curLanguage === 'en_US') {
          this.$i18n.locale = 'en_US';
          this.$store.commit('setLang', 0);
        } else {
          this.$i18n.locale = 'zh_CN';
          this.$store.commit('setLang', 1);
        }
        setLocal('lang', this.curLanguage);
        this.resetLabelWidth();
      },
      modifyPWD(val) {
        let param = {
          userid: getLocal('curId'),
          password: val
        };
        this.fetch.ajax('/user/modifyPWD', param, 'POST')
          .then(res => {
            if (res.data.state) {
              console.log(res);
              this.$message({
                type: "success",
                message: this.$t('message.saveSuccess')
              });
            }
          })
      },
      cancel(){
        this.dialogVisible = false;
        this.$nextTick(function () {
          this.$refs['ruleForm'].resetFields();
        });
      }
    }
  }
</script>

<style scoped>

</style>
