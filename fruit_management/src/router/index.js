import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '@/views/Login'
import Index from '@/views/Index'
import Welcome from '@/components/index/Welcome'
import Users from '@/components/index/Users'
import Products from '@/components/index/Products'
import PermissionList from '@/components/index/PermissionList'
import RoleList from '@/components/index/RoleList'
import OrderList from '@/components/index/OrderList'

//挂载路由
Vue.use(VueRouter)

//路由匹配规则
const routes = [
  {
    path: '/',
    redirect: "/login"

  },{
    path: '/login',
    name: 'login',
    component: Login
  }, {
    path: '/index',
    name: 'index',
    redirect: '/index/welcome',
    component: Index,
    children:[{
        path: '/index/welcome',
        name: 'welcome',
        component: Welcome
      },{
        path: '/index/users',
        name: 'users',
        component: Users
      },{
      path: '/index/products',
      name: 'products',
      component: Products
      }, {
      path: '/index/permissionList',
      name: 'permissionList',
      component: PermissionList
    }, {
      path: '/index/roleList',
      name: 'roleList',
      component: RoleList
    }, {
      path: '/index/orderList',
      name: 'orderList',
      component: OrderList
    }]
  }
]





const router = new VueRouter({
  routes
})


// 目标、原路由对象、放行行函数
//如果用户未登录，强行通过地址栏登录也不可以
router.beforeEach( (target, from, next) => {

  var token = window.sessionStorage.getItem('token');

  var targetPath = target.path

  console.log(token);
  if(targetPath != '/login') {

    if(token != null && token != undefined) {
      next()
    }else {
      next("/login")
    }
  }else {
    next();
  }

});

export default router


//测试打包
/*export default new Router({
  //如打包文件存放在Tomcat/webapps/aoxinwechat下，
  //那么base就这么写
  base: '/fruitManagement/',
  mode: 'history',
  routes: routes
})*/
