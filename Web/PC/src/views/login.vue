<template>
  <div class="loginBg">
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" :label-width="labelW" class="loginForm">
      <el-form-item :label="$t('message.account')" prop="username">
        <el-input v-model="ruleForm.username"></el-input>
      </el-form-item>
      <el-form-item :label="$t('message.password')" prop="password">
        <el-input v-model="ruleForm.password"></el-input>
      </el-form-item>
      <el-form-item :label="$t('message.validateCode')" prop="validateCode">
        <el-input v-model="ruleForm.validateCode" maxlength="4" class="codeVal"></el-input>
        <div class="validateCode">
          <img class="authCode" :src="codePath" @click="setCode" />
        </div>
      </el-form-item>
      <el-form-item :label="$t('message.language')">
        <el-select v-model="ruleForm.language" @change="setLanguage">
          <el-option
            v-for="item in languageOptions"
            :key="item.code"
            :label="item.text"
            :value="item.code">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-tooltip class="item" effect="dark" :content="$t('message.login')" placement="top">
          <el-button type="primary" icon="iconfont icon-denglu" @click="submitForm('ruleForm')"></el-button>
        </el-tooltip>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  import { baseUrl } from "../config/env";
  import { setLocal, getLocal } from "../config/mUtils";

  export default {
    name: 'login',
    data () {
      return {
        labelW: 0+ 'px',
        ruleForm: {
          username: '',
          password: '',
          validateCode: '',
          language: getLocal('language') ? getLocal('language') : 'zh_CN',
        },
        rules: {
          username: [
            { required: true, message: '请输入活动名称', trigger: 'blur' },
            { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' }
          ],
          password: [
            { required: true, message: '请选择活动区域', trigger: 'change' }
          ],
          validateCode: [
            { required: true, message: '请选择活动区域', trigger: 'change' }
          ]
        },
        codePath: baseUrl + '/user/getAuthCode',
        languageOptions: [
          {code: 'zh_CN', text: this.$t('message.chinese')},
          {code: 'en_US', text: this.$t('message.english')}
        ],
      }
    },
    created() {
      this.resetLabelWidth();
      if (getLocal('language') === 'en_US') {
        this.$i18n.locale = "en_US";
      } else {
        this.$i18n.locale = "zh_CN";
      }
    },
    methods: {
      resetLabelWidth() {
        if (getLocal('language') === 'en_US') {
          this.labelW = 90+'px';
        } else {
          this.labelW = 70+'px';
        }
      },
      setCode() {
        let random = Math.random() * 10000;
        random = Base64.encode(parseInt(random));
        this.codePath = baseUrl + '/user/getAuthCode/?' + random;
        console.log(this.codePath);
      },
      setLanguage() {
        setLocal('language', this.ruleForm.language);
        if (this.ruleForm.language === 'zh_CN') {
          this.$i18n.locale = "zh_CN";
        } else if (this.ruleForm.language === 'en_US') {
          this.$i18n.locale = "en_US";
        } else {
          this.$i18n.locale = "zh_CN";
        }
        this.resetLabelWidth();
      },
      login() {
        let param = {
          username: this.ruleForm.username.trim(),
          password: this.ruleForm.password.trim()
        }
        this.fetch.ajax('/user/login', param, 'POST')
          .then(res => {
            if (res.data.state) {
              setLocal('username', res.data.rows.username);
              console.log(res)
              this.$router.push('manage');
            }
          })
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
