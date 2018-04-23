<template>
  <el-card class="box-card" >
    <el-form :model="form" :inline="true" :rules="rules" ref="form" label-width="95px">
      <el-row>
        <el-col :span="8">
          <el-form-item label="姓名" prop="name">
            <el-input v-if="formStatus == 'see'" :disabled="true" v-model="form.name" placeholder="请输入姓名" class="input-selects-width-basic"></el-input>
            <el-input v-else v-model="form.name" placeholder="请输入姓名" class="input-selects-width-basic"></el-input>
          </el-form-item>
          <el-form-item  label="政治面貌"prop="political">
            <el-select  v-if="formStatus == 'see'" :disabled="true"  class="filter-item input-selects-width-basic" v-model="form.political"  placeholder="请选择">
              <el-option v-for="(item,index) in politicalList" :key="index" :label="item.name" :value="item.id"></el-option>
            </el-select>
            <el-select  v-else class="filter-item input-selects-width-basic" v-model="form.political"  placeholder="请选择">
              <el-option v-for="(item,index) in politicalList" :key="index" :label="item.name" :value="item.id"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item  label="性别"  prop="sex">
            <el-select v-if="formStatus == 'see'" :disabled="true"  class="filter-item input-selects-width-basic" v-model="form.sex"  placeholder="请选择">
              <el-option v-for="(item,index) in sexList" :key="index" :label="item.name" :value="item.id"></el-option>
            </el-select>
            <el-select v-else class="filter-item input-selects-width-basic" v-model="form.sex"  placeholder="请选择">
              <el-option v-for="(item,index) in sexList" :key="index" :label="item.name" :value="item.id"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="民族" prop="nation">
            <el-input v-if="formStatus == 'see'" :disabled="true"  v-model="form.nation" class="input-selects-width-basic" placeholder="请输入民族" ></el-input>
            <el-input v-else  v-model="form.nation" class="input-selects-width-basic" placeholder="请输入民族" ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="身份证号码"  prop="card">
            <el-input v-if="formStatus == 'see'" :disabled="true" v-model="form.card" class="input-selects-width-basic" placeholder="请输入身份证号码" ></el-input>
            <el-input v-else v-model="form.card" @blur="blurCard" class="input-selects-width-basic" placeholder="请输入身份证号码" ></el-input>
          </el-form-item>
          <el-form-item  label="出生年月" prop="birthday">
            <el-date-picker
              :disabled="true"
              v-model="form.birthday"
              type="date"
              style="width: 330px"
              format="yyyy-MM-dd"
              value-format="yyyy-MM-dd HH:mm:ss"
              placeholder="选择日期">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="邮箱" prop="email">
            <el-input v-if="formStatus == 'see'" :disabled="true" v-model="form.email" placeholder="请输入邮箱" class="input-selects-width-basic"></el-input>
            <el-input v-else v-model="form.email" placeholder="请输入邮箱" @blur="blurEmail" class="input-selects-width-basic"></el-input>
          </el-form-item>
          <el-form-item label="登录名" prop="username">
            <el-input  :disabled="true" v-model="form.username" placeholder="请输入登录名" class="input-selects-width-basic"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="照片" class="uploadImageSrc">
            <div class="avatarImg" @click="imageClick">
              <img  v-if="form.imageSrc" :src="form.imageSrc" class="formImage">
              <i  v-else class="el-icon-plus avatar-uploader-icon"></i>
            </div>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item label="婚姻状况" prop="marriage">
            <el-select v-if="formStatus == 'see'" :disabled="true" class="filter-item input-selects-width-basic" v-model="form.marriage"  placeholder="请选择">
              <el-option v-for="(item,index) in marriageList" :key="index" :label="item.name" :value="item.id"></el-option>
            </el-select>
            <el-select v-else class="filter-item input-selects-width-basic" v-model="form.marriage"  placeholder="请选择">
              <el-option v-for="(item,index) in marriageList" :key="index" :label="item.name" :value="item.id"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="工作证号" prop="workCard">
            <el-input v-if="formStatus == 'see'" :disabled="true" v-model="form.workCard" class="input-selects-width-basic" placeholder="请输入工作证号" ></el-input>
            <el-input v-else v-model="form.workCard" class="input-selects-width-basic" placeholder="请输入工作证号" ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="车牌号码" prop="platesNo">
            <el-input v-if="formStatus == 'see'" :disabled="true" v-model="form.platesNo" placeholder="请输入车牌号码" class="input-selects-width-basic"></el-input>
            <el-input v-else v-model="form.platesNo" placeholder="请输入车牌号码" class="input-selects-width-basic"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="8">
          <el-form-item label="手机号码" prop="mobilePhone">
            <el-input v-if="formStatus == 'see'" :disabled="true" v-model="form.mobilePhone" placeholder="请输入手机号码" class="input-selects-width-basic"></el-input>
            <el-input v-else v-model="form.mobilePhone" placeholder="请输入手机号码" class="input-selects-width-basic"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="住宅电话" prop="homePhone">
            <el-input v-if="formStatus == 'see'" :disabled="true" v-model="form.homePhone" placeholder="请输入住宅电话" class="input-selects-width-basic"></el-input>
            <el-input v-else v-model="form.homePhone" placeholder="请输入住宅电话" class="input-selects-width-basic"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="家庭地址" prop="homeAddress">
            <el-input v-if="formStatus == 'see'" :disabled="true" v-model="form.homeAddress" placeholder="请输入家庭地址" class="input-selects-width-basic"></el-input>
            <el-input v-else v-model="form.homeAddress" placeholder="请输入家庭地址" class="input-selects-width-basic"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item label="户口所在地" prop="domicilePlace">
            <el-input v-if="formStatus == 'see'" :disabled="true" v-model="form.domicilePlace" placeholder="请输入户口所在地" class="input-selects-width-basic"></el-input>
            <el-input v-else v-model="form.domicilePlace" placeholder="请输入户口所在地" class="input-selects-width-basic"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="户口类型" prop="accType">
            <el-select v-if="formStatus == 'see'" :disabled="true" class="filter-item input-selects-width-basic" v-model="form.accType"  placeholder="请选择">
              <el-option v-for="(item,index) in accTypeList" :key="index" :label="item.name" :value="item.id"></el-option>
            </el-select>
            <el-select v-else class="filter-item input-selects-width-basic" v-model="form.accType"  placeholder="请选择">
              <el-option v-for="(item,index) in accTypeList" :key="index" :label="item.name" :value="item.id"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="籍贯" prop="nativePlace">
            <el-input v-if="formStatus == 'see'" :disabled="true" v-model="form.nativePlace" placeholder="请输入籍贯" class="input-selects-width-basic"></el-input>
            <el-input v-else v-model="form.nativePlace" placeholder="请输入籍贯" class="input-selects-width-basic"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item label="所级单位" prop="deptName">
            <el-input  v-if="formStatus == 'see'" v-model="form.deptName" :disabled="true" placeholder="请选择所级单位" class="input-selects-width-basic"></el-input>
            <el-row v-else>
              <el-col :span="20">
                <el-input v-model="form.deptName" :disabled="true" placeholder="请选择所级单位" class="input-org"></el-input>
              </el-col>
              <el-col :span="4">
                <el-button type="primary" @click="checkOrg(form.deptId,'deptId')">选择</el-button>
              </el-col>
            </el-row>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="所在科室" prop="officeName">
            <el-input  v-if="formStatus == 'see'" v-model="form.officeName" :disabled="true" placeholder="请选择所级单位" class="input-selects-width-basic"></el-input>
            <el-row v-else>
              <el-col :span="20">
                <el-input v-model="form.officeName" :disabled="true" placeholder="请选择所级单位" class="input-org"></el-input>
              </el-col>
              <el-col :span="4">
                <el-button type="primary" @click="checkOrg(form.officeId,'officeId')">选择</el-button>
              </el-col>
            </el-row>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="院级别单位" prop="orgName">
            <el-input v-if="formStatus == 'see'" v-model="form.orgName" :disabled="true" placeholder="请选择所级单位" class="input-selects-width-basic"></el-input>
            <el-row v-else>
              <el-col :span="20">
                <el-input v-model="form.orgName" :disabled="true" placeholder="请选择所级单位" class="input-org"></el-input>
              </el-col>
              <el-col :span="4">
                <el-button type="primary" @click="checkOrg(form.orgId,'orgId')">选择</el-button>
              </el-col>
            </el-row>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item label="出生地" prop="birthPlace">
            <el-input v-if="formStatus == 'see'" :disabled="true" v-model="form.birthPlace" placeholder="请输入出生地" class="input-selects-width-basic"></el-input>
            <el-input v-else v-model="form.birthPlace" placeholder="请输入出生地" class="input-selects-width-basic"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="备注">
            <el-input type="textarea" :autosize="{ minRows: 3, maxRows: 5}" class="input-selects-width-basic" placeholder="请输入备注"
                      v-model="form.remark"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div style="text-align: center">
      <el-button v-if="formStatus == 'see'" @click="cancel('form')">返回列表</el-button>
      <el-button v-else @click="cancel('form')">取 消</el-button>
      <el-button v-if="formStatus == 'create'"  type="primary" @click="create('form')">确 定</el-button>
      <el-button v-if="formStatus == 'update'"  type="primary" @click="update('form')">确 定</el-button>
    </div>
    <!-- 组织机构管理选择树 -->
    <el-dialog :visible.sync="dialogOrgTreeVisible" ref="diaog_tree" width="25%">
      <org-tree @org_tree="getTreeNodes" @org_tree_cancel="orgCancelTree" :checkOrgId="checkOrgId" ref="orgTreeRef" ></org-tree>
    </el-dialog>
    <el-dialog :visible.sync="dialogCroppaVisible" width="40%" >
      <el-row>
        <el-col :span="12" class="croppa-style">
          <croppa
            v-model="myCroppa"
            :width="295"
            :height="412"
            placeholder="选择图片"
            :placeholder-font-size="0"
            :disabled="false"
            :prevent-white-space="false"
            :show-remove-button="false">
          </croppa>
        </el-col>
        <el-col :span="12" class="croppa-style">
          <div class="output_img_croppa">
            <img v-if="imgOption.imgUrl != ''" :src="imgOption.imgUrl">
          </div>
        </el-col>
      </el-row>
      <div style="text-align: center; margin-top: 15px;">
        <el-button-group>
          <el-button type="primary" native-type="submit"  @click="removeGenerateImage">重新选择照片</el-button>
          <el-button type="primary"  @click="generateImage">裁剪</el-button>
          <el-button type="primary"  @click="successGenerateImage()" >确定</el-button>
        </el-button-group>
      </div>
    </el-dialog>
  </el-card>
</template>
<script>
  /*
  import { getObj, addObj, putObj } from 'api/admin/user/index';
  import { getDictByCode } from 'api/admin/dict/index';
  // import OrgTree from '@/views/admin/org/checkOrg';
  import axios from 'axios';
  // import { addObj as addRoleObj } from 'api/admin/role/user';
  // import { seleByCode } from 'api/admin/role/index';

  export default {
    props: ['userId'],
    // components: {
    //   OrgTree
    // },
    data() {
      return {
        myCroppa: null,
        imgOption: {
          imgUrl: ''
        },
        fsImageOption: {
          file: null,
          sysname: 'esp-food'
        },
        rules: {
          name: [
            { required: true, message: '请输入用户名', trigger: 'blur'}
          ],
          card: { required: true, message: '请输入身份证号码', trigger: 'blur'},
          political: { required: true, message: '请选择政治面貌', trigger: 'blur'},
          birthday: { required: true, message: '请选择出生日期', trigger: 'blur'},
          sex: { required: true, message: '请选择性别', trigger: 'blur'},
          nation: { required: true, message: '请输入名族', trigger: 'blur'},
          email: [
            { type: 'email', message: '请输入有效的邮箱格式', trigger: 'blur' },
            { required: true, message: '请输入邮箱', trigger: 'blur'}
          ],
          mobilePhone: [
            {pattern: /^1[34578]\d{9}$/, message: '目前只支持中国大陆的手机号码', trigger: 'blur'},
            { required: true, message: '请输入手机号码', trigger: 'blur'}
          ],
          workCard: { required: true, message: '请输入工作证号码', trigger: 'blur'}
        },
        form: this.initObj(),
        formStatus: 'see',
        sexList: [],
        politicalList: [],
        marriageList: [],
        accTypeList: [],
        dialogOrgTreeVisible: false,
        checkOrgId: '',
        checkOrgStatus: '',
        dialogCroppaVisible: false
      };
    },
    created() {
      this.formStatus = 'create';
      this.initList();
      if (this.userId !== '') {
        this.getObj();
      }
    },
    methods: {
      imageClick() {
        this.dialogCroppaVisible = true
      },
      successGenerateImage: function() {
        const url = this.myCroppa.generateDataUrl()
        const param = new FormData()  // 创建form对象
        param.append('file', url)  // 通过append向form对象添加数据
        param.append('sysname', 'esp-admin')
        console.log(param.get('file')) // FormData私有类对象，访问不到，可以通过get判断值是否传进去
        const config = {
          headers: {'Content-Type': 'multipart/form-data'}
        }
        // 添加请求头
        axios.post('http://10.27.213.66:9091/api/upload/savePic', param, config).then(response => {
          this.dialogCroppaVisible = false
          this.form.imageSrc = 'http://10.27.213.66:9091/images/' + response.data.filePath
        })
      },
      removeGenerateImage: function() {
        this.myCroppa.chooseFile()
      },
      generateImage: function() {
        const url = this.myCroppa.generateDataUrl()
        this.myCroppa.output
        if (!url) {
          alert('no image')
          return
        }
        this.imgOption.imgUrl = url
      },
      initList() {
        getDictByCode('XB').then(data => {
          this.sexList = data.data
        })
        getDictByCode('ZZMM').then(data => {
          this.politicalList = data.data
        })
        getDictByCode('HYZK').then(data => {
          this.marriageList = data.data
        })
        getDictByCode('HKLX').then(data => {
          this.accTypeList = data.data
        })
      },
      getObj() {
        getObj(this.userId).then(response => {
          this.form = response.data
          this.formStatus = 'update'
        })
      },
      cancel(formName) {
        this.$router.push({
          path: '/admin/user'
        })
      },
      initObj() {
        return {
          id: null,
          imageSrc: null,
          name: null,
          sex: null,
          nation: null,
          birthday: null,
          marriage: null,
          workCard: null,
          political: null,
          mobilePhone: null,
          platesNo: null,
          homePhone: null,
          zipCode: null,
          accType: null,
          domicilePlace: null,
          birthPlace: null,
          nativePlace: null,
          card: null,
          homeAddress: null,
          remark: null,
          email: null,
          empSort: null,
          deptId: null,
          deptName: null,
          officeId: null,
          officeName: null,
          orgName: null,
          orgId: null,
          superAdmin: null,
          checkIp: null,
          username: null,
          password: null,
          confirmPassword: null
        }
      },
      create(formName) {
        // 生成主键
        const uuidv1 = require('uuid/v1');
        let uId = uuidv1();
        uId = uId.split('-').join('')
        this.form.id = uId;
        this.userId = uId;

        const set = this.$refs;
        set[formName].validate(valid => {
          if (valid) {
            addObj(this.form).then(() => {
              this.$notify({
                title: '成功',
                message: '创建成功',
                type: 'success',
                duration: 2000
              });
              seleByCode('COMMON_USER').then(response => {
                addRoleObj(response.data.id, uId);
              });
              this.formStatus = 'see'
              this.$emit('getUserId', this.form.id)
            })
          } else {
            return false
          }
        })
      },
      update(formName) {
        const set = this.$refs
        set[formName].validate(valid => {
          if (valid) {
            putObj(this.userId, this.form).then(() => {
              this.$notify({
                title: '成功',
                message: '创建成功',
                type: 'success',
                duration: 2000
              })
              this.formStatus = 'see'
            })
          } else {
            return false
          }
        })
      },
      checkOrg(id, name) {
        this.checkOrgId = id
        this.checkOrgStatus = name
        this.dialogOrgTreeVisible = true
        this.$nextTick(() => {
          this.$refs.orgTreeRef.getOrgTree();
        })
      },
      getTreeNodes(msg) {
        let cid = '';
        let corg = '';
        cid = msg.map(item => item.id).join();
        corg = msg.map(item => item.label).join();
        if (this.checkOrgStatus === 'deptId') {
          this.form.deptName = corg
          this.form.deptId = cid
        } else if (this.checkOrgStatus === 'officeId') {
          this.form.officeId = cid;
          this.form.officeName = corg
        } else if (this.checkOrgStatus === 'orgId') {
          this.form.orgId = cid;
          this.form.orgName = corg;
        }
        this.dialogOrgTreeVisible = false
      },
      orgCancelTree() {
        this.dialogOrgTreeVisible = false
      },
      blurEmail() {
        this.form.username = this.form.email.split('@')[0];
      },
      blurCard() {
        const y = this.form.card.substr(6, 4);
        const m = this.form.card.substr(10, 2);
        const d = this.form.card.substr(12, 2);
        const bir = y + '-' + m + '-' + d + ' 00:00:00'
        this.form.birthday = bir
      }
    }
  }
  */
</script>

<style  rel="stylesheet/scss" lang="scss" scoped>
  .input-selects-width-basic{ width: 330px; }
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 200px;
    height: 200px;
    line-height: 200px;
    text-align: center;
  }
  .avatar {
    width: 200px;
    height: 200px;
    display: block;
  }
  .avatarImg{
    width: 200px;
    height: 250px;
    display: block;
    border: 1px gray solid !important;
  }
  .uploadImageSrc label { margin-top: 70px; }
  .input-org { width: 265px; }
  .croppa-container{
    border: 1px solid green;
    height: 415px;
    width: 297px;
  }
  .output_img_croppa {
    height: 412px;
    width: 295px;
    border: 1px solid green;
  }
  .output_img_croppa img {
    height: 100%;
    width: 100%;
  }
  .croppa-style {
    padding: 0px 20px;
  }
  .formImage {
    height: 100%;
    width: 100%;
  }
</style>
