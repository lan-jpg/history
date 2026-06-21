import App from './App'

// #ifndef VUE3
import Vue from 'vue'
import './uni.promisify.adaptor'

Vue.prototype.getUserId = function() {
    return uni.getStorageSync('userId'); // 获取全局用户 ID
};

Vue.prototype.checkLogin = function() {
    return !!uni.getStorageSync('userId'); // 检查是否登录
};

Vue.config.productionTip = false
App.mpType = 'app'
const app = new Vue({
  ...App
})
app.$mount()
// #endif

// #ifdef VUE3
import { createSSRApp } from 'vue'
export function createApp() {
  const app = createSSRApp(App)
  return {
    app
  }
}
// #endif