import axios from 'axios'
import { baseUrl } from "./env";
import { Message } from 'element-ui';  //element库的消息提示，可以不用

//创建axios实例
var service = axios.create({
  baseURL: baseUrl,
  timeout: 5000,
  headers: {
    'content-type': 'application/json',
    "token":'14a1347f412b319b0fef270489f'
  }
})

export default {
  ajax(url, param, method) {
    return new Promise((callback, reject) => {
      service({
        method: method,
        url,
        params: param,
      }, { emulateJSON: true }).then(res => {
        //axios返回的是一个promise对象
        let result = res.data;
        console.log(result)
        if (result.state) {
          callback(res);   //cback在promise执行器内部
          Message({
            showClose: true,
            message: result.message,
            type: 'success'
          });
        } else {
          Message({
            showClose: true,
            message: result.message,
            type: 'error'
          });
        }
      }).catch(err => {
        if (!err.response) {
          //Message是element库的组件，可以去掉
          Message({
            showClose: true,
            message: '请求错误',
            type: 'error'
          });
        } else {
          reject(err.response);
          console.log(err.response, '异常2')
        }
      })

    })
  }
}
