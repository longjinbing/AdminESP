<template>
  <div class="app-container calendar-list-container">

    <!--查询条件-->
    <div class="filter-container">
      <el-input @keyup.enter.native="handleFilter" style="width: 200px;" class="filter-item" placeholder="姓名或账户"
                v-model="listQuery.name" :clearable="true"/>
      <el-button class="filter-item" type="primary" icon="search" @click="handleFilter" title="搜索">搜索</el-button>
      <el-button class="filter-item" type="primary" icon="plus" v-if="userManager_btn_add" title="新增"
                 style="margin-left:10px;" @click="handleCreate">新增</el-button>
    </div>

    <!--列表-->
    <el-table :key="tableKey" :data="list"  stripe v-loading.body="listLoading" fit highlight-current-row
              style="width: 100%;">
      <el-table-column align="center" label="序号" type="index" width="65">
      </el-table-column>
      <el-table-column align="center" label="姓名" sortable prop="name" style="width: 10%">
        <template scope="scope">
          <span>{{scope.row.name}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="账户" style="width: 10%">
        <template scope="scope">
          <span>{{scope.row.username}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="性别" style="width: 10%">
        <template scope="scope">
          <span v-for="(item, index ) in sexList" :key="index" v-if="item.id === scope.row.sex"> {{ item.name }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="生日" style="width: 10%" prop="birthday">
        <template scope="scope">
          <span>{{scope.row.birthday | formatDate('yyyy-MM-dd')}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="邮箱"  style="width: 20%">
        <template scope="scope">
          <span>{{scope.row.email}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="手机" style="width: 10%">
        <template scope="scope">
          <span>{{scope.row.mobilePhone}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="最近更新时间"  style="width: 10%">
        <template scope="scope">
          <span>{{scope.row.updateTime}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="最后更新人" style="width: 10%">
        <template scope="scope">
          <span>{{scope.row.updateName}}</span>
        </template>
      </el-table-column>
      <el-table-column fixed="right" align="center" label="操作" style="width: 10%">
        <template scope="scope">
          <el-button v-if="userManager_btn_edit" size="small" type="success"
                     @click="handleUpdate(scope.row)">编辑</el-button>
          <el-button v-if="userManager_btn_del" size="small" type="danger"
                     @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--分页-->
    <div v-show="!listLoading" class="pagination-container">
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                     :current-page.sync="listQuery.page"
                     :page-sizes="[10,20,30,50]" :page-size="listQuery.limit"
                     layout="total,sizes,prev,pager,next,jumper" :total="total">
      </el-pagination>
    </div>

    <!-- 组织机构管理选择树 -->
    <!--<el-dialog :visible.sync="dialogOrgTreeVisible" ref="diaog_tree" size="tiny">-->
    <!--<org-tree @org_tree="getTreeNodes" checkOrgId="" @org_tree_cancel="orgCancelTree"></org-tree>-->
    <!--</el-dialog>-->

  </div>
</template>

<script>
  import { mapGetters } from 'vuex';
  import { page, delObj} from 'api/admin/user/index';
  import OrgTree from '@/views/admin/org/checkOrg';
  import { getDictByCode } from 'api/admin/dict/index';

  export default {
    name: 'user',
    components: { OrgTree },
    data() {
      return {
        form: this.initObj(),
        birthdayOptions: {
          disabledDate(time) {
            return time.getTime() > Date.now() - 8.64e7;
          }
        },
        list: null,
        total: null,
        listLoading: true,
        dialogOrgTreeVisible: false,
        listQuery: {
          page: 1,
          limit: 20,
          name: undefined
        },
        sexOptions: ['男', '女'],
        dialogFormVisible: false,
        dialogStatus: '',
        userManager_btn_edit: false,
        userManager_btn_add: false,
        userManager_btn_del: false,
        textMap: {
          update: '更新',
          create: '创建'
        },
        tableKey: 0,
        treeNodes: '',
        sexList: []
      }
    },
    created() {
      this.getList();
      this.userManager_btn_edit = true;
      this.userManager_btn_add = true;
      this.userManager_btn_del = true;
    },
    computed: {
      ...mapGetters(['elements'])
    },
    methods: {
      initObj() {
        return {
          username: undefined,
          name: undefined,
          sex: '男',
          password: undefined,
          email: undefined,
          birthday: '',
          address: '',
          mobilePhone: undefined,
          description: undefined,
          corg: '',
          corgId: ''
        }
      },
      handleClose(done) {
        this.cancel('form');
        done();
      },
      getList() {
        this.listLoading = true;
        page(this.listQuery).then(response => {
          this.list = response.data.data.rows;
          this.total = response.data.data.total;
          this.listLoading = false;
        });
        getDictByCode('XB').then(data => { this.sexList = data.data; });
      },
      handleFilter() {
        this.getList();
      },
      handleSizeChange(val) {
        this.listQuery.limit = val;
        this.getList();
      },
      handleCurrentChange(val) {
        this.listQuery.page = val;
        this.getList();
      },
      handleCreate() {
        this.$router.push({
          path: '/admin/uLayout',
          query: { id: '' }
        })
      },
      handleUpdate(row) {
        this.$router.push({
          path: '/admin/uLayout',
          query: { id: row.id }
        })
      },
      handleDelete(row) {
        this.$confirm('是否刪除该记录？', '记录', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          delObj(row.id).then(() => {
            this.$notify({
              title: '成功',
              message: '删除成功',
              type: 'success',
              duration: 2000
            });
            const index = this.list.indexOf(row);
            this.list.splice(index, 1);
          })
        })
      }
    }
  }
</script>
<style rel="stylesheet/scss" lang="scss" scoped>
  .input-selects-width { width: 340px; }
</style>
