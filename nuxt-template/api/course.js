import request from '@/utils/request'

export default {

    // 1. 条件查询带分页 课程
    getCourseList(current, limit, searchObj) {
        return request({
            url: `/eduservice/courseFront/getFrontCourseList/${current}/${limit}`,
            method: 'post',
            data: searchObj
        })
    },
    // 2. 查询所有一级分类
    getAllSubject(){
        return request({
            url: `/eduservice/subject/getAllSubject`,
            method: 'get',
        })
    },
    // 3. 课程详情的方法
    getCourseInfo(courseId){
        return request({
            url: `/eduservice/courseFront/getFrontCourseInfo/${courseId}`,
            method: 'get',
        })
    }
}
