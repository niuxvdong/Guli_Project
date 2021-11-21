import request from '@/utils/request'

export default {
    // 1. 生成统计数据
    createStaData(day) {
        return request({
            url: `/staservice/sta/registerCount/${day}`,
            method: 'post',
        })
    },
    // 2. 获取到统计数据进行显示
    getDataSta(searchObj){
        return request({
            url: `/staservice/sta/showData/${searchObj.type}/${searchObj.begin}/${searchObj.end}`,
            method: 'get',
        })
    }
}