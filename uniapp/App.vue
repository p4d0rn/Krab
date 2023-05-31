<script>
	export default {
		onLaunch: async function() {
			// this.GetUserInfo()
			this.login()
			const { data: addr } = await uni.$http.get('/addr/get')
			this.$store.state.addresses = addr.data
			this.$store.state.default_address = this.$store.state.addresses.find(item => item.isDefault == 1)
			const { data: coupon } = await uni.$http.get('/coupon/get')
			this.$store.state.coupon = coupon.data
		},
		onLoad: async function() {
			await this.$onLaunched;
		},
		onShow: function() {
		},
		onHide: function() {
		},
		methods: {
			GetUserInfo() {
				var _this = this;
				uni.getUserInfo({
					desc: '登录',
					lang: 'zh_CN',
					success: (res) => {
						console.log('获取的信息', res.userInfo);
					},
					fail: (res) => {
						console.log('用户拒绝了授权');
						uni.showToast({
							title: '授权失败',
							icon: 'error',
							duration: 2000
						});
					}
				});
			},
			login() {
				let _this = this;
				uni.showLoading({
					title: '登录中...'
				});
				// 获取登录用户 code
				uni.login({
					provider: 'weixin',
					success: function(res) {
						if (res.code) {
							let code = res.code;
							uni.request({
								url: "http://127.0.0.1:8088/user/login",
								method: 'POST',
								header: {
									'Content-Type': 'application/x-www-form-urlencoded'
								},
								data: {
									code: res.code //wx.login 登录成功后的code 
								},
								success: function(cts) {
									uni.setStorageSync('user', cts.data.data.user)
									uni.setStorageSync('token', cts.data.data.token)
									console.log('token='+cts.data.data.token)
									uni.hideLoading();
									uni.switchTab({
										// 登录成功后的跳转
										url: 'pages/home/home'
									});
								}
							});
						} else {
							uni.showToast({
								title: '登录失败！',
								duration: 2000
							});
							console.log('登录失败！' + res.errMsg)
						}
					},
			  });
			}
		}
	}
</script>

<style lang="scss">
	@import '~@/static/styles/app.scss';
</style>
