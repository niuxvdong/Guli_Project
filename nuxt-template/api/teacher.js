import request from '@/utils/request'

export default {

    // 1. 讲师分页
    getTeacherList(current, limit) {
        return request({
            url: `/eduservice/teacherFront/getTeacherFrontList/${current}/${limit}`,
            method: 'post',
        })
    },
    // 2. 讲师详情方法
    getTeacherInfo(id){
        return request({
            url: `/eduservice/teacherFront/getTeacherFrontInfo/${id}`,
            method: 'get',
        })
    }

}
