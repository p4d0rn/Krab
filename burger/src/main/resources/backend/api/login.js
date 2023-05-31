function loginApi(data) {
    return $axios({
        'url': '/login',
        'method': 'post',
        data
    })
}

function logoutApi() {
    return $axios({
        'url': '/logout',
        'method': 'post',
    })
}

