// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import echarts from 'echarts'
import i18n from './i18n/i18n'
import store from './store'
import fetch from './config/fetch'

Vue.config.productionTip = false;

Vue.prototype.fetch = fetch;
Vue.prototype.$echarts = echarts;

let Base64 = require('js-base64').Base64;

Vue.use(ElementUI);

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  i18n,
  store,
  template: '<App/>',
  components: { App }
})
