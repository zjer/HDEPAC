<template>
  <div class="loginBg">
    <div class="loginContainer">
      <div class="loginTitle">{{ $t('message.userLogin') }}</div>
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" :label-width="labelW" class="loginForm">
        <el-form-item :label="$t('message.account')" prop="username">
          <el-input v-model="ruleForm.username"></el-input>
        </el-form-item>
        <el-form-item :label="$t('message.password')" prop="password">
          <el-input v-model="ruleForm.password"></el-input>
        </el-form-item>
        <el-form-item :label="$t('message.validateCode')" prop="validateCode">
          <el-input v-model="ruleForm.validateCode" maxlength="4" class="codeVal" @keyup.enter.native="onSubmit"></el-input>
          <div class="validateCode">
            <img class="authCode" :src="codePath" @click="setCode" />
          </div>
        </el-form-item>
        <el-form-item :label="$t('message.language')" prop="language" class="choseLanguage">
          <el-select v-model="ruleForm.language" @change="setLanguage">
            <el-option
              v-for="item in languageOptions"
              :key="item.code"
              :label="item.text"
              :value="item.code">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="remember" class="remember">
          <el-checkbox v-model="ruleForm.remember" @change="setRemember">{{ $t('message.remember') }}</el-checkbox>
        </el-form-item>
        <el-form-item>
          <el-tooltip class="item" effect="dark" :content="$t('message.login')" placement="top">
            <el-button type="primary" icon="iconfont icon-denglu" @click="submitForm('ruleForm')"></el-button>
          </el-tooltip>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
  import { baseUrl } from "../config/env";
  import { setLocal, getLocal, removeLocal, setStore } from "../config/mUtils";

  export default {
    name: 'login',
    data () {
      let validateUser = (rule, value, callback) => {
        if (!value) {
          return callback(new Error(this.$t('message.inputUsername')));
        } else if (value.length < 5 || value.length > 10) {
          return callback(new Error(this.$t('message.len5to10')));
        } else {
          callback();
        }
      };
      let validatePass = (rule, value, callback) => {
        if (!value) {
          callback(new Error(this.$t('message.inputPassword')));
        } else if (value.length < 5 || value.length > 10) {
          return callback(new Error(this.$t('message.len5to10')));
        } else {
          callback();
        }
      };
      let validateVCode = (rule, value, callback) => {
        if (!value) {
          callback(new Error(this.$t('message.inputVCode')));
        } else {
          callback();
        }
      };
      return {
        labelW: 0 + 'px',
        ruleForm: {
          username: getLocal('username') ? getLocal('username') : '',
          password: '',
          validateCode: '',
          language: getLocal('lang') ? getLocal('lang') : 'zh_CN',
          remember: getLocal('remember') === 'true' ? true : false
        },
        rules: {
          username: [
            { required: true, validator: validateUser, trigger: "blur" }
          ],
          password: [
            { required: true, validator: validatePass, trigger: "blur" }
          ],
          validateCode: [
            { required: true, validator: validateVCode, trigger: "blur" }
          ]
        },
        codePath: '',
        languageOptions: [
          {code: 'zh_CN', text: this.$t('message.chinese')},
          {code: 'en_US', text: this.$t('message.english')}
        ],
      }
    },
    created() {
      this.setCode();
      this.resetLabelWidth();
      if (getLocal('lang') === 'en_US') {
        this.$i18n.locale = "en_US";
      } else {
        this.$i18n.locale = "zh_CN";
      }
    },
    methods: {
      resetLabelWidth() {
        if (getLocal('lang') === 'en_US') {
          this.labelW = 90 + 'px';
        } else {
          this.labelW = 70 + 'px';
        }
      },
      setCode() {
        let random = Math.random() * 10000;
        random = Base64.encode(parseInt(random));
        this.codePath = baseUrl + '/user/getAuthCode?' + random;
        console.log(this.codePath);
      },
      setLanguage() {
        this.$refs['ruleForm'].resetFields();
        if (this.ruleForm.language === 'en_US') {
          this.$i18n.locale = 'en_US';
        } else {
          this.$i18n.locale = 'zh_CN';
        }
        setLocal('lang', this.ruleForm.language);
        this.resetLabelWidth();
      },
      setRemember() {
        let remember = this.ruleForm.remember;
        setLocal('remember', remember);
        if (remember === true) {
          setLocal('username', this.ruleForm.username);
        } else {
          removeLocal('username');
        }
      },
      login() {
        let param = {
          username: this.ruleForm.username.trim(),
          password: this.ruleForm.password.trim(),
          code: this.ruleForm.validateCode.trim().toLocaleLowerCase()
        };
        this.fetch.ajax('/user/login', param, 'POST')
          .then(res => {
            if (res.data.state) {
              console.log(res);
              let token = Base64.encode(res.data.rows.username + res.data.rows.password + new Date());
              setStore('token', token);
              setStore('username', res.data.rows.username);
              setLocal('lang', this.ruleForm.language);
              this.$router.push('manage');
              this.setCode();
            } else {
              this.setCode();
            }
          })
      },
      onSubmit() {
        this.login();
      },
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.login();
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      }
    }
  }
</script>

<style scoped>

</style>
