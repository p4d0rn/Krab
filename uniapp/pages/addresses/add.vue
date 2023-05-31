<template>
	<view class="container">
		<view class="address-form">
			<list-cell padding="30rpx">
				<view class="form-item">
					<view class="label">收货人</view>
					<input type="text" v-model="form.consignee" placeholder="请填写收货人的姓名" placeholder-class="placeholder"/>
				</view>
			</list-cell>
			<list-cell padding="30rpx">
				<view class="form-item">
					<view class="label">性别</view>
					<view class="radio" @tap="form.gender = !form.gender">
						<image :src="form.gender ? '/static/images/common/gouxuankuang.png' : '/static/images/common/round-black-selected.png'"></image>
						<view>先生</view>
					</view>
					<view class="radio" @tap="form.gender = !form.gender">
						<image :src="!form.gender ? '/static/images/common/gouxuankuang.png' : '/static/images/common/round-black-selected.png'"></image>
						<view>女士</view>
					</view>
				</view>
			</list-cell>
			<list-cell padding="30rpx">
				<view class="form-item">
					<view class="label">手机号</view>
					<input type="text" v-model="form.consignPhone" placeholder="请填写收货手机号码" placeholder-class="placeholder"/>
				</view>
			</list-cell>
			<list-cell padding="30rpx">
				<view class="form-item">
					<view class="label">收货地址</view>
					<input type="text" @tap="chooseLocation" v-model="form.address" placeholder="点击选择" placeholder-class="placeholder"/>
					<image src="/static/images/common/icon_jump_black3.png" class="jump-icon"></image>
				</view>
			</list-cell>
			<list-cell padding="30rpx">
				<view class="form-item">
					<view class="label">门牌号</view>
					<input type="text" v-model="form.detail" placeholder="例:B座6楼606室" placeholder-class="placeholder"/>
				</view>
			</list-cell>
			<list-cell padding="30rpx" last>
				<view class="form-item">
					<view class="radio" @tap="form.isDefault = !form.isDefault">
						<image :src="!form.isDefault ? '/static/images/common/gouxuankuang.png' : '/static/images/common/round-black-selected.png'"></image>
						<view>设为默认地址</view>
					</view>
				</view>
			</list-cell>
		</view>
		
		<view class="save-btn">
			<button type="info" @tap="save">添加</button>
		</view>
		<view class="attention">
			<view>
				注意事项:<br/>
			</view>
			<view>
				1、请认真填写, 该信息仅用于商品配送时使用<br/>
				2、如需删除请及时联系开发者<br/>
				3、收货人、性别、手机号、收货地址、门牌号仅在商品配送时使用、不做其他用途<br/>
				4、如不信任，则勿轻易填写<br/>
			</view>
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
				form: {
					addrId: null,
					consignee: '',
					gender: 0,
					consignPhone: '',
					detail: '',
					isDefault: 0,
					completeAddress: '',
					address: ''
				}
			}
		},
		onLoad({id}) {
			if(id) {
				this.form = this.$store.state.addresses.find(item => item.addrId == id)
			}
		},
		methods: {
			chooseLocation() {
				uni.chooseLocation({
					success: res => {
						const {errMsg, address , latitude, longitude, name} = res
						// name 大概地址
						// address 详细地址
						if(errMsg === "chooseLocation:ok") {
							this.form = Object.assign(
								this.form, 
								{completeAddress: address, address: name},
							)
						}
					}
				})
			},
			async save() {
				const { data: res } = await uni.$http.post('/addr/add', JSON.stringify(this.form))
				if(res.code) {
					uni.$showMsg(res.msg)
				}
				if (res.code == 1) {
					setTimeout(function(){
						uni.navigateTo({
							url: '/pages/addresses/addresses'
						})
					}, 1500)
				}
			}
		},
	}
</script>

<style lang="scss" scoped>
	.attention {
		font-weight:bold;
		background:#b1b2a4;
		align-items: center;
	}
	
	.address-form {
	margin-top: 20rpx;
	
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
		
		.jump-icon {
			width: 30rpx;
			height: 48rpx;
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
</style>
