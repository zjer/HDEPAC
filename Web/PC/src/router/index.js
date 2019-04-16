import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/views/login'
import Manage from '@/views/manage'
import Home from '@/views/home'
import Lists from '@/views/lists'
import userManage from '@/views/master/userManage'
import productManage from '@/views/master/productManage'
import Charts from '@/views/charts'
import Settings from '@/views/settings'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'login',
      component: Login
    }, {
      path: '/manage',
      name: 'manage',
      component: Manage,
      children: [
        {
          path: '/home',
          name: 'home',
          component: Home,
          meta: ['home']
        }, {
          path: '/lists',
          name: 'lists',
          component: Lists,
          meta: ['aa', 'dd']
        }, {
          path: '/charts',
          name: 'charts',
          component: Charts,
          meta: ['aa', 'dd']
        }, {
          path: '/settings',
          name: 'settings',
          component: Settings,
          meta: ['aa', 'dd']
        }, {
          path: '/userManage',
          name: 'userManage',
          component: userManage,
          meta: ['masterData', 'userData']
        }, {
          path: '/productManage',
          name: 'productManage',
          component: productManage,
          meta: ['masterData', 'userData']
        },
      ]
    }
  ]
})
