<template>


  <el-container>
    <!--头部 -->
    <el-header>
      <h1>后台管理系统</h1>
      <el-button type="danger" round @click="loginOut">退出</el-button>
    </el-header>
    <el-container>
      <el-aside :width="notCollapse? '64px': '200px'">

        <el-menu
          background-color="#435970"
          text-color="#fff"
          active-text-color="#ffd04b"
          :unique-opened="true"
          :collapse="notCollapse"
          :collapse-transition="false"
          :router="true"
          :default-active="$route.path">
          <el-switch
            v-model="notCollapse"
            active-color="#ff4949"
            inactive-color="#13ce66">
          </el-switch>
          <!--一级菜单 -->
          <el-submenu :index="menu.id.toString()" v-for="(menu) in menuList" :key="menu.id">
            <template slot="title">
              <i :class="iconClass[menu.id.toString()]"></i>
              <span>{{menu.auth_name}}</span>
            </template>

            <!--二级菜单 -->
            <el-menu-item :index="children.path" v-for="(children) in menu.childrens" :key="children.id">
              <template slot="title">
                <i class="el-icon-menu"></i>
                <span>{{children.auth_name}}</span>
              </template>

            </el-menu-item>
          </el-submenu>

        </el-menu>

      </el-aside>
      <el-main>
          <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>


</template>

<script>
  export default {
    name: 'index',
    data () {
      return {
        menuList: [],
        iconClass: {
          '1': 'iconfont icon-shangpinguanli',
          '3': 'iconfont icon-dingdanguanli',
          '6': 'iconfont icon-yonghuguanli',
          '8': 'iconfont icon-quanxianguanli',
          '10': 'iconfont icon-icon-test'
        },
        //false就是展开菜单
        notCollapse: false
      }
    },
    methods: {
      //退出登录,跳回登录页面、删除Token 本地缓存
      loginOut: function () {
        window.sessionStorage.clear()
        this.$router.push('/login')
      },

      //获取菜单信息 - 根据用户ID
      async getMenuList () {

        var user_id = window.sessionStorage.getItem("user_id");
        var { data: result } = await this.$http.get('memberService/roles/menus/' + user_id)
        if (result.effective) {
          this.menuList = result.data
        } else {
          this.$message.error(result.message)
        }
      }

    },

    //进入首页，即向服务器请求获取首页菜单信息‘
    //ps：不同的权限获取的菜单列表是不一样的 - 我只做了静态页面的屏蔽，后台权限验证还没做
    created () {
      this.getMenuList()
    }
  }
</script>

<style scoped lang="less">
  .el-container {
    width: 100%;
    height: 100%;
  }

  .el-header {
    background: #25313F;

    .el-button {
      float: right;
      margin-top: 10px;
    }

    h1 {
      color: white;
      line-height: 60px;
      float: left;
      font-size: 20px;
    }
  }

  .el-aside {
    background: #435970;
    color: white;

    .el-menu {
      text-align: left;
      border-right: 0px;
      .iconfont {
        margin-right: 18px;
        font-size: 18px;
      }
      .el-switch {
        left:50%;
        transform: translate(-50%);
        margin-bottom:10px
      }
    }


  }

  .el-main {
    background: #E9EDF1;
  }


</style>
