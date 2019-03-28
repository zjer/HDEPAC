<template>
  <div class="loginBg" id="login">
    <form class="loginBox">
      <van-cell-group>
        <van-cell>
          <van-field
            v-model="loginData.username"
            label="用户名"
            placeholder="请输入用户名"/>
        </van-cell>
        <van-cell>
          <van-field
            v-model="loginData.password"
            label="密码"
            placeholder="请输入手机号"/>
        </van-cell>
        <van-cell>
          <van-field
            v-model="loginData.validcode"
            label="验证码"
            placeholder="请输入手机号"/>
        </van-cell>
        <van-cell>
          <van-button type="primary" size="small" :disabled="canComplete" @click="doLogin">{{ $t('complete') }}</van-button>
        </van-cell>
      </van-cell-group>
    </form>
  </div>
</template>

<script>
import { login } from '@/api/api.js'
export default {
  name: 'login',
  data () {
    return {
      loginData: {
        username: '',
        password: '',
        validcode: ''
      },
      canComplete: false
    }
  },
  methods: {
    async login () {
      let result = await login({
        username: this.loginData.username.trim(),
        password: this.loginData.password.trim(),
        code: this.loginData.validcode.trim()
      });
      if (result.success) {
        console.log(result.rows)
      }
    },
    doLogin () {
      this.login()
    }
  }
}
</script>

<style scoped>

</style>
