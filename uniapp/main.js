import Vue from 'vue'
import App from './App'
import store from './store'
import util from './common/util.js'
import {$http} from '@escook/request-miniprogram'

uni.$http = $http
$http.baseUrl = 'http://127.0.0.1:8088'

$http.beforeRequest = function(options) {
	let token = uni.getStorageSync('token')
	if(!token){
		uni.showToast({
		      title: '请先登录',
		      icon: 'none'
		})
		throw new Error('请先登录')
	}
	if (options.method == "POST") {
	    options.header = {
	      'Content-Type': 'application/json',
	    }
	}
	options.header.Authorization = token
	console.log(options.header)
	uni.showLoading({
		title: '数据加载中...',
	})
	
}

$http.afterRequest = function(options) {
	console.log(options.header)
	if(options.header.hasOwnProperty('Token')){
		uni.setStorageSync('token', options.header.Token)
	}
	uni.hideLoading()
}

uni.$showMsg = function(title = '数据加载失败!', duration = 1500) {
	uni.showToast({
		title:title,
		duration:duration,
		icon:'none',
	})
}

Vue.config.productionTip = false

App.mpType = 'app'

Vue.prototype.$onLaunched = new Promise(resolve => {
    Vue.prototype.$isResolve = resolve
})

Vue.prototype.$store = store
Vue.prototype.$util = util

const app = new Vue({
	store,
    ...App
})
app.$mount()

