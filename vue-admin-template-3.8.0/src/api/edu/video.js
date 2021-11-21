import request from '@/utils/request'

export default {
    // 1. 添加小节
    addVideo(video) {
        return request({
            url: `/eduservice/video/addVideo`,
            method: 'post',
            data: video
        })
    },
    // 2. 删除小节
    deleteVideo(id) {
        return request({
            url: `/eduservice/video/${id}`,
            method: 'delete',
        })
    },
    // 3. 修改小节
    updateVideo(video){
        return request({
            url: `/eduservice/video/updateVideo`,
            method: 'post',
            data: video
        })
    },
    // 4. 根据id查询小节
    getVideoById(id){
        return request({
            url: `/eduservice/video/getVideo/${id}`,
            method: 'get',
        })
    },
    // 5. 根据id删除视频
    deleteVideo(id){
        return request({
            //url: `/eduvod/video/removeVideo/${id}`,
            url: `/eduservice/video/${id}`,
            method: 'delete',
        })
    },
}