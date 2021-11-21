import request from '@/utils/request'

export default {

    // 1. 登录方法
    submitLoginUser(userInfo) {
        return request({
            url: `/educenter/member/login`,
            method: 'post',
            data: userInfo
        })
    },
    // 2. 从token获取用户信息
    getLoginUserInfo(){
        return request({
            url: `/educenter/member/getMemberInfo`,
            method: 'get',
        })
    }
}
