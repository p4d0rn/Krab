<template>
	<view class="container">
		<view class="bg-white p-30 mb-20" v-if="orderType == 'takeout'" @tap="selectAddr">
			<view class="font-size-medium font-weight-bold mb-30 d-flex align-items-center">
				<list-cell arrow line-right>
					配送地址
				</list-cell>
			</view>
			<view>收货人: {{default_address.consignee}}</view>
			<view>手机号: {{default_address.consignPhone}}</view>
			<view>收货地址:</view>
			<view>{{default_address.address}}</view>
			<view>{{default_address.detail}}</view>
		</view>
		<view class="bg-white p-30 mb-20">
			<view class="font-size-medium font-weight-bold mb-30">取餐时间</view>
			<view class="text-color-primary">预计30分钟</view>
		</view>
		<view class="bg-white pt-30 mb-20">
			<view class="font-size-medium font-weight-bold mb-30 pl-30">商品列表</view>
			<list-cell arrow line-right>
				<view class="w-100 d-flex align-items-center overflow-hidden">
					<scroll-view class="flex-fill overflow-hidden" scroll-x>
						<view class="w-100 d-flex align-items-center">
							<image :src="item.dishImg" class="pro-img" v-for="(item, index) in cart" :key="index"></image>
						</view>
					</scroll-view>
					<view class="flex-shrink-0 ml-20">共{{ cartNum }}件</view>				
				</view>
			</list-cell>
			<list-cell arrow last>
				<view class="w-100 d-flex align-items-center justify-content-between">
					<view class="uni-list">
						<view class="uni-list-cell">
							<view class="d-flex align-items-center">
								<view>蟹蟹鲤神券</view>
								<view class="coupon-label">劵</view>
							</view>
							<view class="text-color-assist" v-if="available.length == 0">暂无可用</view>
							<view class="uni-list-cell-db" v-if="available.length !=0">
								<picker @change="bindPickerChange" :value="index" :range="available">
									<view class="uni-input">{{available[index]}}</view>
								</picker>
							</view>
						</view>
					</view>
				</view>
			</list-cell>
			<list-cell arrow last>
				<view class="w-100 d-flex align-items-center justify-content-between overflow-hidden">
					<view class="flex-shrink-0">备注</view>
					<navigator hover-class="none" class="flex-fill ml-40 text-truncate text-right" open-type="navigate" url="/pages/pay/remark">{{ remark }}</navigator>
				</view>
			</list-cell>
			<list-cell last>
				<view class="w-100 d-flex justify-content-end align-items-center">
					<text class="font-size-sm">共{{ cartNum }}件商品，小计</text>
					<text class="font-size-lg font-weight-bold">￥{{ cartAmount }}</text>
				</view>
			</list-cell>
			<list-cell last v-if="available.length != 0">
				<view class="w-100 d-flex justify-content-end align-items-center">
					<text class="font-size-sm">优惠</text>
					<text class="font-size-lg font-weight-bold">￥{{ cuts[index] }}</text>
				</view>
			</list-cell>
		</view>
		<list-cell last>
			<view class="w-100 d-flex align-items-center justify-content-between">
				<view>支付方式</view>
				<view class="d-flex align-items-center">
					<image src="/static/images/order/weixin-pay.png" class="wx-pay-icon"></image>
					<view>微信</view>
				</view>
			</view>
		</list-cell>
		<view class="footer">
			<view class="mr-30">
				合计：<text class="font-size-lg font-weight-bold">￥{{ cartAmount - coupon_cut}}</text>
			</view>
			<button type="primary" @tap="submitOrder">支付</button>
		</view>
	</view>
</template>

<script>
	import listCell from '@/components/list-cell/list-cell.vue'
	
	export default {
		components: {
			listCell
		},
		data() {
			return {
				orderType: 'takin',
				cart: uni.getStorageSync('cart'),
				default_address: {},
				available: [],
				cuts: [],
				coupon_ids: [],
				coupon_cut: 0,
				index: 0,
				total: 0
			}
		},
		onLoad({type}) {
			this.orderType = type
			this.default_address = this.$store.state.default_address
			uni.getStorageSync('cart').forEach(item => {
				this.$store.state.coupon.forEach(i => {
				    if(i.dishId == item.dishId){
						this.available.push(i.couponName)
						this.cuts.push(i.cut)
						this.coupon_ids.push(i.couponId)
					}
				})
			})
			if(this.available.length != 0){
				this.coupon_cut = this.cuts[0]
			}
		},
		methods: {
			selectAddr() {
				uni.navigateTo({
					url: '/pages/addresses/addresses'
				})
			},
			setAddr(address) {
				this.default_address = address
			},
			async submitOrder() {
				let dishes = []
				this.cart.forEach((val, index, array) => {
					let dish = {}
					dish.dishId = val.dishId
					dish.dishName = val.dishName
					dishes.push({"dish": dish, "quantity": val.number})
				})
				let bonus = Math.ceil(((this.total - this.coupon_cut)*1000*0.15)/1000)
				const { data: res } = await uni.$http.post('/order/put?addr=' + (this.orderType=='takeout'? this.default_address.addrId: 0) + ('&coupon=' + (this.available.length!=0 ? this.coupon_ids[this.index]: 0)), JSON.stringify({"dishes": dishes, "remarks": this.$store.state.remark}))
				if(res.code) {
					uni.$showMsg(res.msg + `\n获得${bonus}积分`)
				}
				if (res.code == 1) {
					setTimeout(function(){
						uni.redirectTo({
							url: '/pages/order/order'
						})
					}, 1500)
				}
				var user = uni.getStorageSync('user')
				user.points = user.points + 
				uni.setStorageSync('user', user)
				if(this.available.length != 0) {
					const { data: coupon } = await uni.$http.get('/coupon/get')
					this.$store.state.coupon = coupon.data
				}
			},
			bindPickerChange: function(e) {
			    this.index = e.detail.value
				this.coupon_cut = this.cuts[this.index]
			}
		},
		computed: {
			cartNum() {
				return this.cart.reduce((acc, cur) => acc + cur.number, 0)
			},
			cartAmount() {
				this.total = this.cart.reduce((acc, cur) => acc + cur.number * cur.dishPrice, 0)
				return this.total
			},
			remark() {
				return this.$store.state.remark
			}
		}
	}
</script>

<style lang="scss" scoped>
	.container {
		height: auto;
		padding-bottom: 120rpx;
	}
	
	.pro-img {
		width: 120rpx;
		height: 90rpx;
		flex-shrink: 0;
	}
	
	.coupon-label {
		background-color: $color-primary;
		color: #FFFFFF;
		font-size: 18rpx;
		line-height: 0.9rem;
		width: 0.9rem;
		height: 0.9rem;
		margin-left: 10rpx;
		text-align: center;
	}
	
	.wx-pay-icon {
		width: 40rpx;
		height: 40rpx;
		margin-right: 10rpx;
	}
	
	.footer {
		background-color: #FFFFFF;
		z-index: 10;
		position: fixed;
		bottom: 0;
		left: 0;
		width: 100%;
		height: 100rpx;
		display: flex;
		justify-content: flex-end;
		align-items: center;
		
		button {
			width: 250rpx;
			text-align: center;
			padding: 0;
			height: 100%;
			line-height: 100rpx;
			border-radius: 0 !important;
			font-size: $font-size-lg;
		}
	}
</style>
