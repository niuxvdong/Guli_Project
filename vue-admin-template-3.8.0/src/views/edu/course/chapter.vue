<template>
  <div class="app-container">
    <h2 style="text-align: center">发布新课程</h2>
    <el-steps
      :active="2"
      process-status="wait"
      align-center
      style="marginbottom: 40px"
    >
      <el-step title="填写课程基本信息" />
      <el-step title="创建课程大纲" />
      <el-step title="提交审核" />
    </el-steps>

    <el-button type="text" @click="openChapterDialog()">添加章节</el-button>

    <!-- 添加和修改章节表单 -->
    <el-dialog :visible.sync="dialogChapterFormVisible" title="添加章节">
      <el-form :model="chapter" label-width="120px">
        <el-form-item label="章节标题">
          <el-input v-model="chapter.title" />
        </el-form-item>
        <el-form-item label="章节排序">
          <el-input-number
            v-model="chapter.sort"
            :min="0"
            controlsposition="right"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogChapterFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveOrUpdate">确 定</el-button>
      </div>
    </el-dialog>

    <!-- 添加和修改课时表单弹框 -->
    <!-- 添加和修改课时表单 -->
    <el-dialog :visible.sync="dialogVideoFormVisible" title="添加课时">
      <el-form :model="video" label-width="120px">
        <el-form-item label="课时标题">
          <el-input v-model="video.title" />
        </el-form-item>
        <el-form-item label="课时排序">
          <el-input-number
            v-model="video.sort"
            :min="0"
            controls-position="right"
          />
        </el-form-item>
        <el-form-item label="是否免费">
          <el-radio-group v-model="video.free">
            <el-radio :label="0">免费</el-radio>
            <el-radio :label="1">默认</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="上传视频">
          <el-upload
            :on-success="handleVodUploadSuccess"
            :on-remove="handleVodRemove"
            :before-remove="beforeVodRemove"
            :on-exceed="handleUploadExceed"
            :file-list="fileList"
            :action="BASE_API + '/eduvod/video/uploadVideo'"
            :limit="1"
            class="upload-demo"
          >
            <el-button size="small" type="primary">上传视频</el-button>
            <el-tooltip placement="right-end">
              <div slot="content">
                最大支持1G，<br />
                支持3GP、ASF、AVI、DAT、DV、FLV、F4V、<br />
                GIF、M2T、M4V、MJ2、MJPEG、MKV、MOV、MP4、<br />
                MPE、MPG、MPEG、MTS、OGG、QT、RM、RMVB、<br />
                SWF、TS、VOB、WMV、WEBM 等视频格式上传
              </div>
              <i class="el-icon-question" />
            </el-tooltip>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVideoFormVisible = false">取 消</el-button>
        <el-button
          :disabled="saveVideoBtnDisabled"
          type="primary"
          @click="saveOrUpdateVideo"
          >确 定</el-button
        >
      </div>
    </el-dialog>

    <!-- 章节弹框 -->
    <ul class="chanpterList">
      <li v-for="chapter in chapterVideoList" :key="chapter.id">
        <p>
          {{ chapter.title }}

          <span class="acts">
            <el-button style="" type="text" @click="openVideo(chapter.id)"
              >添加小节</el-button
            >
            <el-button style="" type="text" @click="openEditChatper(chapter.id)"
              >编辑</el-button
            >
            <el-button type="text" @click="removeChapter(chapter.id)"
              >删除</el-button
            >
          </span>
        </p>

        <!-- 视频 -->
        <ul class="chanpterList videoList">
          <li v-for="video in chapter.children" :key="video.id">
            <p>
              {{ video.title }}

              <span class="acts">
                <el-button style="" type="text" @click="openEditVideo(video.id)"
                  >编辑</el-button
                >
                <el-button type="text" @click="removeVideo(video.id)"
                  >删除</el-button
                >
              </span>
            </p>
          </li>
        </ul>
      </li>
    </ul>
    <div>
      <el-button @click="previous">上一步</el-button>
      <el-button :disabled="saveBtnDisabled" type="primary" @click="next"
        >下一步</el-button
      >
    </div>
  </div>
</template>

<script>

import chapter from "@/api/edu/chapter";
import video from "@/api/edu/video";

export default {
  data() {
    return {
      saveBtnDisabled: false, // 保存按钮是否禁用
      courseId: "",
      chapterVideoList: [],
      dialogChapterFormVisible: false, // 章节弹框
      dialogVideoFormVisible: false, // 小节弹框
      chapter: {
        // 封装章节属性
        title: "",
        sort: 0,
      },
      video: {
        // 封装小节属性
        title: "",
        sort: 0,
        free: 0,
        videoSourceId: "",
        videoOriginalName: ''
      },
      fileList: [], //上传文件列表
      BASE_API: process.env.BASE_API, // 接口API地址
    };
  },
  created() {
    console.log("chapter created");
    // 获取路由的id值
    if (this.$route.params && this.$route.params.id) {
      this.courseId = this.$route.params.id;
      this.getChapterVideo();
    }
  },
  methods: {
    // =============================视频上传==================================
    // 点击视频的x触发事件
    beforeVodRemove(file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`);
    },
    // 弹框点击确认调用
    handleVodRemove(file, fileList) {
      console.log(file);
      video.deleteVideo(this.video.videoSourceId).then((response) => {
        this.$message({
          type: "success",
          message: "删除视频成功！",
        });
        // 清空上传的文件列表，由于只限制了一个视频，可以直接清空
        this.fileList = [];
        // 删除视频后还需要清空video的视频id和名称
        this.video.videoSourceId = '';
        this.video.videoOriginalName = '';
      });
    },
    //成功回调 参数：file表示上传的文件
    handleVodUploadSuccess(response, file, fileList) {
      console.log(response);
      // 上传成功后设置视频id和名称
      this.video.videoSourceId = response.data.videoId;
      this.video.videoOriginalName = file.name;
    },
    //视图上传多于一个视频
    handleUploadExceed(files, fileList) {
      this.$message.warning("想要重新上传视频，请先删除已上传的视频");
    },

    // ==============================小节操作=========================================
    //  修改小节按钮
    openEditVideo(id) {
      // 回显信息
      video.getVideoById(id).then((response) => {
        this.video = response.data.video;
        this.fileList = [{'name': this.video.videoOriginalName}]
      });
      this.dialogVideoFormVisible = true;
    },
    // 修改小节
    updateVideo() {
      // 修改完毕保存
      video.updateVideo(this.video).then((response) => {
        // 关闭弹框
        this.dialogVideoFormVisible = false;
        this.$message({
          type: "success",
          message: "修改小节成功!",
        });
        this.video = {};
        // 刷新页面
        this.getChapterVideo();
      });
    },
    // 添加小节
    openVideo(chapterId) {
      this.video = {};
      this.fileList = [];
      // 设置章节id
      this.video.chapterId = chapterId;
      // 设置课程id
      this.video.courseId = this.courseId;

      this.dialogVideoFormVisible = true;
    },
    saveOrUpdateVideo() {
      // 根据有无videoId判断是添加还是修改小节
      if (this.video.id) {
        this.updateVideo();
      } else {
        this.addVideo();
      }
    },
    addVideo() {
      video.addVideo(this.video).then((response) => {
        // 关闭弹框
        this.dialogVideoFormVisible = false;
        // 提示信息
        this.$message({
          type: "success",
          message: "添加小节成功!",
        });
        // 清空表单数据
        this.video = {};
        // 刷新页面
        this.getChapterVideo();
      });
    },
    // 删除小节
    removeVideo(id) {
      console.log(id);
      this.$confirm("此操作将删除小节, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        //点击确定，执行then方法
        //调用删除的方法
        video.deleteVideo(id).then((response) => {
          //删除成功
          //提示信息
          this.$message({
            type: "success",
            message: "删除小节成功!",
          });
          //刷新页面
          this.getChapterVideo();
        });
      });
    },
    // ==============================章节操作=========================================
    // 删除章节
    removeChapter(chapterId) {
      // 确认弹框
      this.$confirm("此操作将永久删除该章节记录, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          // 点击确认执行
          chapter.deleteChapter(chapterId).then((response) => {
            // 删除成功 提示删除成功
            this.$message({
              type: "success",
              message: "删除成功!",
            });
            // 刷新页面
            this.getChapterVideo();
          });
        })
        .catch(() => {
          // 点击取消执行
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
    // 点击修改按钮弹框和回显
    openEditChatper(chapterId) {
      chapter.getChapter(chapterId).then((response) => {
        this.chapter = response.data.chapter;
      });
      // 获取到信息回显弹框
      this.dialogChapterFormVisible = true;
    },
    // 修改章节
    updateChapter() {
      chapter.updateChapter(this.chapter).then((respnse) => {
        // 关闭弹框
        this.dialogChapterFormVisible = false;
        // 关闭弹框后将表单数据清空
        this.chapter.title = "";
        this.chapter.sort = 0;
        // 提示信息
        this.$message({
          type: "success",
          message: "修改章节成功!",
        });
        // 刷新页面
        this.getChapterVideo();
      });
    },
    // 添加章节
    addChapter() {
      // 设置章节表需要的课程id
      this.chapter.courseId = this.courseId;
      chapter.addChapter(this.chapter).then((respnse) => {
        // 关闭弹框
        this.dialogChapterFormVisible = false;
        // 关闭弹框后将表单数据清空
        this.chapter.title = "";
        this.chapter.sort = 0;
        // 提示信息
        this.$message({
          type: "success",
          message: "添加章节成功!",
        });
        // 刷新页面
        this.getChapterVideo();
      });
    },
    // 保存修改二合一
    saveOrUpdate() {
      // 通过 章节id 标志位 判断是修改还是添加
      if (this.chapter.id != "") {
        this.updateChapter();
      } else {
        this.addChapter();
      }
    },
    // 章节弹框方法
    openChapterDialog() {
      this.chapter.title = "";
      this.chapter.sort = 0;
      // 添加章节点击时清空chapter的id
      this.chapter.id = "";
      this.dialogChapterFormVisible = true;
    },
    // 根据课程id查询章节和小结列表
    getChapterVideo() {
      chapter.getAllChapterVideo(this.courseId).then((response) => {
        this.chapterVideoList = response.data.allCharapterVideo;
      });
    },
    previous() {
      console.log("previous");
      this.$router.push({ path: "/course/info/" + this.courseId });
    },
    next() {
      console.log("next");
      this.$router.push({ path: "/course/publish/" + this.courseId });
    },
  },
};
</script>

<style scoped>
.chanpterList {
  position: relative;
  list-style: none;
  margin: 0;
  padding: 0;
}
.chanpterList li {
  position: relative;
}
.chanpterList p {
  float: left;
  font-size: 20px;
  margin: 10px 0;
  padding: 10px;
  height: 70px;
  line-height: 50px;
  width: 100%;
  border: 1px solid #ddd;
}
.chanpterList .acts {
  float: right;
  font-size: 14px;
}

.videoList {
  padding-left: 50px;
}
.videoList p {
  float: left;
  font-size: 14px;
  margin: 10px 0;
  padding: 10px;
  height: 50px;
  line-height: 30px;
  width: 100%;
  border: 1px dotted #ddd;
}
</style>