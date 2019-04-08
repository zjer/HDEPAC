/**
 * 存储sessionStorage
 */
export const setStore = (name, content) => {
  if (!name) return;
  if (typeof content !== 'string') {
    content = JSON.stringify(content);
  }
  window.sessionStorage.setItem(name, content);
};

/**
 * 获取sessionStorage
 */
export const getStore = name => {
  if (!name) return;
  return window.sessionStorage.getItem(name);
};

/**
 * 删除sessionStorage
 */
export const removeStore = name => {
  if (!name) return;
  window.sessionStorage.removeItem(name);
};

/**
 * 存储localStorage
 */
export const setLocal = (name, content) => {
  if (!name) return;
  if (typeof content !== 'string') {
    content = JSON.stringify(content);
  }
  window.localStorage.setItem(name, content);
};

/**
 * 获取localStorage
 */
export const getLocal = name => {
  if (!name) return;
  return window.localStorage.getItem(name);
};

/**
 * 删除localStorage
 */
export const removeLocal = name => {
  if (!name) return;
  window.localStorage.removeItem(name);
};

/**
 * 设置日期时间格式
 */
export const formatDateTime = (inputTime, type) => {
  let date  = parseInt(inputTime.substring(6,inputTime.length-2));
  date = new Date(date);
  let y = date.getFullYear();
  let m = date.getMonth() + 1;
  m = m < 10 ? ('0' + m) : m;
  let d = date.getDate();
  d = d < 10 ? ('0' + d) : d;
  let h = date.getHours();
  h = h < 10 ? ('0' + h) : h;
  let minute = date.getMinutes();
  let second = date.getSeconds();
  minute = minute < 10 ? ('0' + minute) : minute;
  second = second < 10 ? ('0' + second) : second;
  if (type === "yyyy-mm-dd") {
    return y + '-' + m + '-' + d;
  } else {
    return y + '-' + m + '-' + d + ' ' + h + ':' + minute + ':' + second;
  }

};

/**
 * 设置日期格式
 */
export const formatDate = (inputTime) => {
  let d = new Date(inputTime);
  let a = [d.getFullYear(), d.getMonth() + 1, d.getDate(), d.getHours(), d.getMinutes(), d.getSeconds()];
  for(let i = 0, len = a.length; i < len; i ++) {
    if(a[i] < 10) {
      a[i] = '0' + a[i];
    }
  }
  let str = a[0] + '-' + a[1] + '-' + a[2] + ' ' + a[3] + ':' + a[4] + ':' + a[5];
  return str;
};
