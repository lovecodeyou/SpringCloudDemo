<template>
  <div>

    <el-row>
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>商品管理</el-breadcrumb-item>
        <el-breadcrumb-item>商品列表</el-breadcrumb-item>
      </el-breadcrumb>
    </el-row>


    <el-card class="box-card">

      <el-row>

        <el-col :span="5">
          <el-select @change="changType" class="myCardHead" v-model="selectProductType" placeholder="请选择商品分类">

            <el-option
              :key="0"
              :label="'任意产品'"
              :value="-1">
            </el-option>

            <el-option
              v-for="productType in productTypes"
              :key="productType.id"
              :label="productType.name"
              :value="productType.id">
            </el-option>
          </el-select>
        </el-col>

        <el-col :span="13">
          <div style="opacity:0 ">fsdfd</div>
        </el-col>

        <el-col :span="6">
          <el-input placeholder="请输入产品名进行模糊查询, 不按产品分类" v-model="searchProductName" class="input-with-select">
            <el-button slot="append" icon="el-icon-search" @click="search"></el-button>
          </el-input>
        </el-col>


      </el-row>

      <el-row class="myCardTable">

        <el-table
          :data="products"
          border
          stripe
          :highlight-current-row="true"
          @expand-change="expandChange"
          :row-key="getRowKey"
          :expand-row-keys="expandKeys"
        >


          <el-table-column type="expand">

            <template>

              <el-tooltip class="item" effect="dark" content="编辑产品详情信息，谨慎操作" placement="bottom">
                <el-button @click="openEditproductDetail" class="myEditDetailBtn" type="success" icon="el-icon-edit"
                           circle></el-button>
              </el-tooltip>


              <el-row class="myDetailRow">

                <el-col :span="5">
                  <el-tag class="myTagDetail">产品描述：</el-tag>
                </el-col>
                <el-col :span="19">
                  <span>{{productDetail.description}}</span>
                </el-col>

              </el-row>
              <el-row class="myDetailRow">
                <el-col :span="5">
                  <el-tag class="myTagDetail">运费：</el-tag>
                </el-col>
                <el-col :span="19">
                  <span>{{productDetail.freight == undefined ? '' : '￥ ' + (productDetail.freight/100).toFixed(2)}}</span>
                </el-col>

              </el-row>
              <el-row class="myDetailRow">
                <el-col :span="5">
                  <el-tag class="myTagDetail">产品轮播图展示图片：</el-tag>
                </el-col>
                <el-col :span="19">
                  <el-image
                    v-for="(img,index) in productDetail.slide_show_imgs"
                    :key="index"
                    class="myElImg"
                    :src="img"
                  >
                  </el-image>
                </el-col>

              </el-row>
              <el-row class="myDetailRow">

                <el-col :span="5">
                  <el-tag class="myTagDetail">产品详情介绍图片：</el-tag>
                </el-col>
                <el-col :span="19">
                  <el-image
                    v-for="(img,index) in productDetail.graphic_details_imgs"
                    :key="index"
                    class="myElImg"

                    :src="img"
                  >
                  </el-image>
                </el-col>

              </el-row>
            </template>

          </el-table-column>
          <el-table-column
            prop="id"
            label="ID"
            width="60">
          </el-table-column>
          <el-table-column
            prop="name"
            label="产品"
          >
          </el-table-column>
          <el-table-column
            prop="specifications"
            label="规格">
          </el-table-column>
          <el-table-column
            prop="current_price2"
            label="目前价格">
          </el-table-column>
          <el-table-column
            prop="history_price2"
            label="历史价格">
          </el-table-column>

          <el-table-column
            prop="special_information"
            label="产品标签">
          </el-table-column>
          <el-table-column
            prop="img_url"
            label="产品简介图片">

            <template slot-scope="scope">
              <el-image
                style="width: 100px; height: 100px"
                :src="$nginxPrefix + scope.row.img_url"
              >
              </el-image>
            </template>


          </el-table-column>

          <el-table-column
            label="操作">
            <template slot-scope="scope">
              <el-row class="myRow">
                <el-tooltip class="item" effect="dark" content="修改产品的信息" placement="top-start">
                  <el-button
                    round
                    type="success"
                    size="mini"
                    icon="el-icon-edit"
                    @click="openEditDialog(scope.row)">修改
                  </el-button>
                </el-tooltip>
              </el-row>

              <el-row>
                <el-tooltip class="item" effect="dark" content="删除产品，谨慎操作" placement="top-start">
                  <el-button
                    round
                    size="mini"
                    type="danger"
                    icon="el-icon-delete"
                    @click="handleDelete(scope.row)">删除
                  </el-button>
                </el-tooltip>
              </el-row>

            </template>
          </el-table-column>

        </el-table>
        <el-pagination class="myTablePagination"
                       background
                       @size-change="handleSizeChange"
                       @current-change="handleCurrentChange"
                       :current-page="queryParam.pageNum"
                       :page-sizes="[2, 5, 8, 10]"
                       :page-size="queryParam.pageSize"
                       layout="total, sizes, prev, pager, next, jumper"
                       :total="totalDataNum">
        </el-pagination>
      </el-row>
    </el-card>


    <el-dialog
      title="编辑产品"
      :visible.sync="editDialogvisible"
      width="50%"
    >


      <el-form :model="editRowData" :rules="rules" ref="editForm" label-width="160px" class="demo-ruleForm">
        <el-form-item label="产品ID：" prop="id">
          <el-input v-model="editRowData.id" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="产品名：" prop="name">
          <el-input v-model="editRowData.name"></el-input>
        </el-form-item>
        <el-form-item label="规格：" prop="specifications">
          <el-input v-model="editRowData.specifications"></el-input>
        </el-form-item>
        <el-form-item label="目前价格(分为单位)：" prop="current_price2">
          <el-input v-model="editRowData.current_price"></el-input>
        </el-form-item>
        <el-form-item label="历史价格(分为单位)：" prop="history_price2">
          <el-input v-model="editRowData.history_price"></el-input>
        </el-form-item>
        <el-form-item label="产品标签：">
          <el-input v-model="editRowData.special_information"></el-input>
        </el-form-item>
        <el-form-item label="产品简介图片：" prop="img_url">

          <el-image
            style="width: 100px; height: 100px;float:left;"
            :src="$nginxPrefix + editRowData.img_url"
            :preview-src-list="[$nginxPrefix + editRowData.img_url]">
          </el-image>

          <el-upload ref="uploadImg"
                     style="margin-top: 27px;"
                     :auto-upload="false"
                     class="upload-demo"
                     action="http://localhost:8080/apiGateway/productService/products/img"
                     :headers="MyHeaders"
                     :limit="1"
                     :http-request="customUpload"
                     :file-list="fileList"
                     list-type="picture">
            <el-button size="small" type="primary">选择需要替代的图片</el-button>
            <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
          </el-upload>

        </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogvisible = false">取 消</el-button>
        <el-button type="primary" @click="editFinished">编辑完成</el-button>
      </span>
    </el-dialog>


    <el-dialog
      title="编辑产品详情信息"
      :visible.sync="editProductDetaildialogVisible"
      width="50%"
    >
      <el-form :model="editproductDetail" :rules="editproductDetailRule" ref="editDetailRef" label-width="180px"
               class="demo-ruleForm">
        <el-form-item label="ID：" prop="id">
          <el-input v-model="editproductDetail.id" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="产品描述：" prop="description">
          <el-input v-model="editproductDetail.description"></el-input>
        </el-form-item>
        <el-form-item label="运费（以分为单位）：" prop="freight">
          <el-input v-model="editproductDetail.freight"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editProductDetaildialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="editProductDetailFinished">编辑完成</el-button>
      </span>
    </el-dialog>


  </div>
</template>

<script>

  import $ from 'jquery'

  export default {
    name: 'Products',
    data() {
      return {
        searchProductName: '',
        MyHeaders: {
          token: window.sessionStorage.getItem('token'),
        },
        fileList: [],
        imageUrl: '',
        selectProductType: '',
        productTypes: [],
        products: [],
        queryParam: {
          pageNum: 1,
          pageSize: 2,
        },
        totalDataNum: 0,
        editRowData: {},
        editDialogvisible: false,
        rules: {
          id: [
            {
              required: true,
              message: '必须有产品ID',
              trigger: 'blur'
            },
          ],
          name: [
            {
              required: true,
              message: '必须有产品名',
              trigger: 'change'
            }
          ],
          specifications: [
            {
              required: true,
              message: '必须有产品规格',
              trigger: 'change'
            }
          ],
          current_price2: [
            {
              required: true,
              message: '必须有当前价格',
              trigger: 'change'
            }
          ],
          history_price2: [
            {
              required: true,
              message: '必须有历史价格',
              trigger: 'change'
            }
          ]

        },

        expandKeys: [],
        productDetail: {},
        editproductDetail: {},
        editProductDetaildialogVisible: false,

        editproductDetailRule: {
          id: [{required: true, message: '必须有产品ID', trigger: 'blur'}],
          description: [{required: true, message: '必须有产品描述', trigger: 'blur'}],
          freight: [{required: true, message: '必须有产品运费', trigger: 'blur'}]
        }

      }
    },

    methods:
      {

        //按搜索栏进行查询产品
        async search() {
          var productName = this.searchProductName
          var type_id = null;

          //搜索栏什么都不填，则全局查询
          if (this.selectProductType == '' || this.selectProductType == null) {
            type_id = -1
          } else {
            type_id = this.selectProductType
          }

          //组建请求参数
          var queryParam = {
            type_id: type_id,
            productName: productName,
            pageNum: this.queryParam.pageNum,
            pageSize: this.queryParam.pageSize
          }

          var {data: result} = await this.$http.get("http://localhost:8080/apiGateway/productService/products/", {params: queryParam})

          if (result.size > 0) {
            //格式化产品价格
            this.totalDataNum = result.total
            this.products = result.list
            this.products.forEach((item, index) => {
              item.current_price2 = '￥ ' + (item.current_price / 100).toFixed(2)
              item.history_price2 = '￥ ' + (item.history_price / 100).toFixed(2)
            })
            this.$message({
              message: '查询产品成功',
              type: 'success'
            });
          } else {
            this.products = []
            this.totalDataNum = 0
            this.$message({
              message: '无查询的产品信息',
              type: 'warning'
            });
          }


        },

        //提交编辑产品详情信息
        async editProductDetailFinished() {

          var productId = this.editproductDetail.id;

          //组件请求参数
          var param = {
            id: this.editproductDetail.id,
            description: this.editproductDetail.description,
            freight: this.editproductDetail.freight
          }

          var {data: result} = await this.$http.put("http://localhost:8080/apiGateway/productService/products/" + productId, param)


          if (result.effective) {
            this.editProductDetaildialogVisible = false;
            this.editproductDetail = {};

            this.$message({
              message: result.message,
              type: 'success'
            });

            this.getProductDetail(productId);

          } else {

            this.$message({
              message: result.message,
              type: 'success'
            });

          }

        },

        //打开产品详情编辑窗口
        openEditproductDetail() {

          var jsonStr = JSON.stringify(this.productDetail);

          this.editproductDetail = JSON.parse(jsonStr);

          this.editProductDetaildialogVisible = true;
        },


        //标记行
        getRowKey(row) {
          return row.id;
        },

        //获取产品详情信息 -根据产品ID号
        async getProductDetail(id) {

          var {data: result} = await this.$http.get("http://localhost:8080/apiGateway/productService/products/" + id)

          if (result.effective) {
            this.$message({
              message: '获取成功产品' + id + '详情信息',
              type: 'success'
            });

            result.data.slide_show_imgs.forEach((item, index) => {
              result.data.slide_show_imgs[index] = this.$nginxPrefix + item;
            });
            result.data.graphic_details_imgs.forEach((item, index) => {
              result.data.graphic_details_imgs[index] = this.$nginxPrefix + item;
            });

            this.productDetail = result.data;
            console.log(this.productDetail);
          } else {

            this.productDetail = {}
            this.$message({
              message: '获取失败产品' + id + '详情信息',
              type: 'danger'
            });
          }


        },


        //展开行 - 只能展开一行 - 获取产品详情信息
        expandChange(row, expandRows) {

          if (expandRows.length) {
            this.expandKeys = []
            if (row) {

              this.getProductDetail(row.id);

              setTimeout(() => {
                this.expandKeys.push(row.id);
              }, 500)

            }
          } else {
            this.expandKeys = []
          }

        },

        //更新产品信息 -- TODO待做
        async editFinished() {

          this.$refs.uploadImg.submit()

          var {data: result} = await this.$http.put('http://localhost:8080/apiGateway/productService/products', this.editRowData)

          if (result.effective) {
            this.$message({
              message: result.message,
              type: 'success'
            })
            this.editDialogvisible = false
            this.editRowData = {}
            this.getProduct()
          } else {
            this.$message({
              message: result.message,
              type: 'danger'
            })
          }

        },

        //自定义产品图片上传的参数
        async customUpload(file) {

          console.log(file)

          var imgURI = this.editRowData.img_url

          var index = imgURI.lastIndexOf('\\')

          console.log(imgURI.slice(0, index + 1))

          var param = new FormData()
          param.append('file', file.file)
          param.append('imgURI', imgURI.slice(0, index + 1))
          param.append('productId', this.editRowData.id)
          var {data: result} = await this.$http.post('http://localhost:8080/apiGateway/productService/products/img', param)

          if (result.effective) {
            this.$message({
              message: result.message,
              type: 'success'
            })

          } else {
            this.$message({
              message: result.message,
              type: 'danger'
            })
          }

          this.fileList = []

        },

        //打开编辑产品窗口 - 将数据渲染到编辑窗口
        openEditDialog(row) {

          var jsonStr = JSON.stringify(row)
          this.editRowData = JSON.parse(jsonStr)
          this.editDialogvisible = true

        },

        //删除产品 - 根据产品ID
        async handleDelete(row) {

          var product_id = row.id

          var {data: result} = await this.$http.delete('http://localhost:8080/apiGateway/productService/products/' + row.id)

          if (result.effective) {

            this.getProduct()
            this.$message({
              message: result.message,
              type: 'success'
            })
          } else {
            this.$message({
              message: result.message,
              type: 'danger'
            })
          }

        },

        //显示条数改变pageSize
        handleSizeChange(pageSize) {


          this.queryParam.pageSize = pageSize

          if (this.searchProductName != null && this.searchProductName != '') {
            this.search();
            return;
          }

          this.getProduct()
        },

        //页码改变pageNum
        handleCurrentChange(pageNum) {


          this.queryParam.pageNum = pageNum

          if (this.searchProductName != null && this.searchProductName != '') {
            this.search();
            return;
          }

          this.getProduct()
        },

        //产品类别改变
        changType() {
          this.getProduct()
        },

        //获取产品类别 - 渲染到select标签中
        async getProductTyps() {

          var {data: result} = await this.$http.get('http://localhost:8080/apiGateway/productService/products/onlyTypes')

          if (result.effective) {
            this.productTypes = result.data.list
            console.log('=========产品类别')
            console.log(this.productTypes)
            this.$message({
              message: '获取产品类别成功',
              type: 'success'
            })

          } else {

            this.$message({
              message: '获取产品类别失败',
              type: 'danger'
            })
          }
        },

        //获取产品普通信息
        async getProduct() {

          var type_id = null

          //不按产品类别查询
          if (this.selectProductType == '' || this.selectProductType == null) {
            type_id = -1

            //按产品类别查询 - 传产品类别ID号
          } else {
            type_id = this.selectProductType
          }

          var {data: result} = await this.$http.get('http://localhost:8080/apiGateway/productService/products/types2/' + type_id, {params: this.queryParam})
          if (result.effective) {

            this.totalDataNum = result.data.total

            this.products = result.data.list

            this.products.forEach((item, index) => {
              item.current_price2 = '￥ ' + (item.current_price / 100).toFixed(2)
              item.history_price2 = '￥ ' + (item.history_price / 100).toFixed(2)
            })


            this.$message({
              message: '获取类别' + this.selectProductType + '下的产品成功',
              type: 'success'
            })
          } else {

            this.products = []
            this.totalDataNum = 0

            this.$message({
              message: '获取类别' + selectProductType + '下的产品失败',
              type: 'danger'
            })
          }

        },

      }
    ,
    //进入产品列表页面 - 即请求获取产品类别信息以及产品信息
    created() {
      this.getProductTyps()

      this.getProduct()

    }

  }
</script>

<style scoped>

  .myEditDetailBtn {
    position: absolute;
    right: 60px;
  }

  .myTagDetail {
    float: right;
    margin-right: 60px;

  }

  .myElImg {
    width: 100px;
    height: 100px;
    margin-right: 20px;
    background: #000;
    border: 1px solid #fff;
    border-width: 5px 5px 5px 5px;
    box-shadow: 1px 1px 5px #333;
  }

  .myDetailRow {
    margin-bottom: 20px !important;
    line-height: 32px;
  }

  .myCardHead {
    float: left !important;
  }

  .myCardTable {
    margin-top: 20px;
  }

  .myTablePagination {
    margin-top: 20px;
    float: left;
  }

  .myRow {
    margin-bottom: 20px !important;
  }
</style>
