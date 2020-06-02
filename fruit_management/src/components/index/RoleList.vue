<template>
  <div>
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/index/welcome' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>权限管理</el-breadcrumb-item>
      <el-breadcrumb-item>角色列表</el-breadcrumb-item>
    </el-breadcrumb>


    <el-card class="box-card">

      <el-row>
        <el-col :span="8">
          <div class="grid-content bg-purple">
            <el-button class="myBtn" type="primary" round @click="addDataDialog">添加角色</el-button>
          </div>
        </el-col>
      </el-row>

      <el-table
        :data="treeRoleMenu"
        border
        stripe
        style="width: 100%">


        <el-table-column
          type="expand"
        >
          <template slot-scope="scope">
            <el-row :class="[index==0?'fatherRow':'', 'bdbootom']" v-for="(item,index) in scope.row.umsRoleMenus"
                    :key="index">
              <el-col :span="5">
                <el-tag>{{item.auth_name}}</el-tag>
                <i class="el-icon-caret-right"></i>
              </el-col>

              <el-col :span="19">

                <el-row v-for="(item1) in item.childrens" :key="item1.id">
                  <el-col :span="5">
                    <el-tag type="success">{{item1.auth_name}}</el-tag>
                    <i class="el-icon-caret-right"></i>
                  </el-col>
                  <el-col :span="19">
                    <el-tag closable @close="deleteByRoleMenu(item2,scope.row)" class="myElTag" type="warning"
                            v-for="item2 in item1.childrens" :key="item2.id">{{item2.auth_name}}
                    </el-tag>
                  </el-col>
                </el-row>

              </el-col>


            </el-row>
          </template>

        </el-table-column>

        <el-table-column
          prop="id"
          label="ID"
          width="70"
        >
        </el-table-column>
        <el-table-column
          prop="name"
          label="角色"
        >
        </el-table-column>
        <el-table-column
          prop="describe"
          label="描述">
        </el-table-column>
        <el-table-column prop="menus" label="操作">

          <template slot-scope="scope">

            <el-tooltip class="item" effect="dark" content="修改用户的信息" placement="top-start">
              <el-button
                round
                type="success"
                size="mini"
                icon="el-icon-edit"
                @click="updateDialog(scope.row)">修改
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
            <el-tooltip class="item" effect="dark" content="给予用户权限，谨慎操作" placement="top-start">
              <el-button
                round
                size="mini"
                type="warning"
                icon="el-icon-first-aid-kit"
                @click="openPermissionsDialog(scope.row)">分配权限
              </el-button>
            </el-tooltip>

          </template>

        </el-table-column>
      </el-table>
    </el-card>


    <el-dialog :title="isEditDialog? '编辑角色':'添加角色'" :visible.sync="addVisible" width="50%" @close="closeAddDialog">

      <el-form :model="addFormData" :rules="addRuleForm" ref="addFormRef" label-width="100px" class="demo-ruleForm">

        <el-form-item label="ID：" prop="id" v-show="isEditDialog">
          <el-input v-model="addFormData.id" :disabled="isEditDialog?true:false"></el-input>
        </el-form-item>

        <el-form-item label="角色：" prop="name">
          <el-input v-model="addFormData.name"></el-input>
        </el-form-item>
        <el-form-item label="描述：" prop="describe">
          <el-input v-model="addFormData.describe" :placeholder="isEditDialog?'填写角色作用、职责等信息':''"></el-input>
        </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="closeAddDialog">取 消</el-button>
        <el-button type="primary" @click="addRole">{{isEditDialog?'更新':'添加'}}</el-button>
      </span>
    </el-dialog>


    <el-dialog
      title="分配权限"
      :visible.sync="permissionsDialogVisible"
      width="50%"
      @close="closePermissionsDialog"
      >

      <el-tree
        ref="tree"
        :data="displayRoleMenu"
        show-checkbox
        node-key="id"
        :default-checked-keys="checkedKeys"
        default-expand-all
        :highlight-current="true"
        :props="treeProps">
      </el-tree>

      <span slot="footer" class="dialog-footer">
        <el-button @click="permissionsDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="assignPermissions">分 配</el-button>
     </span>
    </el-dialog>

  </div>

</template>

<script>

  import $ from 'jquery'

  export default {
    name: 'RoleList',
    data () {

      return {
        tableData: [],
        treeRoleMenu: [],
        dataTotal: 0,
        queryParam: {
          pageNum: 0,
          pageSize: 0
        },
        isEditDialog: false,
        addFormData: {},
        addRuleForm: {
          name: [
            {
              required: true,
              message: '请输入角色名',
              trigger: 'blur'
            },
            {
              min: 2,
              message: '至少有两个字符',
              trigger: 'blur'
            }
          ],
          describe: [
            {
              required: true,
              message: '请输入角色描述',
              trigger: 'blur'
            },
          ],
        },
        addVisible: false,
        permissionsDialogVisible: false,
        displayRoleMenu: [],
        treeProps: {
          label: 'auth_name',
          children: 'childrens'
        },
        expandedKeys: [],
        checkedKeys: [],
        checkRoleId: null
      }

    },
    methods: {
      // 分配角色权限
      assignPermissions() {
        //1. 找到所有打勾的权限ID
        var menuIdss = [1,2,3,4,5,6,7,8,9,10,15];

        var role_id = window.sessionStorage.getItem("role_id")

        //第三级权限ID以及当前权限下的所有子菜单都被选中的父级菜单
        var menuId1 = this.$refs.tree.getCheckedKeys();

        // 一级权限、二级权限(其子权限必须都被选中)ID
        var menuId2 = this.$refs.tree.getHalfCheckedKeys();

        //将menuId2 与 menuId1 菜单集合
        menuId1.forEach( (item,index) => {

          var index = menuIdss.findIndex( (item1) => {
            return item1 == item
          });

          if(index == -1)
            menuIdss.push(item);

        })
        menuId2.forEach( (item,index) => {

          var index = menuIdss.findIndex( (item1) => {
            return item1 == item
          });

          if(index == -1)
            menuIdss.push(item);

        })

        //将角色新的权限传给服务器进行记录
        this.$confirm('此操作将永久更改该角色权限, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(async () => {

          var params = {
            menuIds: menuIdss.toString()
          }
          var {data:result} = await this.$http.put("http://localhost:8080/apiGateway/memberService/roles/" + this.checkRoleId, params)

          if(result.effective) {
            this.$message({
              type: 'success',
              message: result.message
            });
            this.getRoles();
            this.permissionsDialogVisible =false;
            this.checkRoleId = null;
          }else {
            this.$message({
              type: 'danger',
              message: result.message
            });
          }

        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },

      //关闭权限弹框
      closePermissionsDialog() {
        this.permissionsDialogVisible = false;
        this.checkedKeys = [];
      },

      //打开权限对话框
      openPermissionsDialog(row) {

        //记录当前选中编辑的角色ID
        this.checkRoleId  =row.id;

        this.permissionsDialogVisible = true;

        var sessinRoleId = window.sessionStorage.getItem("role_id");

        this.treeRoleMenu.forEach((item,index) => {
           if(sessinRoleId == item.id) {
             this.displayRoleMenu = item.umsRoleMenus;
           }
        })

        //分配权限展开后需显示的权限 - 形成当前角色拥有的权限树
        this.expandedKeys = [];
        this.displayRoleMenu.forEach((item,index) => {
          this.expandedKeys.push(item.id)
        })



        //当前选中角色所有的MenuId
        // var currentRoleMenu = row.menuIds.split(",");


        ////分配权限展开后需选中的权限
        this.checkedKeys = []
        this.tableData.forEach((item, index) => {

          if(item.id == row.id) {
            item.umsRoleMenus.forEach((item1,index) => {
              var checkedKeys = this.checkedKeys;
              if(item1.level == 2) {
                checkedKeys.push(item1.id);
              }

            })
          }
        })

        // 渲染
        this.$refs.tree.setCheckedKeys(this.checkedKeys);

      },

      //获取角色数据
      async getRoles () {

        var { data: result } = await this.$http.get('http://localhost:8080/apiGateway/memberService/roles/', { params: this.queryParam })

        if (result.effective) {

          this.$message({
            message: result.message,
            type: 'success'
          })

          //数据库表有多少条数据
          this.dataTotal = result.data.total
          //显示到列表的数据
          this.tableData = result.data.list
          //将数据格式化树类型数据
          this.FormatTree(result.data)

        } else {
          this.$message.error(result.message)
        }

      },

      //删除角色
      async handleDelete (row) {

        this.$confirm('此操作将永久删除该角色, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(async () => {
          var { data: result } = await this.$http.delete('http://localhost:8080/apiGateway/memberService/roles/' + row.id)
          if (result.effective) {
            this.$message({
              type: 'success',
              message: '成功删除角色!'
            })
            this.getRoles()
          } else {
            this.$message.error('删除失败，请重新尝试')
          }
        }).catch(() => {

          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })

      },

      //添加、更新角色
      async addRole () {

        //更新角色
        if (this.isEditDialog) {
          var role_id = this.addFormData.id
          var { data: result } = await this.$http.put('http://localhost:8080/apiGateway/memberService/roles/' + role_id, this.addFormData)

          if (result.effective) {
            this.$message({
              message: result.message,
              type: 'success'
            })
            this.closeAddDialog()
            this.getRoles()
          } else {
            this.$message.error(result.message)
          }

          //增加角色
        } else {

          var { data: result } = await this.$http.post('http://localhost:8080/apiGateway/memberService/roles/', this.addFormData)

          if (result.effective) {

            this.$message({
              message: result.message,
              type: 'success'
            })
            this.closeAddDialog()
            this.getRoles()

          } else {
            //插入数据不成功，弹框不会关闭，等待重新点击继续添加角色
            this.$message.error(result.message)
          }
        }

      },

      //添加角色弹框打开
      addDataDialog () {
        this.addVisible = true
      },

      //更新角色弹框打开 - 仅仅只是打开编辑弹框
      updateDialog (row) {
        this.addVisible = true
        this.isEditDialog = true
        this.addFormData = row
      },

      //关闭添加角色弹框
      closeAddDialog () {
        this.addVisible = false
        this.isEditDialog = false
      },

      //格式化角色数据
      FormatTree (tableData) {

        var b = []
        $.extend(true, b, tableData.list)

        //角色循环遍历
        b.forEach((item, index) => {

          var menus = []

          //找到一级权限
          item.umsRoleMenus.forEach((umsRoleMenu, index) => {
            if (umsRoleMenu.level == 0) {
              umsRoleMenu.childrens = []
              menus.push(umsRoleMenu)
            }
          })

          //找到三级权限并挂载到二级权限
          item.umsRoleMenus.forEach((umsRoleMenu, index) => {
            if (umsRoleMenu.level == 2) {
              for (var i = 0; i < item.umsRoleMenus.length; i++) {
                if (item.umsRoleMenus[i].id == umsRoleMenu.father_id) {

                  if (item.umsRoleMenus[i].childrens == null) {
                    item.umsRoleMenus[i].childrens = []
                  }
                  item.umsRoleMenus[i].childrens.push(umsRoleMenu)
                  console.log('=======三级菜单')
                  console.log(umsRoleMenu)
                  break
                }
              }
            }
          })

          //将二级权限挂载到一级权限
          item.umsRoleMenus.forEach((umsRoleMenu, index) => {
            if (umsRoleMenu.level == 1) {
              menus.forEach((item, index) => {
                if (item.id == umsRoleMenu.father_id) {
                  item.childrens.push(umsRoleMenu)
                }
              })
            }
          })

          item.umsRoleMenus = menus

        })

        this.treeRoleMenu = b
      },

      //删除角色Menu
      deleteByRoleMenu (item, row) {


        //当前角色、以及角色MenuId
        var role_id = row.id
        var menuIds = row.menuIds

        //需要删除的MunuId
        var deleteId = item.id

        //从menuIds删除MenuId
        var menuIdss = menuIds.split(',')
        var deleteIndex = menuIdss.findIndex((item) => {
          return item == deleteId
        })
        menuIdss.splice(deleteIndex, 1)

        //即将上传的MenuIds
        var updateMenuIdss = menuIdss.toString()


        this.$confirm('此操作将永久删除该角色权限, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(async () => {

          var param = {
            menuIds: updateMenuIdss
          }

          var {data:result} = await this.$http.put("http://localhost:8080/apiGateway/memberService/roles/" + role_id, param)

          if(result.effective) {
            this.$message({
              type: 'success',
              message: result.message
            })

            this.getRoles();

          }else {
            this.$message({
              type: 'danger',
              message: result.message
            })
          }

        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })

      }

    },
    //表格创建即加载这个函数
    created () {
      this.getRoles()
    }
  }
</script>

<style scoped>
  .myBtn {
    float: left;
    margin-bottom: 20px;
  }

  .el-tag {
    margin-bottom: 10px;
  }

  .myElTag {
    margin-right: 20px;
  }

  .fatherRow {
    border-top: 1px solid #eee;

  }

  .bdbootom {
    border-bottom: 1px solid #eee;
    margin-bottom: 30px;
  }

</style>
