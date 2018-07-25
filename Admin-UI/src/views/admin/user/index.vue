<template>
  <div class="app-container">

    <div class="filter-container">
      <el-input placeholder="姓名/拼音/缩写/用户名" style="width: 200px;"/>

      <el-button type="primary" icon="el-icon-search" :title="labels.search">{{labels.search}}</el-button>
      <el-button type="primary" icon="el-icon-plus" :title="labels.add" @click="dialogVisible = true">{{labels.add}}</el-button>
    </div>

    <el-table :data="list" :key="tableKey" border stripe fit highlight-current-row v-loading.body="listLoading"
              max-height="650">
      <el-table-column align="center" type="index" label="序号" fixed="left" width="65"/>
      <el-table-column align="center" prop="name" label="姓名"/>
      <el-table-column align="center" prop="username" label="账号"/>
      <el-table-column align="center" prop="email" label="邮箱"/>
      <el-table-column align="center" prop="mobile" label="手机"/>
      <el-table-column align="center" label="状态" width="80">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.enabled"
            :active-value="elSwitch.enable"
            :inactive-value="elSwitch.disable"
            active-color="#13ce66"
            inactive-color="#ff4949"
            @change="switchState($event, scope.row.id)"/>
        </template>
      </el-table-column>
      <el-table-column align="center" fixed="right" label="操作" width="120">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" icon="el-icon-edit" circle @click="handleEdit(scope.row)"/>
          <el-button type="danger" size="mini" icon="el-icon-delete" circle @click="handleDelete(scope.row)"/>
        </template>
      </el-table-column>
    </el-table>

    <div class="pagination-container">
      <el-pagination
        background
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page.sync="listQuery.page"
        :page-sizes="[10, 20, 30, 50]"
        :page-size="listQuery.limit"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
      </el-pagination>
    </div>

    <el-dialog title="人员信息" :visible.sync="dialogVisible" width="23%" @close="closeDialog">
      <el-form label-width="80px" :model="form" ref="form" :rules="rules">
        <el-form-item label="姓名" prop="name">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="账号" prop="username">
          <el-input v-model="form.username"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email"></el-input>
        </el-form-item>
        <el-form-item label="手机" prop="mobile">
          <el-input v-model="form.mobile"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveUser">保 存</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script>
  import { page, addObj, putObj, delObj } from 'api/admin/user/index';

  export default {
    data() {
      return {
        labels: { search: '搜索', add: '新增' },
        list: [],
        total: 0,
        tableKey: 0,
        listLoading: true,
        listQuery: {
          page: 1,
          limit: 20,
          name: undefined
        },
        dialogVisible: false,
        form: { id: null, name: null, username: null, email: null, mobile: null },
        rules: {
          name: [
            { required: true, message: '请输入姓名', trigger: 'blur' }
          ],
          username: [
            { required: true, message: '请输入账号', trigger: 'blur' }
          ],
          email: [
            { required: true, message: '请输入邮箱', trigger: 'blur' }
          ],
          mobile: [
            { required: true, message: '请输入手机号', trigger: 'blur' }
          ]
        },
        elSwitch: { enable: 1, disable: 0 }
      };
    },
    created() {
      this.getList();
    },
    methods: {
      getList() {
        this.listLoading = true;
        page(this.listQuery).then(res => {
          this.list = res.data.data.rows;
          this.total = res.data.data.total;
          this.listLoading = false;
        });
      },
      handleSizeChange(val) {
        this.listQuery.limit = val;
        this.getList();
      },
      handleCurrentChange(val) {
        this.listQuery.page = val;
        this.getList();
      },
      handleEdit(row) {
        const { id, name, username, email, mobile } = row;
        this.form = { id, name, username, email, mobile };
        this.dialogVisible = true;
      },
      handleDelete(row) {
        this.$confirm('此操作将彻底删除用户信息, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          delObj(row.id).then(() => {
            this.getList();
            this.$message({ type: 'success', message: '删除成功!' });
          });
        }).catch(() => {});
      },
      saveUser() {
        this.$refs['form'].validate(valid => {
          if (valid) {
            (this.form.id ? putObj : addObj)(this.form).then(() => {
              this.$message({ type: 'success', message: '保存成功!' });
              this.dialogVisible = false;
              this.getList();
            });
          }
        });
      },
      closeDialog() {
        this.form = { id: null, name: null, username: null, email: null, mobile: null };
        this.$refs.form.clearValidate();
      },
      switchState(val, id) {
        console.info(val)
        console.info(id)
        putObj({ id, enabled: val }).then();
      }
    }
  }
</script>
