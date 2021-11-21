<template>
  <div class="app-container">
    讲师列表

    <!--
      查询表单
      inline：单行显示
    -->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="teacherQuery.name" placeholder="讲师名" />
      </el-form-item>

      <el-form-item>
        <el-select
          v-model="teacherQuery.level"
          clearable
          placeholder="讲师头衔"
        >
          <el-option :value="1" label="高级讲师" />
          <el-option :value="2" label="首席讲师" />
        </el-select>
      </el-form-item>

      <el-form-item label="添加时间">
        <!-- el-date-picker el-ui的日期组件 -->
        <el-date-picker
          v-model="teacherQuery.begin"
          type="datetime"
          placeholder="选择开始时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>

      <el-form-item>
        <el-date-picker
          v-model="teacherQuery.end"
          type="datetime"
          placeholder="选择截止时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>

      <!-- 点击查询调用getList事件 -->
      <el-button type="primary" icon="el-icon-search" @click="getList()"
        >查 询</el-button
      >
      <el-button type="default" @click="resetData()">清空</el-button>
    </el-form>

    <!-- 表格 :data表示单向绑定v-bind简写 遍历list el-ul已经做到了自动遍历 -->
    <el-table :data="list" border fit highlight-current-row>
      <!-- 表头项 -->
      <el-table-column label="序号" width="70" align="center">
        <!-- slop-scope 表示表格域 -->
        <template slot-scope="scope">
          {{ (current - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>

      <el-table-column prop="name" label="名称" width="80" />
      <el-table-column label="头衔" width="80">
        <template slot-scope="scope">
          {{ scope.row.level === 1 ? "高级讲师" : "首席讲师" }}
        </template>
      </el-table-column>

      <el-table-column prop="intro" label="资历" />
      <el-table-column prop="gmtCreate" label="添加时间" width="160" />
      <el-table-column prop="sort" label="排序" width="60" />
      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <!-- 修改按钮 -->
          <router-link :to="'/teacher/edit/' + scope.row.id">
            <el-button type="primary" size="mini" icon="el-icon-edit">修改</el-button>
          </router-link>
          <!-- 删除按钮 scope.row.id 拿到该行数据对应的分布式id -->
          <el-button type="danger" size="mini" icon="el-icon-delete" 
            @click="removeDataById(scope.row.id)" >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <!-- 
      @xxx v-on简写 
      getList 写入方法名称，不需要写参数，el-ul 自动传入当前的页数
    -->
    <el-pagination
      :current-page="current"
      :page-size="limit"
      :total="total"
      style="padding: 30px 0; text-align: center"
      layout="total, prev, pager, next, jumper"
      @current-change="getList"
    />
  </div>
</template>

<script>
// 1. 引入teacher.js
import teacher from "@/api/edu/teacher";

export default {
  data() {
    // 数据定义
    return {
      list: null, // 查询返回的集合
      current: 1, // 当前页
      limit: 10, // 每页记录数
      total: 0, // 总记录数
      teacherQuery: {}, // 条件对象 v-mode 会自动添加属性，无需自己写
    };
  },
  created() {
    // 方法的调用
    this.getList();
  },
  methods: {
    // 方法定义，调用teacher.js中的方法
    // 分页数据处理，el-ui 自动传入请求的是第几页
    getList(current = 1) {
      this.current = current;

      // 通过传入的第几页调用teacher.js中的方法进行分页
      teacher
        .getTeacherListPage(this.current, this.limit, this.teacherQuery)
        .then((response) => {
          // 请求成功
          // response: 接口返回的数据
          // console.log(response);
          if(response.data.rows == null){
            this.getList(this.current - 1);
          }
          this.list = response.data.rows;
          this.total = response.data.total;
        })
        .catch((error) => {
          // 请求失败
          // error: 发生的异常
          console.log(error);
        });
    },
    resetData() {
      // 清空条件输入框数据并查询所有数据回显
      // 双向绑定，将对象清空即可
      this.teacherQuery = {};
      this.getList();
    },
    removeDataById(id) {
      // 删除讲师方法
      this.$confirm('此操作将永久删除讲师记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => { // 点击确认执行

        // 调用删除方法
        teacher.deleteTeacherById(id)
          .then(response => {
            // 删除成功 提示删除成功 
            this.$message({
              type: 'success',
              message: '删除成功!'
            });
            // 重新回到该页面
            this.getList(this.current);
          })
          .catch(error => {
            console.log(error);
          });
        
      }).catch(() => { // 点击取消执行
        this.$message({
          type: 'info',
          message: '已取消删除'
        });          
      });  
    },
  },
};
</script>