<template>
  <div style="padding: 50px 140px 10px 40px">
    <el-tabs tab-position="left" @tab-click="clickTab" value="basic">
      <el-tab-pane label="用户管理" class="el-tab-pane-left"  name="basic">
        <basic :userId="userId" @getUserId="getUserIdToNew"></basic>
      </el-tab-pane>
      <el-tab-pane label="人事信息维护" class="el-tab-pane-left"  name="matter" :disabled="tabDisabled" >
        <matter :userId="userId" ref="matter"></matter>
      </el-tab-pane>
      <el-tab-pane label="合同情况" class="el-tab-pane-left" name="contract" :disabled="tabDisabled">
        <contract :userId="userId" ref="contract"></contract>
      </el-tab-pane>
      <el-tab-pane label="学历信息" class="el-tab-pane-left" name="education" :disabled="tabDisabled">
        <education :userId="userId" ref="education"></education>
      </el-tab-pane>
      <el-tab-pane label="家庭情况" class="el-tab-pane-left" name="family" :disabled="tabDisabled">
        <family :userId="userId" ref="family"></family>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
  // import Education from '@/views/admin/user/Education'
  // import Family from '@/views/admin/user/Family'
  // import Contract from '@/views/admin/user/Contract'
  import Basic from '@/views/admin/user/Basic'
  // import Matter from '@/views/admin/user/Matter'
  export default {
    components: {
      // Family,
      // Education,
      // Contract,
      Basic
      // Matter
    },
    created() {
      // 获取用户的id
      this.userId = this.$route.query.id;
      if (this.userId === '') {
        this.$message({
          message: '请先维护用户的基础信息！',
          type: 'success'
        });
      } else {
        this.tabDisabled = false;
      }
    },
    data() {
      return {
        userId: '',
        tabDisabled: true
      };
    },
    methods: {
      getUserIdToNew: function(data) {
        this.userId = data;
        this.tabDisabled = false;
      },
      clickTab(tab) {
        if (tab.name === 'basic') {
          console.log('basic')
        } /* else if (tab.name === 'matter') {
          this.$refs.matter.initCreate();
        } else if (tab.name === 'contract') {
          this.$refs.contract.contractInit();
        } else if (tab.name === 'education') {
          this.$refs.education.educationInit();
        } else if (tab.name === 'family') {
          this.$refs.family.familyInit();
        } */
      }
    }
  }
</script>
<style rel="stylesheet/scss" lang="scss" scoped>
  .el-tab-pane-left { margin-left: 10px; }
</style>
