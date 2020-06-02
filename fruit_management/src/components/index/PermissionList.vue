<template>

  <div>
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/index/welcome' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>权限管理</el-breadcrumb-item>
      <el-breadcrumb-item>权限列表</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card class="box-card">
      <el-table
        :data="tableData"
        border
        stripe
        style="width: 100%">
        <el-table-column
          prop="id"
          label="ID"
          width="70"
          >
        </el-table-column>
        <el-table-column
          prop="auth_name"
          label="姓名"
          >
        </el-table-column>
        <el-table-column
          prop="path"
          label="路由">
        </el-table-column>
        <el-table-column prop="level" label="权限级别">

          <template slot-scope="scope">
            <el-tag :type="scope.row.level == 0 ? '' : (scope.row.level == 1 ?  'success':'warning')">{{scope.row.level == 0 ? '一级' :(scope.row.level == 1 ?  '二级':'三级')}}</el-tag>
          </template>

        </el-table-column>
      </el-table>
    </el-card>
  </div>

<!--  <el-tag>标签一</el-tag>-->
<!--  <el-tag type="success">标签二</el-tag>-->

</template>

<script>
  export default {
    name: 'PermissionList',
    data() {
      return {
        tableData: [],
      }
    },


    methods: {

      //获取权限数据
      async getTableData() {

        var user_id = window.sessionStorage.getItem("user_id");

        var {data:result} = await this.$http.get("http://localhost:8080/apiGateway/memberService/roles/menus/"+ user_id + "?dataType=list");
        if(result.effective) {
          this.$message({
            message: '权限列表数据获取成功！',
            type: 'success'
          });
          this.tableData = result.data;
        }else {
          this.$message.error('获取权限列表数据失败：请重新请求获取');
        }

      }
    },

    //进入权限列表 即获取权限数据
    created() {
      this.getTableData();
    }
  }
</script>

<style scoped>

</style>
