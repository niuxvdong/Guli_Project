import request from '@/utils/request'

export default {

    // 1. 注册发送验证码
    sendCode(mobile) {
        return request({
            url: `/edumsm/msm/send/${mobile}`,
            method: 'get'
        })
    },
    // 2. 注册方法
    registerMember(formItem) {
        return request({
            url: `/educenter/member/register`,
            method: 'post',
            data: formItem
        })
    }
}
