module.exports = {
  devServer: {
    open: true,
    port: 81,

    //取消域名检查
    disableHostCheck: true,

    // 打包的配置
    // publicPath: './',
    // publicPath: process.env.NODE_ENV === 'production' ? '/fruitManagement/' : '/',
    // outputDir: 'dist',
    // assetsDir:'fruit_management',
  }
}
