<template>
  <div>
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/index/welcome' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>用户管理</el-breadcrumb-item>
      <el-breadcrumb-item>用户列表</el-breadcrumb-item>
    </el-breadcrumb>

    <el-card class="box-card">

      <el-row :gutter="30">
        <el-col :span="10">
          <div class="grid-content bg-purple">

            <el-input placeholder="请输入内容" v-model="searchMessage" class="input-with-select">
              <el-select v-model="select" slot="prepend" placeholder="查询条件">
                <el-option label="按用户ID号" value="1"></el-option>
                <el-option label="按用户昵称" value="2"></el-option>
              </el-select>
              <el-button slot="append" icon="el-icon-search" @click="searchUser"></el-button>
            </el-input>

          </div>
        </el-col>
        <el-col :span="14">
          <div class="grid-content bg-purple">

            <el-button type="primary" round @click="addVisible = true">添加用户</el-button>

          </div>
        </el-col>
      </el-row>
      <el-row class="myTable">
        <el-table
          :data="users"
          stripe
          style="width: 100%"
          :border="true">
          <el-table-column
            prop="id"
            label="用户ID"
            :width="70"
          >
          </el-table-column>
          <el-table-column
            prop="roleName"
            label="角色"
          >
          </el-table-column>
          <el-table-column
            prop="loginName"
            label="账号">
          </el-table-column>
          <el-table-column
            prop="nickname"
            label="昵称">
          </el-table-column>
          <el-table-column
            prop="email"
            label="邮箱">
          </el-table-column>

          <el-table-column prop="flag1" label="用户状态" :width="80">

            <template slot-scope="scope">
              <el-switch
                v-model="scope.row.flag1"
                active-color="#13ce66"
                inactive-color="#ff4949"
                @change="changeStatus(scope.row)">
              </el-switch>
            </template>

          </el-table-column>
          <el-table-column label="操作">

            <template slot-scope="scope">

              <el-tooltip class="item" effect="dark" content="修改用户的信息" placement="top-start">
                <el-button
                  round
                  type="success"
                  size="mini"
                  icon="el-icon-edit"
                  @click="handleEdit(scope.row)">修改
                </el-button>
              </el-tooltip>
              <el-tooltip class="item" effect="dark" content="删除用户，谨慎操作" placement="top-start">
                <el-button
                  round
                  size="mini"
                  type="danger"
                  icon="el-icon-delete"
                  @click="handleDelete(scope.row)">删除
                </el-button>
              </el-tooltip>

            </template>
          </el-table-column>

        </el-table>
      </el-row>
      <el-row class="mypagination">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="queryParam.pageNum"
          :page-sizes="[2, 5, 8, 10]"
          :page-size="queryParam.pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="pageTotal">
        </el-pagination>
      </el-row>

    </el-card>

    <el-dialog
      title="编辑用户信息"
      :visible.sync="editVisible"
      width="50%" height="50%"
    >
      <span slot="footer" class="dialog-footer">
        <el-button @click="editVisible = false">取 消</el-button>
        <el-button type="primary" @click="editVisible = false">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog :title="isEditDialog? '编辑用户':'添加用户'" :visible.sync="addVisible" width="50%" @close="closeAddDialog">

      <el-form :model="addFormData" :rules="addRuleForm" ref="addFormRef" label-width="100px" class="demo-ruleForm">
        <el-form-item label="账号：" prop="loginName">
          <el-input v-model="addFormData.loginName" :disabled="isEditDialog?true:false"></el-input>
        </el-form-item>
        <el-form-item label="密码：" prop="password">
          <el-input v-model="addFormData.password"  :placeholder="isEditDialog?'不填写默认原密码':''"></el-input>
        </el-form-item>
        <el-form-item label="昵称：" prop="nickname">
          <el-input v-model="addFormData.nickname"></el-input>
        </el-form-item>
        <el-form-item label="邮箱：" prop="email">
          <el-input v-model="addFormData.email"></el-input>
        </el-form-item>

        <el-form-item label="角色：" prop="role_id">
          <el-select v-model="addFormData.role_id" placeholder="请选择用户角色">

            <el-tooltip class="item" effect="dark" v-for="role in roles" :key="role.id" :content="role.describe" placement="right">
              <el-option :label="role.name" :value="role.id" ></el-option>
            </el-tooltip>


<!--            <el-option label="普通用户" value="0"></el-option>-->
          </el-select>
        </el-form-item>
        <el-form-item label="使用状态：" prop="flag">
          <el-select v-model="addFormData.flag" placeholder="请选择用户角色">
            <el-option label="添加该用户，即可登录" value="1"></el-option>
            <el-option label="添加该用户，不可登录" value="0"></el-option>
          </el-select>
        </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="addVisible = false">取 消</el-button>
        <el-button type="primary" @click="addUser">{{isEditDialog?'更新':'添加'}}</el-button>
      </span>
    </el-dialog>


  </div>
</template>

<script>

  import {hex_md5} from "../../assets/js/md5";

  export default {
    name: 'Users',
    data () {

      var reg = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/
      var checkEmail = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入邮箱'))
        } else if (!reg.test(value)) {

          callback(new Error('请输入正确的邮箱格式'))
        } else {
          callback()
        }
      }

      return {
        select: null,
        searchMessage: '',
        users: [],
        pageTotal: null,
        queryParam: {
          pageNum: 1,
          pageSize: 2,
          nickname: null,
          id: null
        },
        editVisible: false,
        addVisible: false,

        addFormData: {},
        addRuleForm: {
          loginName: [
            {
              required: true,
              message: '请输入账号',
              trigger: 'blur'
            },
            {
              min: 4,
              message: '至少有四个字符',
              trigger: 'blur'
            }
          ],
          password: [
            {
              required: this.isEditDialog?true:false,
              message: '请输入密码',
              trigger: 'blur'
            },
            {
              min: 4,
              message: '至少有四个字符',
              trigger: 'blur'
            }
          ],
          nickname: [
            {
              required: true,
              message: '请输入昵称',
              trigger: 'blur'
            },
            {
              min: 1,
              message: '至少有一个字符',
              trigger: 'blur'
            }
          ],
          email: [
            {
              required: true,
              message: '请输入邮箱',
              trigger: 'blur'
            },
            {
              validator: checkEmail,
              trigger: 'blur'
            }
          ]

        },

        isEditDialog: false,
        roles: []
      }
    },
    methods: {

      //直接关闭用户弹框
      closeAddDialog () {
        this.addVisible = false
        this.addFormData = {}
        this.isEditDialog = false;
      },

      //打开修改用户弹框 - 确定是编辑框还是添加用户狂
      handleEdit (row) {

        this.addFormData = row
        this.addVisible = true
        this.isEditDialog = true
        // this.editVisible = true;
      },

      //删除用户
      async handleDelete (row) {

        var user_id = row.id

        this.$confirm('此操作将永久删除选中用户, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(async () => {
          var { data: result } = await this.$http.delete('http://localhost:8080/apiGateway/memberService/members/' + user_id)
          if (result.effective) {
            this.$message({
              message: '成功删除ID是' + user_id + '的用户',
              type: 'success'
            })
            this.getUsers()
          } else {
            this.$message.error('删除失败，请在尝试')
          }
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })

      },

      //添加、编辑用户 - 密码需要MD5加密
      async addUser () {

        //用户添加弹框
        if (this.isEditDialog == false) {
          this.$refs.addFormRef.validate(async (valid) => {
            if (valid) {

              var jsonObj = JSON.stringify(this.addFormData);
              var paramObj = JSON.parse(jsonObj);

              paramObj.password = hex_md5(paramObj.password);

              var param = this.$qs.stringify(paramObj)
              var { data: result } = await this.$http.post('http://localhost:8080/apiGateway/memberService/members/register', param)
              if (result.effective) {
                this.addVisible = false
                this.$message({
                  showClose: true,
                  message: '成功添加用户',
                  type: 'success'
                })
                this.addFormData = {}
              } else {
                this.$message({
                  showClose: true,
                  message: '添加失败，请重新提交或者更改',
                  type: 'error'
                })
              }
            } else {
              this.$message({
                showClose: true,
                message: '添加失败，请填写完整的信息',
                type: 'error'
              })
              return false
            }
          })

        //用户编辑弹框
        } else {
          this.$refs.addFormRef.validate(async (valid) => {
            if (valid) {

              var jsonObj = JSON.stringify(this.addFormData);
              var paramObj = JSON.parse(jsonObj);

              paramObj.password = hex_md5(paramObj.password);

              var { data: result } = await this.$http.put('http://localhost:8080/apiGateway/memberService/members/', paramObj)
              if (result.effective) {
                this.$message({
                  message: '成功更新ID是' + this.addFormData.id + '用户的信息',
                  type: 'success'
                });
                this.getUsers();
                this.closeAddDialog();
              } else {
                this.$message.error('更新失败：请重新尝试')
              }

            } else {
              this.$message.error('填写不符合要求：请认真填写更改的用户信息')
              return false
            }
          })
        }

      },

      //获取用户信息
      async getUsers () {

        var { data: result } = await this.$http.get('http://localhost:8080/apiGateway/memberService/members/', { params: this.queryParam })

        if (result.effective) {
          this.pageData = result.data

          //用户列表中的用户信息
          this.users = result.data.list
          //数据库总数据条数
          this.pageTotal = result.data.total
          //让用户信息添加角色信息
          this.users.forEach((item, index) => {

              var indexxx = this.roles.findIndex((item1) => {
                return item1.id == item.role_id
              })
              item.roleName = this.roles[indexxx].describe

          })
        } else {
          this.$message.error('获取失败：可能无数据，请重新请求用户列表数据！')
        }

      },

      // 页码改变获取页面改变的值 - 页码改变重新获取数据
      handleCurrentChange (pageNum) {
        console.log(pageNum)
        this.queryParam.pageNum = pageNum
        this.getUsers()
      },

      //pageSize改变 -参数为改变后的值pageSize - 每页显示条数改变重新获取数据
      handleSizeChange (pageSize) {
        this.queryParam.pageSize = pageSize
        this.queryParam.pageNum = 1
        this.getUsers()
      },

      //更改用户状态 - 编辑 - 封号操作
      async changeStatus (row) {

        var id = row.id
        var flag = row.flag1 ? 1 : 0
        var params = {
          id: id,
          flag: flag
        }
        var { data: result } = await this.$http.put('http://localhost:8080/apiGateway/memberService/members/', params)

        if (result.effective) {

          this.$message({
            message: result.message,
            type: 'success'
          })
        } else {
          this.$message.error(result.message)
        }

      },

      //查询用户 - 不同条件的查询的情况
      searchUser () {
        if (this.select == 1 || this.select == '') {
          this.queryParam.id = parseInt(this.searchMessage)
          this.queryParam.nickname = null
        } else {
          this.queryParam.nickname = this.searchMessage
          this.queryParam.id = null
        }
        this.getUsers()
      },

      //获取所有角色信息
      async getRoles() {

        var {data:result} = await this.$http.get("http://localhost:8080/apiGateway/memberService/roles/");

        if(result.effective) {
          this.roles = result.data.list;
          this.$message({
            message: result.message,
            type: 'success'
          });
        }else {
          this.$message({
            message: result.message,
            type: 'danger'
          });
        }

      }
    },

    //进入用户管理页面，即获取角色详细信息、用户详细信息
    created () {
      //获取角色数据
      this.getRoles();
      setTimeout( ()=> {

        //获取用户数据
        this.getUsers()
      }, 500)

    }
  }
</script>

<style scoped>
  .grid-content {
    float: left;
  }

  .myTable {
    margin-top: 20px;
  }

  .mypagination {
    margin-top: 20px;
  }

  .el-form .el-select {
    float: left;
  }

  .grid-content .el-select {
    width: 120px;
  }
</style>
