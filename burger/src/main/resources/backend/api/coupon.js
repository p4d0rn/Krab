// 查询列表接口
// params: {page:int, pageSize:int}
// return: {couponId, couponName, cut, couponDesc, dishId}
const getCoupon = (params) => {
    return $axios({
        url: '/coupon/page',
        method: 'get',
        params
    })
}

// 删除当前列的接口
const deleCoupon = (ids) => {
    return $axios({
        url: '/coupon/del',
        method: 'get',
        params: { ids }
    })
}

// 新增接口
// params: {couponName, cut, couponDesc, dishId， coupon_img}
const addCoupon = (params) => {
    return $axios({
        url: '/coupon/add',
        method: 'post',
        data: { ...params }
    })
}

// 修改接口
// params: {couponId, couponName, cut, couponDesc, dishId}
const editCoupon = (params) => {
    return $axios({
        url: '/coupon/edit',
        method: 'post',
        data: { ...params }
    })
}

// 编辑页面反查详情接口
// return: {couponId, couponName, couponDesc, couponId, cut, dishId}
const queryCouponById = (id) => {
    return $axios({
        url: `/coupon/${id}`,
        method: 'get'
    })
}