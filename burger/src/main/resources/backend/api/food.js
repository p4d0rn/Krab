// 查询列表接口
// params: {page:int, pageSize:int, name:str or undefined}
// return: 数组，{dishName, dishImg, dishPrice, dishStatus, dishId, cateName}
const getDishPage = (params) => {
  return $axios({
    url: '/dish/page',
    method: 'get',
    params
  })
}

// 删除接口
// params: [id1, id2, ...]
const deleteDish = (ids) => {
  return $axios({
    url: '/dish/del',
    method: 'post',
    data: {ids}
  })
}

// 修改接口
// params: {cateId, dishName, dishPrice, description, dishImg}
const editDish = (params) => {
  console.log(ids)
  return $axios({
    url: '/dish/edit',
    method: 'post',
    data: { ...params }
  })
}

// 新增接口
// params: {dishName, dishPrice, dishImg, description}
const addDish = (params) => {
  return $axios({
    url: '/dish/add',
    method: 'post',
    data: { ...params }
  })
}

// 查询详情
// parmas: dishId
// return: {dishName, dishPrice, dishImg, description}
const queryDishById = (id) => {
  return $axios({
    url: `/dish/${id}`,
    method: 'get'
  })
}

// 获取菜品分类列表
// params: {page=1, pageSize=1000}
// return: 数组，{cateName, cateId}
const getCategoryList = (params) => {
  return $axios({
    url: '/category/page',
    method: 'get',
    params
  })
}

// 查菜品列表的接口
const queryDishList = (params) => {
  return $axios({
    url: '/dish/list',
    method: 'get',
    params
  })
}

// 起售停售---批量起售停售接口  不要了
// params: {id: [id1. id2, ...], status: '0' or '1'}
const dishStatusByStatus = (params) => {
  return $axios({
    url: `/dish/status/${params.status}`,
    method: 'post',
    params: { ids: params.id }
  })
}