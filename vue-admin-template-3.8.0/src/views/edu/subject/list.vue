<template>
  <div class="app-container">
    <el-input
      v-model="filterText"
      placeholder="Filter keyword"
      style="margin-bottom: 30px"
    />

    <el-tree
      ref="tree2"
      :data="data2"
      :props="defaultProps"
      :filter-node-method="filterNode"
      class="filter-tree"
      default-expand-all
    />
  </div>
</template>

<script>
import subject from "@/api/edu/subject";

export default {
  data() {
    return {
      filterText: "",
      data2: [], // 返回所有分类
      defaultProps: {
        children: "children",
        label: "title",
      },
    };
  },
  created(){
	  // 页面加载就进行调用
	  this.getAllSubjectList();
  },
  watch: {
    filterText(val) {
      this.$refs.tree2.filter(val);
    },
  },

  methods: {
    // 获取所有课程分类信息
    getAllSubjectList() {
      subject.getSubjectList()
        .then((response) => {
          this.data2 = response.data.list;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    filterNode(value, data) {
	  // 简单的检索方法
      if (!value) return true;
      return data.title.toLowerCase().indexOf(value.toLowerCase()) !== -1;
    },
  },
};
</script> 

