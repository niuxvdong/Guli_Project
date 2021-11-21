import request from '@/utils/request'

export default {
    // 1. 添加课程信息
    addCourseInfo(courseInfo) {
        return request({
            url: `/eduservice/course/addCourseInfo`,
            method: 'post',
            data: courseInfo
        })
    },
    // 2. 查询所有讲师
    getListTeacher() {
        return request({
            url: `/eduservice/teacher/findAll`,
            method: 'get'
        })
    },
    // 3. 根据课程id查询课程基本信息
    getCourseInfoById(courseId) {
        return request({
            url: `/eduservice/course/getCourseInfo/${courseId}`,
            method: 'get'
        })
    },
    // 4. 修改课程信息
    updateCourseInfo(course) {
        return request({
            url: `/eduservice/course/updateCourseInfo`,
            method: 'post',
            data: course
        })
    },
    // 5. 根据id值查询最终发布课程的所有信息
    getPublishCourseInfo(id) {
        return request({
            url: `/eduservice/course/getPublishCourseInfo/${id}`,
            method: 'get',
        })
    },
    // 6. 课程最终发布 状态修改
    publishCourse(id) {
        return request({
            url: `/eduservice/course/publishCourse/${id}`,
            method: 'post',
        })
    },
    // 7. 课程列表 分页
    getCourseListPage(current, limit, courseQuery){
        return request({
            url: `/eduservice/course/pageCourseCondition/${current}/${limit}`,
            method: 'post',
            data: courseQuery
        })
    },
    // 8. 删除课程
    deleteCourseById(id){
        return request({
            url: `/eduservice/course/${id}`,
            method: 'delete',
        })
    } 
}