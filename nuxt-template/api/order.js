import request from '@/utils/request'

export default {

    // 1. 生成订单
    createOrder(courseId) {
        return request({
            url: `/eduorder/order/createOrder/${courseId}`,
            method: 'post',
        })
    },
    // 2. 根据订单id查询订单信息
    getOrderInfo(orderId){
        return request({
            url: `/eduorder/order/getOrderInfo/${orderId}`,
            method: 'get',
        })
    },
    // 3. 生成二维码的方法
    createNative(orderNo){
        return request({
            url: `/eduorder/paylog/createNative/${orderNo}`,
            method: 'get',
        })
    },
    // 4. 查询订单状态方法
    queryPayStatus(orderNo){
        return request({
            url: `/eduorder/paylog/queryPayStatus/${orderNo}`,
            method: 'get',
        })
    },
}