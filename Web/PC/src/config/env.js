/**
 * 配置编译环境和线上环境之间的切换
 *
 * baseUrl: 域名地址
 * routerMode: 路由模式
 * baseImgPath: 图片存放地址
 *
 */
let baseUrl = '';
let baseImgPath;
let logoutUrl;

if (process.env.NODE_ENV === 'development') {
  baseUrl = 'http://localhost:81';
  baseImgPath = 'http://localhost:81/img/';
  logoutUrl = '/'
} else {
  baseUrl = 'http://localhost:81';
  baseImgPath = 'http://localhost:81/img/';
  logoutUrl = '/'
}

export {
  baseUrl,
  baseImgPath,
  logoutUrl
}
