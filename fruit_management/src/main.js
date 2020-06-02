import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import axios from 'axios'
import qs from 'qs'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import './assets/css/reset.css'
import './assets/fonts/iconfont.css'


//引入ElementUi
Vue.use(ElementUI);

Vue.config.productionTip = false


// axios请求设置 - 每发送一个请求都必须携带Token字符串 - 进行后台验证用户
axios.defaults.withCredentials = true;
axios.defaults.baseURL="http://localhost:8080/apiGateway/"

//从浏览器缓存中找到token字段，获取token值，然后设置到Vue中每个请求头中都携带Token身份证
axios.interceptors.request.use(config => {
  config.headers.token = window.sessionStorage.getItem("token")
  return config;
})
Vue.prototype.$http = axios;


//axios的json转表单数据
Vue.prototype.$qs = qs;

//Nginx服务器前缀域名 - 静态资源服务器 - 获取图片的静态服务器前缀域名
Vue.prototype.$nginxPrefix='http://localhost:9999/'

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
