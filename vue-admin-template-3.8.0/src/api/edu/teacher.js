import request from '@/utils/request'

export default {
    // 1. 讲师列表 分页 /pageTeacherCondition/{current}/{limit}
    getTeacherListPage(current, limit, teacherQuery) {
        return request({
            //url: '/eduservice/teacher/pageTeacherCondition/'+current+'/'+limit,
            url: `/eduservice/teacher/pageTeacherCondition/${current}/${limit}`,
            method: 'post',
            // params
            // 表示将对象转换成json传递到服务器
            data: teacherQuery
        })
    },
    // 2. 删除讲师id
    deleteTeacherById(id) {
        return request({
            url: `/eduservice/teacher/${id}`,
            method: 'delete'
        })
    },
    // 3. 添加讲师
    addTeacher(teacher) {
        return request({
            url: `/eduservice/teacher/addTeacher`,
            method: 'post',
            data: teacher
        })
    },
    // 4. 根据id查讲师
    getTeacherInfo(id) {
        return request({
            url: `/eduservice/teacher/getTeacher/${id}`,
            method: 'get'
        })
    },
    // 5. 修改讲师
    updateTeacher(teacher) {
        return request({
            url: `/eduservice/teacher/updateTeacher`,
            method: 'post',
            data: teacher
        })
    }
}