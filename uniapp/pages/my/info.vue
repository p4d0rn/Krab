<template>
	<view class="container">
		<view class="brand">
			<button class="avatar-wrapper" open-type="chooseAvatar" @chooseavatar="onChooseAvatar">
				<image :src="form.avatarUrl" class="avatar"></image>
			</button>
		</view>
		<view class="user-form">
			<list-cell line-right padding="30rpx">
				<view class="form-item">
					<view class="label">昵称</view>
					<input type="nickname" class="weui-input" v-model="form.nickName" @blur="onNickName"/>
				</view>
			</list-cell>
			<list-cell line-right padding="30rpx">
				<view class="form-item">
					<view class="label">手机号码</view>
					<input type="text" v-model="form.phone" />
				</view>
			</list-cell>
		</view>
		
		<view class="save-btn">
			<button type="info" @tap="save">保存</button>
		</view>
	</view>
</template>

<script>
	import listCell from '@/components/list-cell/list-cell.vue'
	import modal from '@/components/modal/modal.vue'
	
	export default {
		components: {
			listCell,
			modal
		},
		data() {
			return {
				form: {
					avatarUrl: uni.getStorageSync('user').avatar,
					nickName: uni.getStorageSync('user').userName,
					phone: uni.getStorageSync('user').phone
				}
				
			}
		},
		methods: {
			onChooseAvatar(e) {
				console.log(e.detail.avatarUrl)
			    this.form.avatarUrl = e.detail.avatarUrl
			},
			onNickName(e) {
				console.log(e.detail.value)
				this.form.nickName = e.detail.value
			},
			async save(){
				const { data: res } = await uni.$http.post('/user/save', JSON.stringify(this.form))
				if(res.code) {
					uni.$showMsg(res.msg)
				}
			}
		}
	}
</script>

<style lang="scss" scoped>
.brand {
	background-color: $bg-color-white;
	display: flex;
	flex-direction: column;
	align-items: center;
	justify-content: center;
	padding: 40rpx 0;
	margin-bottom: 20rpx;
	
	.avatar {
		width: 200rpx;
		height: 200rpx;
		border-radius: 100%;
		margin-bottom: 30rpx;
	}
	
	.username {
		color: $font-size-medium;
		font-weight: bold;
	}
}

.user-form {
	.form-item {
		width: 100%;
		display: flex;
		align-items: center;
		
		.label {
			width: 160rpx;
		}
		
		input {
			flex: 1;
		}
		
		.radio {
			display: flex;
			margin-right: 50rpx;
			image {
				width: 40rpx;
				height: 40rpx;
				margin-right: 20rpx;
			}
		}
	}
}

.save-btn {
	padding: 0 30rpx;
	margin-top: 60rpx;
	
	button {
		width: 100%;
		font-size: $font-size-extra-lg;
	}
}

.telphone-modal {
	.header {
		display: flex;
		align-items: center;
		justify-content: center;
		position: relative;
		font-size: $font-size-extra-lg;
		font-weight: bold;
		margin-bottom: 30rpx;
		
		.close-icon {
			width: 60rpx;
			height: 60rpx;
			position: absolute;
			right: 0;
		}
	}
	
	.tips {
		color: $text-color-grey;
		line-height: 1.2rem;
		margin-bottom: 50rpx;
	}
	
	.telphone-form {
		font-size: $font-size-base;
		
		.telphone-input {
			width: 100%;
			display: flex;
			align-items: center;
			
			.prefix {
				color: $color-primary;
				display: flex;
				align-items: center;
				padding-right: 20rpx;
				position: relative;
				margin-right: 10rpx;
				
				&::after {
					content: " ";
					position: absolute;
					width: 4rpx;
					background-color: $border-color;
				}
			}
			
			input {
				flex: 1;
			}
		}
		
		.verfiycode-input {
			width: 100%;
			display: flex;
			align-items: center;
			justify-content: space-between;
			
			.countdown {
				font-size: $font-size-base;
			}
		}
	}
	
	.footer {
		margin-top: 30rpx;
		button {
			font-size: $font-size-lg;
		}
	}
}
</style>
