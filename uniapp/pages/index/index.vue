<template>
	<view class="container">
		<view class="header">
			<view class="center">
				<view class="store">
					<view class="title">
						<image :src="orderType == 'takein' ? 
								'/static/images/common/star_normal.png' : 
								'/static/images/order/order_icon_address.png'" 
								class="left-icon" />
						<!-- 为了测试方便，这里使用静态店铺数据 -->
						<view class="address">{{ orderType == 'takeout' ? address.complete_address : '中心城店' }}</view>
						<image src="/static/images/common/black_arrow_right.png" class="right-icon"></image>
					</view>
					<!-- 外卖&自取switch begin -->
					<view class="buttons">
						<button type="default" class="button" 
								:class="{active: orderType == 'takein'}" plain 
								hover-class="none" @tap="switchOrderType">
							自取
						</button>
						<button type="default" class="button" 
								:class="{active: orderType == 'takeout'}" plain 
								hover-class="none" @tap="switchOrderType">
							外卖
						</button>
					</view>
					<!-- 外卖&自取switch end -->
				</view>
				<view class="location">距离您622m</view>
			</view>
		</view>
		<!-- 滚动公告栏 end -->
		<view class="main">
			<!-- 左侧菜单 begin -->
			<scroll-view class="menu-bar" scroll-y scroll-with-animation>
				<view class="wrapper">
					<view class="menu-item" @tap="handleMenuSelected(category.cateId)" 
						  :class="{active: currentCategoryId == category.id}" v-for="(category, index) in categories" :key="index">
						<image :src="category.cateImg" class="image" mode="widthFix"></image>
						<view class="title">{{ category.cateName }}</view>
					</view>
				</view>
			</scroll-view>
			<!-- 左侧菜单 end -->
			<!-- 右侧商品列表 begin -->
			<scroll-view class="product-section" scroll-y scroll-with-animation :scroll-top="productsScrollTop" @scroll="productsScroll">
				<view class="wrapper">
					<view id="ads">
						<!-- 广告栏 begin -->
						<swiper class="ads1" :indicator-dots="true" :autoplay="true" :interval="3000" :duration="1000" circular>
							<swiper-item v-for="(ad, index) in ads" :key="index">
								<image :src="ad" class="w-100" mode="widthFix"></image>
							</swiper-item>
						</swiper>
						<!-- 广告栏 end -->
					</view>
					<!-- 商品 begin -->
					<view class="products-list" v-for="(category, index) in categories" :key="index" :id="`products-${category.cateId}`">
						<view class="category-name">{{ category.cateName }}</view>
						<view class="products">
							<view class="product" v-for="(product, key) in category.products" :key="key" 
								@tap="showProductDetailModal(product)">
								<image :src="product.dishImg" mode="widthFix" class="image"></image>
								<view class="content">
									<view class="name">{{ product.dishName }}</view>
									<view class="labels">
										<view class="label" 
										:style="{color: labelColor, background: $util.hexToRgba(label.labelColor, 0.2)}"
										 v-for="label in product.labels" :key="label.labelId">{{ label.labelName }}</view>
										<view class="label" style="color: aliceblue;background:#c85662">
											{{ product.remark }}
										</view>
									</view>
									<view class="description">{{ product.description }}</view>
									<view class="price">
										<view>￥{{ product.dishPrice }}</view>
										<actions :materials-btn="false" 
												@materials="showProductDetailModal(product)" 
												:number="productCartNum(product.dishId)"
												@add="handleAddToCart(product)" 
												@minus="handleMinusFromCart(product)" />
									</view>
								</view>
							</view>
						</view>
					</view>
					<!-- 商品 end -->
				</view>
			</scroll-view>
			<!-- 右侧商品列表 end -->
		</view>
		<!-- 商品详情 modal begin -->
		<product-modal :product="product" 
						:visible="productModalVisible" 
						@cancel="closeProductDetailModal" 
						@add-to-cart="handleAddToCartInModal" 
		/>
		<!-- 商品详情 modal end -->
		<!-- 购物车栏 begin -->
		<cart-bar :cart="cart" 
				  @add="handleAddToCart" 
				  @minus="handleMinusFromCart"
				  @clear="clearCart"
				  @pay="pay"
		/>
		<!-- 购物车栏 end -->
		<!-- <search :show="showSearch" :categories="categories" @hide="showSearch=false" @choose="showProductDetailModal"></search> -->
	</view>
</template>

<script>
	import {mapState, mapMutations} from 'vuex'
	import Actions from './components/actions/actions.vue'
	import CartBar from './components/cartbar/cartbar.vue'
	import ProductModal from './components/product-modal/product-modal.vue'
	import cartPopup from './components/cart-popup/cart-popup.vue'
	
	export default {
		components: {
			Actions,
			CartBar,
			ProductModal,
			cartPopup,
		},
		data() {
			return {
				categories: [],
				cart: [],
				product: {},
				currentCategoryId: 0,
				notices: [],
				ads: [
					"https://assets.nick.com/uri/mgid:arc:imageassetref:web.b.nick.tv:f37ef7a3-8b23-4f8c-92e5-2ab33e8e61ad?quality=0.7&gen=ntrn&legacyStatusCode=true",
					"https://assets.nick.com/uri/mgid:arc:imageassetref:web.b.nick.tv:86917b23-91e0-4063-b804-1bc919148aca?quality=0.7&gen=ntrn&legacyStatusCode=true",
					//"http://127.0.0.1:8088/static/imgs/burger.png"
					"https://officialwebsitestorage.blob.core.chinacloudapi.cn/public/upload/photo_db/2023/04/13/202304132106464958/202304132106464958_640_398.jpg?&rand=0827"
				],
				productModalVisible: false,
				cartPopupShow: false,
				productsScrollTop: 0,
				showSearch: false
			}
		},
		computed: {
			...mapState(['orderType', 'address']),
			productCartNum() {	//计算单个产品添加到购物车的数量
				return id => this.cart.reduce((acc, cur) => {
						if(cur.dishId === id) {
							return acc += cur.number
						}
						return acc
					}, 0)
			}
		},
		onLoad() {
			this.getMenu()
		},
		onShow() {
			this.modeSet()
		},
		methods: {
			...mapMutations(['SET_ORDER_TYPE']),
			
			async modeSet(){
				const value = await uni.getStorageSync('take_flag')
				this.SET_ORDER_TYPE(value)
			},
			async getMenu() {
				const { data: res } = await uni.$http.get('/menu')
				if (res.code != 1)
					return uni.$showMsg()
				this.categories = res.data
				this.currentCategoryId = this.categories.length && this.categories[0].cateId
				this.$nextTick(() => this.calcSize())
			},
			
			switchOrderType() {
				if(this.orderType === 'takein') {
					this.SET_ORDER_TYPE('takeout')
				} else {
					this.SET_ORDER_TYPE('takein')
				}
			},
			handleAddToCart(product) {	//添加到购物车
				// 查看购物车是否已经存在此产品
				const index = this.cart.findIndex(item => {
					if(false) { //!product.is_single
						// 组合产品需要都匹配id和材料清单
						return (item.dishId == product.dishId) && (item.materials_text == product.materials_text)
					} else {
						// 单个产品只需id匹配
						return item.dishId === product.dishId
					}
				})
				
				if(index > -1) {
					// 若购物车已存在此产品，数目+1
					this.cart[index].number += (product.number || 1)
					return
				}
				
				// 若购物车不存在该产品，则push进购物车
				this.cart.push({
					dishId: product.dishId,
					cateId: product.cateId,
					dishName: product.dishName,
					dishPrice: product.dishPrice,
					number: product.number || 1,
					dishImg: product.dishImg,
					is_single: true,  //product.is_single,
					materials_text: product.materials_text || ''
				})
			},
			handleMinusFromCart(product) { //从购物车减商品
				let index
				if(true) {  // product.is_single
					// 单个产品直接查找id
				   index = this.cart.findIndex(item => item.dishId == product.dishId)
				} else {
					// 组合产品查找id和材料清单
				   index = this.cart.findIndex(item => (item.dishId == product.dishId) && (item.materials_text == product.materials_text))
				}
				this.cart[index].number -= 1 
				if(this.cart[index].number <= 0) {
					this.cart.splice(index, 1) // 若-1后数量小于0则从购物车中删除
				}
			},
			showProductDetailModal(product) {
				this.product = product
				this.productModalVisible = true
			},
			handleAddToCartInModal(product) {
				this.handleAddToCart(product)
				this.closeProductDetailModal()
			},
			closeProductDetailModal() {
				this.productModalVisible = false
				this.product = {}
			},
			openCartDetailsPopup() {
				this.$refs['cartPopup'].open()
			},
			clearCart() {
				this.cart = []
			},
			handleMenuSelected(id) {
				this.productsScrollTop = this.categories.find(item => item.cateId == id).top
				this.$nextTick(() => this.currentCategoryId = id)
			},
			productsScroll({detail}) {
				const {scrollTop} = detail
				let tabs = this.categories.filter(item=> item.top <= scrollTop).reverse()
				if(tabs.length > 0){
					this.currentCategoryId = tabs[0].cateId
				}
			},
			calcSize() {
				let h = 0
				let view = uni.createSelectorQuery().select('#ads')
				view.fields({
					size: true
				}, data => {
					h += Math.floor(data.height)
				}).exec()
				this.categories.forEach(item => {
					let view = uni.createSelectorQuery().select(`#products-${item.cateId}`)
					view.fields({
						size: true
					}, data => {
						item.top = h
						h += Math.floor(data.height)
						item.bottom = h
					}).exec()
				})
			},
			pay() {
				uni.setStorageSync('cart', this.cart)
				uni.navigateTo({
					url: '/pages/pay/pay?type=' + this.orderType
				})
			}
		}
	}
</script>

<style lang="scss">
	@import './index.scss';
</style>
