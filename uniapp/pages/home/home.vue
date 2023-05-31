<template>
	<view class="container">
		<swiper class="banner-swiper" circular autoplay :interval="5000" :duration="1000">
			<swiper-item class="banner-swiper-item" v-for="(item, index) in swipers" :key="index">
				<image :src="item" mode="widthFix"></image>
			</swiper-item>
		</swiper>
		<view class="content">
			<!-- section-1 begin -->
			<view class="section-1">
				<navigator class="item" open-type="switchTab" url="/pages/index/index" @tap="goOrderIn" hover-class="none">
					<image src="/static/images/home/home_icon_ziqu1.png" mode="widthFix"></image>
					<view class="wenyue-font">门店自取</view>
					<view class="text-color-assist">下单免排队</view>
				</navigator>
				<navigator class="item" open-type="switchTab" url="/pages/index/index" @tap="goOrderOut" hover-class="none">
					<image src="/static/images/home/home_icon_waimai1.png" mode="widthFix"></image>
					<view class="wenyue-font">外卖</view>
					<view class="text-color-assist">海绵宝宝专送</view>
				</navigator>
			</view>
			<!-- section-2 end -->
			<!-- section-2 begin -->
			<view class="section-2">
				<navigator class="item" open-type="switchTab" url="/pages/integrals/mall" hover-class="none">
					<view class="title">
						<image src="/static/images/home/home_icon_baihuo1.png"></image>
						<view>蟹堡百货</view>
					</view>
					<view class="tips">神仙商品 立即选购</view>
				</navigator>
				<navigator class="item" open-type="navigate" url="null" hover-class="none">
					<view class="title">
						<image src="/static/images/home/home_icon_qiye1.png"></image>
						<view>企业团餐</view>
					</view>
					<view class="tips">50份起送</view>
				</navigator>
			</view>
			<!-- section-2 end -->
			<view class="section-3">
				<navigator class="my-integral" open-type="navigate" url="/pages/integrals/mall" hover-class="none">
					<view class="integrals">
						<view>我的积分</view>
						<view class="neutra-font">{{ points }}</view>
					</view>
					<view class="tips">
						可兑换蟹蟹鲤神劵和丰富周边
					</view>
				</navigator>
				<button type="primary" class="font-size-lg" hover-class="none" v-if="!isSingin" @tap="signin">签到</button>
				<button type="default" class="font-size-lg" hover-class="none" v-if="isSingin">已签到</button>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		onShow() {
			this.points = uni.getStorageSync('user').points
		},
		data() {
			return {
				swipers: [
					"https://officialwebsitestorage.blob.core.chinacloudapi.cn/public/upload/photo_db/2023/03/29/202303291047249759/202303291047249759_640_398.jpg?202303291045298272&rand=0827",
					"https://officialwebsitestorage.blob.core.chinacloudapi.cn/public/upload/photo_db/2023/03/13/202303131157393380/202303131157393380_640_398.jpg?202303131157548233&rand=0827",
					"https://officialwebsitestorage.blob.core.chinacloudapi.cn/public/upload/photo_db/2023/05/10/202305101845248453/202305101845248453_640_398.jpg?&rand=0827"
				],
				isSingin: false,
				points: 0
			}
		},
		methods: {
			goOrderIn(){
				uni.setStorageSync('take_flag', 'takein');
			},
			goOrderOut(){
				uni.setStorageSync('take_flag', 'takeout');
			},
			async signin() {
				this.isSingin = true
				const { data: checkin } = await uni.$http.get('/checkin')
				uni.showToast({
					title: checkin.msg
				})
				if(checkin.code == 1){
					this.points += 1
					var user = uni.getStorageSync('user')
					user.points = this.points
					uni.setStorageSync('user', user)
				}
			}
		}
	}
</script>

<style lang="scss" scoped>
	page {
		max-height: 100%;
	}
	
	.banner-swiper {
		width: 100%;
		height: 600rpx;
		
		.banner-swiper-item {
			image {
				width: 100%;
			}
		}
	}
	
	.content {
		width: 100%;
		padding: 0 30rpx;
		position: relative;
	}
	
	.section-1 {
		position: relative;
		background-color: $bg-color-white;
		margin-top: -60rpx;
		border-radius: $border-radius-lg;
		padding: 60rpx 0;
		display: flex;
		margin-bottom: 30rpx;
		box-shadow: $box-shadow;
		
		.item {
			flex: 1;
			flex-shrink: 0;
			display: flex;
			flex-direction: column;
			align-items: center;
			position: relative;
			
			&:nth-child(1):after {
				content: '';
				position: absolute;
				right: 0;
				top: 0;
				bottom: 0;
				width: 2rpx;
				background-color: $border-color;
			}
			
			image {
				width: 100rpx;
				margin-bottom: 20rpx;
			}
			
			.wenyue-font {
				font-size: 48rpx;
				margin-bottom: 10rpx;
			}	
		}
	}
	
	.section-2 {
		display: flex;
		justify-content: space-between;
		margin-bottom: 30rpx;
		
		.item {
			width: 335rpx;
			background-color: #EAEBEC;
			padding: $spacing-row-lg 0;
			display: flex;
			flex-direction: column;
			align-items: center;
			justify-content: center;
			border-radius: $border-radius-lg;
			
			.title {
				width: 100%;
				display: flex;
				align-items: center;
				justify-content: center;
				font-size: $font-size-lg;
				
				image {
					width: 60rpx;
					height: 60rpx;
					margin-right: 10rpx;
				}
			}
			
			.tips {
				color: $text-color-assist;
				font-size: $font-size-base;
			}
		}
	}
	
	.section-3 {
		margin-bottom: 30rpx;
		display: flex;
		justify-content: space-between;
		align-items: center;
		font-size: $font-size-base;
		color: $text-color-assist;
		padding: 0 10rpx;
		
		.my-integral {
			flex: 1;
			display: flex;
			flex-direction: column;
			
			.integrals {
				display: flex;
				align-items: center;
				font-size: $font-size-lg;
				color: $text-color-base;
				margin-bottom: 10rpx;
				
				.neutra-font {
					margin-left: 10rpx;
					font-size: 42rpx;
				}
			}
		}
		
		.my-code {
			display: flex;
			flex-direction: column;
			align-items: center;
			padding: 0 30rpx;
			position: relative;
			
			image {
				width: 60rpx;
				height: 60rpx;
				margin-bottom: $spacing-col-sm;
			}
			
			&:before {
				content: " ";
				position: absolute;
				left: 0;
				top: 0;
				bottom: 0;
				border-left: 1rpx solid rgba($color: $border-color, $alpha: 0.6);
			}
		}
	}
</style>
