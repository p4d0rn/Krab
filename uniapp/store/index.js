import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

//为了方便测试，此处用vuex做全局数据
const store = new Vuex.Store({
	state: {
		userInfo: {},
		isLogin: false,
		orderType: 'takein',
		addresses: [],
		default_address: {},
		orders: [],
		remark: '不打包'
	},
	mutations: {
		SET_ORDER_TYPE(state, orderType) {
			state.orderType = orderType
		},
		SET_ADDRESS(state, address) {
			state.address = address
		},
		SET_REMARK(state, remark) {
			state.remark = remark
		},
		SET_USERINFO(state, userInfo) {
			state.userInfo = userInfo
		},
		SET_ISLOGIN(state, isLogin) {
			state.isLogin = isLogin
		}
	}
})

export default store