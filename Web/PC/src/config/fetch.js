import axios from 'axios'
import { baseUrl } from "./env";
import { Message } from 'element-ui';

axios.defaults.withCredentials = true;

//创建axios实例
var service = axios.create({
  baseURL: baseUrl,
  timeout: 5000,
  headers: {
    'content-type': 'application/json',
    "token":'14a1347f412b319b0fef270489f'
  }
});

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
        console.log(result);
        if (result.state) {
          callback(res);   //callback在promise执行器内部
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
        Message({
          showClose: true,
          message: err.response,
          type: 'error'
        });
      })
    })
  }
}
