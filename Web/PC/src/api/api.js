import fetch from '@/config/fetch'

// 用户登录
export const login = data => fetch('/login/CheckLogin', data, 'POST');
