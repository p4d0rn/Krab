<template>
	<view class="container">
		<!-- 可用积分 begin -->
		<view class="integrals-section">
			<view class="integrals">
				<view class="title">可用积分</view>
				<view class="value">{{ points }}</view>
			</view>
			<view class="menu">
				<view class="item" @tap="integralScores">积分明细</view>
				<view class="divider"></view>
				<view class="item">兑换记录</view>
			</view>
		</view>
		<!-- 可用积分 end -->
		<view class="integrals-mall">
			<view class="navbar">
				<view class="item" :class="{active: !memberType}" @tap="handleSwitchMemberType(0)">蟹蟹鲤神券</view>
				<view class="item" :class="{active: memberType}" @tap="handleSwitchMemberType(1)">商品专区</view>
			</view>
			<view class="list-wrapper">
				<view class="list" v-if="productList.length">
					<view class="item" v-for="(product, index) in productList" :key="index" @tap="detail(product)">
						<image :src="product.image_url" class="image" mode="widthFix"></image>
						<view class="info">
							<view class="title">{{ product.name }}</view>
							<view class="score">
								<view class="value">{{ product.score }}</view>
								<view>积分</view>
							</view>
						</view>
					</view>
				</view>
				<view class="no-order-content" v-else>
					<image src="/static/images/mall/me_pointmall_img_nogift.png"></image>
					<view>暂无神券上架</view>
				</view>
			</view>
			<nomore text="没有更多了" :visible="!loading && productList.length > 0"></nomore>
		</view>
	</view>
</template>

<script>
	import nomore from '@/components/nomore/nomore.vue'
	
	export default {
		components: {
			nomore
		},
		data() {
			return {
				loading: false,
				productList: [],
				coupons: [],
				items: [],
				memberType: 0,
				points: 0
			}
		},
		onShow() {
			this.points = uni.getStorageSync('user').points
		},
		async onLoad() {
			await this.getProductList()
			this.points = uni.getStorageSync('user').points
		},
		methods: {
			detail(product) {
				uni.navigateTo({
					url: '/pages/integrals/detail?id=' + product.id + '&type=' + this.memberType
				})
			},
			handleSwitchMemberType(memberType) {
				this.memberType = memberType
				if(this.memberType == 0) {
					this.productList = this.coupons
				} else {
					this.productList = this.items
				}
			},
			async getProductList() {
				const { data: res } = await uni.$http.get('/products/get')
				this.coupons = res.data.coupons
				this.items = res.data.items
				uni.setStorageSync('products', res.data)
				this.handleSwitchMemberType(0)
			},
			integralScores() {
				uni.navigateTo({
					url: '/pages/integrals/scores'
				})
			}
		}
	}
</script>

<style lang="scss" scoped>
.container {
	overflow: auto;
}

.integrals-section {
	position: relative;
	margin-top: -100rpx;
	background: url('@/static/images/mall/me_img_banner_mall.png') no-repeat;
	background-size: 100% 100%;
	width: 100%;
	height: 420rpx;
	padding: 30rpx 40rpx;
	padding-top: 130rpx;
	display: flex;
	flex-direction: column;
	justify-content: space-between;
	align-items: flex-start;
	margin-bottom: 20rpx;
	
	.integrals {
		display: flex;
		flex-direction: column;
		align-items: center;
		font-size: $font-size-sm;
		color: $text-color-grey;
		
		.value {
			font-family: 'neutra';
			font-size: 72rpx;
			margin-top: 10rpx;
		}
	}
	
	.menu {
		display: flex;
		align-items: center;
		font-size: $font-size-base;
		.divider {
			height: 20rpx;
			width: 1rpx;
			background-color: $text-color-base;
			margin: 0 30rpx;
		}
	}
}

.integrals-mall {
	.navbar {
		position: sticky;
		background-color: $bg-color-white;
		top: 0;
		z-index: 10;
		border-bottom: 1rpx solid rgba($color: $border-color, $alpha: 0.3);
		font-size: $font-size-base;
		color: $text-color-assist;
		height: 110rpx;
		display: flex;
		align-items: stretch;
		
		.item {
			padding: 0 30rpx;
			position: relative;
			display: flex;
			flex-direction: column;
			align-items: center;
			justify-content: center;
			font-size: $font-size-sm;
			
			&.active {
				color: $color-primary;
				font-size: $font-size-base;
				
				&:after {
					content: ' ';
					height: 4rpx;
					background-color: $color-primary;
					width: 100rpx;
					margin-top: 10rpx;
				}
			}
		}
	}
	
	.list-wrapper {
		background-color: $bg-color-white;
		
		.list {
			display: flex;
			flex-wrap: wrap;
			padding-bottom: 50rpx;
			.item {
				width: 375rpx;
				
				.image {
					width: 100%;
				}
				
				.info {
					display: flex;
					flex-direction: column;
					padding: 0 20rpx;
					.title {
						font-size: $font-size-base;
						margin-bottom: 30rpx;
					}
					.score {
						display: flex;
						align-items: baseline;
						font-size: $font-size-sm;
						color: $text-color-assist;
						
						.value {
							color: $color-primary;
							font-size: $font-size-lg;
						}
					}
				}
			}
		}

		.no-order-content {
			height: calc(100vh - 550rpx);
			display: flex;
			flex-direction: column;
			align-items: center;
			justify-content: center;
			font-size: $font-size-lg;
			
			image {
				width: 360rpx;
				height: 300rpx;
				margin-bottom: $spacing-row-lg;
			}
		}
		
		.star-member-intro {
			width: 100%;
			padding: 30px 25px 0;
			
			.content {
				background-color: #fefbf0;
				border-radius: 50rem !important;
				line-height: 2.6rem;
				display: flex;
				padding: 0 30rpx;
				font-size: $font-size-sm;
				
				.image {
					position: relative;
					width: 146rpx;
					margin-top: -40rpx;
				}
				
				.to-see {
					margin-left: 20rpx;
					display: flex;
					align-items: center;
					color: $color-primary;
					
					image {
						width: 16rpx;
						margin-left: 20rpx;
					}
				}
			}
		}
	}
}
</style>
