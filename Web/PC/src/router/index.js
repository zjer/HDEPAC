import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/views/login'
import Home from '@/views/home'
import Lists from '@/views/lists'
import Charts from '@/views/charts'
import Settings from '@/views/settings'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home
    }, {
      path: '/login',
      name: 'login',
      component: Login
    }, {
      path: '/lists',
      name: 'lists',
      component: Lists
    }, {
      path: '/charts',
      name: 'charts',
      component: Charts
    }, {
      path: '/settings',
      name: 'settings',
      component: Settings
    }
  ]
})
