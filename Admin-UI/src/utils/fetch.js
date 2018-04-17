import axios from 'axios'
import { Message, MessageBox } from 'element-ui'
import store from '@/store'
import { getToken, setToken } from '@/utils/auth'
import router from '@/router'

const DURATION = 5 * 1000;
// 创建axios实例
const service = axios.create({
  // baseURL: process.env.BASE_API, // api的base_url
  timeout: 5000 // 请求超时时间
});

// request拦截器
service.interceptors.request.use(config => {
  // Do something before request is sent
  if (store.getters.token) {
    config.headers['access-token'] = getToken(); // 让每个请求携带token--['X-Token']为自定义key 请根据实际情况自行修改
    console.log('发送数据请求携带的token:', getToken());
  }
  return config;
}, error => {
  // Do something with request error
  console.log(error); // for debug
  Promise.reject(error);
});

// respone拦截器
service.interceptors.response.use(
  response => {
    /**
     * 下面的注释为通过response自定义code来标示请求状态，当code返回如下情况为权限有问题，登出并返回到登录页
     * 如通过xmlhttprequest 状态码标识 逻辑可写在下面error中
     */
    const res = response.data;
    console.log('请求返回信息：', res);
    if (response.status === 200) {
      if (res === '') {
        MessageBox.confirm('会话超时，请重新登录，或者取消继续留在该页面', '确定退出', {
          confirmButtonText: '重新登录',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          store.dispatch('FedLogOut').then(() => {
            location.reload(); // 为了重新实例化vue-router对象 避免bug
            location.href = '/#/login';
          });
        });
      } else if (res.status === 308001) { // 登录失败  登录名或者密码错误
        Message({ message: res.data, type: 'error', duration: DURATION });
      } else if (res.status === 308100) { // 更新token
        setToken(res.token);
        console.log('更新token' + res.token);
        return res;
      } else if (res.status === 308010) { // token 错误
        Message({ message: res.data, type: 'error', duration: DURATION })
        console.log('token错误' + res.data)
      }
    }

    if (response.status !== 200 && res.status !== 200) {
      Message({ message: res.message, type: 'error', duration: DURATION });
    } else {
      return response.data;
    }
  },
  error => {
    const status = error.response.status;
    if (status === 401) {
      router.push({path: '/error/401'});
    } else if (status === 404) {
      router.push({path: '/error/404'});
    } else {
      Message({ message: error.message, type: 'error', duration: DURATION });
      return Promise.reject(error);
    }
  }
)

export default service;
