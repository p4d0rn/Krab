<template>
	<view class="container" v-if="product">
		<image :src="product.image_url" class="w-100" mode="widthFix"></image>
		<view class="header">
			<view class="title">{{ product.name }}</view>
			<view class="subtitle">
				<view class="score">{{ product.score }}</view>
				<view>积分</view>
			</view>
		</view>
		<view class="content">
			<list-cell :padding="padding" :hover="false" last>
				<view class="row">
					<view class="label">礼品介绍</view>
					<view class="text">{{ product.remark }}</view>
				</view>
			</list-cell>
		</view>
		<view class="content">
			<list-cell :padding="padding" :hover="false" last>
				<view class="bg-white p-30 mb-20" v-if="type == 1" @tap="selectAddr">
					> 选择配送地址
				</view>
			</list-cell>
		</view>
		<view class="bottom disabled" v-if="points < product.score">积分不足</view>
		<view class="bottom" v-else @tap="buy">兑换</view>
	</view>
</template>

<script>
	import listCell from '../../components/list-cell/list-cell.vue'
	
	export default {
		components: {
			listCell
		},
		data() {
			return {
				product: {},
				id: null,
				type: null,
				points: 0,
				address: null,
				padding: "20rpx"
			}
		},
		async onLoad({id, type}) {
			if(type == 0){  // 优惠券
				this.product = uni.getStorageSync('products').coupons.find(i => i.id == id)
			} else {  // 商品
				this.product = uni.getStorageSync('products').items.find(i => i.id == id)
			}
			this.points = uni.getStorageSync('user').points
			this.id = id
			this.type = type
		},
		methods: {
			async buy(){
				uni.showModal({
				    title: '提示',
				    content: '是否确认兑换',
				    success: async function (res) {
				        if (res.confirm) {
							var that = this
							if(that.type == 1 && that.address == null){
								return uni.showToast({
									title: '请先选择地址',
									icon: 'none',
									duration: 2000
								})
							}
				            const { data: response } = await uni.$http.get(`/products/buy?id=${that.id}&type=${that.type}&addr=${that.type==0 ? 0:that.address.addrId}`)
							if(response.code) {
								uni.$showMsg(response.msg)
							}
							if (response.code == 1) {
								var user = uni.getStorageSync('user')
								user.points = user.points - that.product.score
								uni.setStorageSync('user', user)
								setTimeout(function(){
									uni.redirectTo({
										url: '/pages/order/order?from_store=1'
									})
								}, 1500)
							}
				        }
				    }.bind(this)
				})
			},
			selectAddr() {
				uni.navigateTo({
					url: '/pages/addresses/addresses'
				})
			},
			setAddr(address) {
				console.log("called")
				this.address = address
			}
		}
	}
</script>

<style lang="scss" scoped>
	.container {
		height: auto;
		background-color: #FFFFFF;
	}
	
	.header {
		padding: 20rpx 30rpx;
		display: flex;
		flex-direction: column;
		border-bottom: 1rpx solid rgba($color: $border-color, $alpha: 0.3);
		
		.title {
			font-size: $font-size-lg;
			margin-bottom: 10rpx;
		}
		.subtitle {
			display: flex;
			align-items: baseline;
			font-size: $font-size-sm;
			color: $text-color-assist;
			.score {
				font-size: $font-size-lg;
				color: $color-primary;
				margin-right: 20rpx;
			}
		}
	}
	
	.content {
		padding-bottom: 100rpx;
		
		.row {
			display: flex;
			align-items: baseline;
			
			.label {
				width: 120rpx;
				font-size: $font-size-medium;
				margin-right: 20rpx;
			}
			
			.text {
				flex: 1;
				font-size: $font-size-sm;
				color: $text-color-assist;
			}		
		}
	}
	
	.bottom {
		position: fixed;
		bottom: 0;
		width: 100%;
		height: 100rpx;
		display: flex;
		align-items: center;
		justify-content: center;
		font-size: $font-size-lg;
		color: $bg-color-white;
		background-color: $color-primary;
		
		&.disabled {
			background-color: #e9d5bb;
		}
	}
</style>
