<template>
  <div class="login_container">
    <div class="login_box">
      <!-- 头像区域 -->
      <div class="avatar_box">
        <img src="@/assets/images/login_icon.jpg" alt="">
      </div>

      <el-form class="login_form" ref="loginForm" :rules="rules" :model="loginData" label-width="80px">
        <el-form-item label=" 账号" prop="loginName">
          <el-input prefix-icon="iconfont icon-iconzh1" v-model="loginData.loginName"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input prefix-icon="iconfont icon-mima" v-model="loginData.password" type="password"></el-input>
        </el-form-item>

        <el-form-item class="btns">
          <el-button type="primary" round @click="submitLoginForm('loginForm')">登录</el-button>
          <el-button type="danger" round @click="resetLoginForm('loginForm')" >重置</el-button>
        </el-form-item>

      </el-form>

    </div>
  </div>
</template>

<script>

  import {hex_md5} from "../assets/js/md5";

  export default {
    name: 'login',
    data() {
      return {
        loginData: {
          loginName: '',
          password: ''
        },
        rules: {
          loginName: [
            {
              required: true,
              message: '请输入账号',
              trigger: 'blur'
            },
            {
              min: 4,
              message: '账号长度必须大于四个字符',
              trigger: 'blur'
            }
          ],
          password: [
            {
              required: true,
              message: '请输入密码',
              trigger: 'blur'
            },
            {
              min: 4,
              message: '密码的长度必须大于四个字符',
              trigger: 'blur'
            }
          ],
        }
      }
    },
    methods: {

        //登录 - 登陆前密码需要MD5加密
       submitLoginForm(ref) {


        this.$refs[ref].validate(async (valid, obj) => {

          //前端校验验证成功
          if (valid) {

            //设置请求的信息以及所需参数
            //1. 将账号、密码信息复制一份、并且密码加密
            var loginDataStr = JSON.stringify(this.loginData);
            var loginObj = JSON.parse(loginDataStr);
            loginObj.password = hex_md5(loginObj.password);

            var param = this.$qs.stringify(loginObj);
            var headers = { headers: { "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8"} }

            // 发送登录请求
            var {data:data} = await this.$http.post("http://localhost:8080/apiGateway/authService/login", param, headers);
            var message = '';
            var type = '';

            // 根据后台返回数据，做出不同的决策
            if(data.effective == true) {
              message = "登录成功！"
              type="success";
              window.sessionStorage.setItem("token", data.data.token);
              window.sessionStorage.setItem("user_id", data.data.user_id);
              window.sessionStorage.setItem("role_id", data.data.role_id);
              this.$router.push('/index');
            }else {
              message = "登录失败！";
              type="warning";
              this.resetLoginForm(ref);
            }

            this.$message({
              showClose: true,
              message: message,
              type: type
            });

          //前端验证失败
          } else {

            this.$message({
              showClose: true,
              message: '请按要求输入账号、密码信息!',
              type: 'error'
            });
            return false;
          }
        });
      },

      //重置表单的值
      resetLoginForm(ref) {
        this.$refs[ref].resetFields();
      }

    }

  }
</script>

<style lang="less" scoped>
  .login_container {
    background-color: #2b4b6b;
    height: 100%;
  }

  .login_box {
    width: 450px;
    height: 300px;
    background-color: #fff;
    border-radius: 3px;
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);

    .avatar_box {
      height: 130px;
      width: 130px;
      border: 1px solid #eee;
      border-radius: 50%;
      padding: 10px;
      box-shadow: 0 0 10px #ddd;
      position: absolute;
      left: 50%;
      transform: translate(-50%, -50%);
      background-color: #fff;
      img {
        width: 100%;
        height: 100%;
        border-radius: 50%;
        background-color: #eee;
      }
    }
  }

  .login_form {
    position: absolute;
    /*bottom: 0;*/
    width: 100%;
    padding: 0 20px;
    box-sizing: border-box;
    left: -13px;
    bottom: 10px;
  }

  .btns {
    display: flex;
    justify-content: flex-end;
  }
</style>
