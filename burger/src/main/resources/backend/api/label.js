// 查询列表接口
// params: {page:int, pageSize:int}
// return: 数组，{labelName, labelId, labelColor}
const getLabel = (params) => {
    return $axios({
        url: '/label/page',
        method: 'get',
        params
    })
}

// 删除当前列的接口
const deleLabel = (ids) => {
    return $axios({
        url: '/label/del',
        method: 'get',
        params: { ids }
    })
}

// 新增接口
// params: {labelName, labelColor}
const addLabel = (params) => {
    return $axios({
        url: '/label/add',
        method: 'post',
        data: { ...params }
    })
}

// 修改接口
// params: {labelName, labelId, labelColor}
const editLabel = (params) => {
    return $axios({
        url: '/label/edit',
        method: 'post',
        data: { ...params }
    })
}