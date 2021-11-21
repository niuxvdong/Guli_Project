<template>
  <div class="app-container">
    <el-form label-width="120px">
      <el-form-item label="讲师名称">
        <el-input v-model="teacher.name" />
      </el-form-item>
      <el-form-item label="讲师排序">
        <el-input-number
          v-model="teacher.sort"
          controls-position="right"
          :min="0"
        />
      </el-form-item>

      <el-form-item label="讲师头衔">
        <el-select v-model="teacher.level" clearable placeholder="请选择">
          <!--
            数据类型一定要和取出的json中的一致，否则没法回填
            因此，这里value使用动态绑定的值，保证其数据类型是number
          -->
          <el-option :value="1" label="高级讲师" />
          <el-option :value="2" label="首席讲师" />
        </el-select>
      </el-form-item>

      <el-form-item label="讲师资历">
        <el-input v-model="teacher.career" />
      </el-form-item>
      <el-form-item label="讲师简介">
        <el-input v-model="teacher.intro" :rows="10" type="textarea" />
      </el-form-item>

      <!-- 讲师头像：TODO -->
      <el-form-item label="讲师头像">
        <!-- 头衔缩略图 -->
        <pan-thumb :image="teacher.avatar" />
        <!-- 文件上传按钮 -->
        <el-button
          type="primary"
          icon="el-icon-upload"
          @click="imagecropperShow = true"
          >更换头像
        </el-button>
        <!--
          v-show：是否显示上传组件
          :key：类似于id，如果一个页面多个图片上传控件，可以做区分
          :url：后台上传的url地址
          @close：关闭上传组件
          @crop-upload-success：上传成功后的回调 
        -->
        <image-cropper
          v-show="imagecropperShow"
          :width="300"
          :height="300"
          :key="imagecropperKey"
          :url="BASE_API + '/eduoss/fileoss'"
          field="file"
          @close="close"
          @crop-upload-success="cropSuccess"
        />
      </el-form-item>

      <el-form-item>
        <el-button
          :disabled="saveBtnDisabled"
          type="primary"
          @click="saveOrUpdate"
          >保存</el-button
        >
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import teacherAPI from "@/api/edu/teacher";

// 导入上传组件
import ImageCropper from '@/components/ImageCropper'
import PanThumb from '@/components/PanThumb'

export default {

  // 声明使用
  components: { ImageCropper, PanThumb },

  data() {
    return {
      teacher: {
        name: "",
        sort: 0,
        level: 1,
        career: "",
        intro: "",
        avatar: "",
      },
      imagecropperKey: 0, // 类似于id，如果一个页面多个图片上传控件，可以做区分
      imagecropperShow: false, // 上传弹框组件是否显示
      BASE_API: process.env.BASE_API, // 获取BASE_API
      saveBtnDisabled: false, // 保存按钮禁用
    };
  },
  created() {
    // 从讲师列表切换到save.vue组件 第一次进行初始化，后续同一个路由切换不会执行，通过watch监听来执行
    this.init();
  },
  watch: {
    // 监听路由：只有子路由相互切换回执行（即save update之间的切换）
    // 路由变化方式，路由发生变化就会执行（即使是路由到同一个vue页面）
    $route(to, from) {
      // console.log("watch $route");
      // 路由发生变化都去执行初始化方法
      this.init();
      // console.log("route");
    },
  },
  methods: {

    close(){
      // 关闭上传组件的回调方法
      this.imagecropperShow = false;
      // 关闭后将上传组件初始化一下
      this.imagecropperKey = this.imagecropperKey + 1;
    },
    cropSuccess(data){
      // 上传成功后的回调方法
      this.imagecropperShow = false;
      // data类似 response.data
      this.teacher.avatar = data.url;
      // 上传成功后将上传组件初始化一下
      this.imagecropperKey = this.imagecropperKey + 1;
    },

    init() {
      // 初始化方法

      // 根据路径参数是否有id值进行判断是添加还是修改 路由路径有id值 匹配这个路由 'edit/:id',
      if (this.$route.params && this.$route.params.id) {
        const id = this.$route.params.id;
        this.getTeacherInfo(id);
      } else {
        // 放到created中无效：因为添加和修改是二合一页面，无法在第二次进入created方法（多次跳转同一页面只执行一次created）
        // 没有id值则说明是添加，将表单数据清空
        this.teacher = {};
      }
    },
    saveOrUpdate() {
      // 保存修改二合一方法 根据teacher对象是否有id值判断
      if (this.teacher.id) {
        // 有 id 为 update
        this.updateTeacherInfo();
      } else {
        // 无 id 为 save
        this.saveTeacher();
      }
    },
    updateTeacherInfo() {
      // 修改讲师方法
      teacherAPI
        .updateTeacher(this.teacher)
        .then((response) => {
          // 提示信息 回到列表页面
          this.$message({
            type: "success",
            message: "修改成功!",
          });
          // 路由跳转 回到list.vue页面
          this.$router.push({ path: "/teacher/list" });
        })
        .catch((error) => {
          console.log(error);
        });
    },
    getTeacherInfo(id) {
      // 根据id查询讲师
      teacherAPI
        .getTeacherInfo(id)
        .then((response) => {
          this.teacher = response.data.teacher;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    saveTeacher() {
      // 添加保存
      teacherAPI
        .addTeacher(this.teacher)
        .then((response) => {
          // 提示信息 回到列表页面
          this.$message({
            type: "success",
            message: "添加成功!",
          });
          // 路由跳转 回到list.vue页面
          this.$router.push({ path: "/teacher/list" });
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};
</script>