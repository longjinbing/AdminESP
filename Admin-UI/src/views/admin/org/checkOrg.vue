<template>
  <div id="org" class="app-container calendar-list-container">
    <el-input placeholder="输入关键字进行过滤" v-model="filterText" :clearable="true"/>

    <el-tree class="filter-tree on-border" node-key="id"
             :data="checkOrgData"
             :props="defaultProps"
             :show-checkbox="true"
             :default-expand-all="true"
             :filter-node-method="filterNode"
             ref="checkOrgTree"
             @node-click="clickTree"
             @node-expand="expandTree"/>

    <div slot="footer" class="dialog-footer">
      <el-button @click="cancel()">取 消</el-button>
      <el-button type="primary" @click="checkOrgNodes()" >确 定</el-button>
    </div>
  </div>
</template>
<script>
  import { orgTree } from 'api/admin/org/index';

  export default {
    data() {
      return {
        filterText: '',
        state: 'see',
        checkOrgData: [],
        defaultProps: {
          children: 'children',
          label: 'label'
        },
        treeForm: {
          parentid: 0
        }
      };
    },
    props: ['checkOrgId'],
    watch: {
      filterText(val) {
        this.$refs.checkOrgTree.filter(val);
      }
    },
    created() {
      // 初始化左侧树
      this.getOrgTree();
    },
    methods: {
      getOrgTree() {
        orgTree(this.treeForm).then(response => {
          this.checkOrgData = response.data;
          if (this.checkOrgId !== null && this.checkOrgId !== undefined) {
            this.$refs.checkOrgTree.setCheckedKeys(this.checkOrgId.split(','));
          }
        })
      },
      filterNode(value, data) {
        if (!value) return true;
        return data.label.indexOf(value) !== -1 || data.spell.indexOf(value) !== -1 || data.initials.indexOf(value) !== -1;
      },
      clickTree(data) {
        console.log(data)
        // this.dataTOForm(data)
        // this.getObj(data.id)
      },
      expandTree(data) {
        console.log(data)
      },
      checkOrgNodes() {
        this.$emit('org_tree', this.$refs.checkOrgTree.getCheckedNodes());
      },
      cancel() {
        this.$emit('org_tree_cancel', 'cancel');
      }
    }
  }
</script>
<style rel="stylesheet/scss" lang="scss" scoped>
  .right-layout-from  {margin-left: 20px; }
  .top-button { margin-bottom: 20px; }
  .tree-right-from {
    width: 70%;
    margin: 3%;
  }
  .grid-content {
    border-radius: 4px;
    min-height: 36px;
  }
  .on-border { border: none }
</style>
