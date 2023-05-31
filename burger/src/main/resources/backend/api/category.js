// 查询列表接口
// params: {page, pageSize, input:str or undefined}
// return: 数组，{cateName, cateId, cateImg}
const getCategoryPage = (params) => {
  return $axios({
    url: '/category/page',
    method: 'get',
    params
  })
}

// 编辑页面反查详情接口
// return: {cateName, cateId, cateImg}
const queryCategoryById = (id) => {
  return $axios({
    url: `/category/${id}`,
    method: 'get'
  })
}

// 删除当前列的接口
const deleCategory = (id) => {
  return $axios({
    url: `/category/del/${id}`,
    method: 'get'
  })
}

// 修改接口
// params: {cateId, cateName, cateImg}
const editCategory = (params) => {
  return $axios({
    url: '/category/edit',
    method: 'post',
    data: { ...params }
  })
}

// 新增接口
// params: {cateName, cateImg}
const addCategory = (params) => {
  return $axios({
    url: '/category/add',
    method: 'post',
    data: { ...params }
  })
}