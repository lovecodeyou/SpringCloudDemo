<template>
	<div>
		<!-- 面包屑导航区 -->
		<el-breadcrumb separator="/">
			<el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
			<el-breadcrumb-item>订单管理</el-breadcrumb-item>
			<el-breadcrumb-item>订单列表</el-breadcrumb-item>
		</el-breadcrumb>

		<el-card class="box-card">
			<el-row class="myRowTable">
				<el-col :span="5">
					<div style="margin-top: 15px;">
						<el-input
							placeholder="请输入完整的订单号"
							v-model="queryParam.order_id"
							class="input-with-select"
						>
							<el-button
								slot="append"
								icon="el-icon-search"
								@click="getOrder"
							></el-button>
						</el-input>
					</div>
				</el-col>

				<el-col :span="19">
					<div class="block">
						<span class="demonstration">下单日期：</span>
						<el-date-picker
							@change="DateChanged"
							v-model="dateValue"
							type="daterange"
							align="right"
							unlink-panels
							range-separator="至"
							start-placeholder="开始日期"
							end-placeholder="结束日期"
							:picker-options="pickerOptions"
						>
						</el-date-picker>
					</div>
				</el-col>
			</el-row>
			<el-row>
				<el-table
					id="myTable"
					:data="tableData"
					border
					:stripe="true"
					style="width: 100%"
					:row-class-name="tableRowClassName"
				>
					<el-table-column type="expand"></el-table-column>
					<el-table-column prop="id" label="ID" width="60px">
					</el-table-column>
					<el-table-column prop="order_id" label="订单号" width="160">
					</el-table-column>
					<el-table-column
						prop="total_money2"
						label="订单总价"
						width="140"
					>
					</el-table-column>
					<el-table-column
						prop="pay_status"
						label="是否付款"
						width="160"
					>
						<template slot-scope="scope">
							<el-tag
								class="myTag"
								@click="changPayStatus(scope.row, 0)"
								:type="
									scope.row.pay_status == 1
										? ''
										: scope.row.pay_status == 0
										? 'warning'
										: 'danger'
								"
								>{{
									scope.row.pay_status == 1
										? '已付款'
										: scope.row.pay_status == 0
										? '待付款'
										: '已退款'
								}}</el-tag
							>
						</template>
					</el-table-column>
					<el-table-column prop="order_status" label="是否发货" >
						<template slot-scope="scope">
							<el-tag
								@click="changPayStatus(scope.row, 1)"
								class="myTag"
								:type="
									scope.row.order_status >= 2 ? '' : 'danger'
								"
								>{{
									scope.row.order_status >= 2 ? '是' : '否'
								}}</el-tag
							>
							<el-select v-model="order_status" placeholder="修改状态" @change="changPayStatus(scope.row, 1)">
								<el-option
									v-for="item in options"
									:key="item.value"
									:label="item.label"
									:value="item.value"
								>
								</el-option>
							</el-select>
						</template>
					</el-table-column>
					<el-table-column
						prop="create_time2"
						label="下单时间"
						width="137"
					>
					</el-table-column>
					<el-table-column label="操作">
						<template slot-scope="scope">
							<el-tooltip
								class="item"
								effect="dark"
								content="修改收货地址"
								placement="bottom"
							>
								<el-button
									round
									type="primary"
									size="mini"
									icon="el-icon-edit"
									@click="openAddressDialog(scope.row)"
									>编辑</el-button
								>
							</el-tooltip>

							<el-tooltip
								class="item"
								effect="dark"
								content="删除订单，谨慎操作"
								placement="bottom"
							>
								<el-button
									round
									size="mini"
									type="danger"
									icon="el-icon-delete-solid"
									@click="handleDelete(scope.row)"
									>删除</el-button
								>
							</el-tooltip>
						</template>
					</el-table-column>
				</el-table>
			</el-row>

			<el-row>
				<el-pagination
					class="myPagination"
					background
					@size-change="handleSizeChange"
					@current-change="handleCurrentChange"
					:current-page="currentPage4"
					:page-sizes="[2, 5, 8, 10]"
					:page-size="2"
					layout="total, sizes, prev, pager, next, jumper"
					:total="rowTotal"
				>
				</el-pagination>
			</el-row>
		</el-card>

		<el-dialog
			title="修改地址"
			:visible.sync="addressDialogVisible"
			width="50%"
		>
			<el-form
				:model="addressForm"
				:rules="addressRules"
				ref="addressFormRef"
				label-width="100px"
				class="demo-ruleForm"
			>
				<el-form-item label="省市区县：" prop="currentAreaCode">
					<el-cascader
						placeholder="请选择收货地址省市区( 不填默认原省市区 )"
						v-model="addressForm.currentAreaCode"
						:options="areaDataSource"
						@active-item-change="updateAreaCodeChange"
						:props="props"
					></el-cascader>
				</el-form-item>
				<el-form-item label="详细地址：" prop="addressDetail">
					<el-input v-model="addressForm.addressDetail"></el-input>
				</el-form-item>
			</el-form>

			<span slot="footer" class="dialog-footer">
				<el-button @click="addressDialogVisible = false"
					>取 消</el-button
				>
				<el-button type="primary" @click="editOrderAddress"
					>确 定</el-button
				>
			</span>
		</el-dialog>
	</div>
</template>

<script>
export default {
	name: 'OrderList',
	data() {
		return {
			//日期对象
			pickerOptions: {
				shortcuts: [
					{
						text: '最近一周',
						onClick(picker) {
							const end = new Date();
							const start = new Date();
							start.setTime(
								start.getTime() - 3600 * 1000 * 24 * 7
							);
							picker.$emit('pick', [start, end]);
						}
					},
					{
						text: '最近一个月',
						onClick(picker) {
							const end = new Date();
							const start = new Date();
							start.setTime(
								start.getTime() - 3600 * 1000 * 24 * 30
							);
							picker.$emit('pick', [start, end]);
						}
					},
					{
						text: '最近三个月',
						onClick(picker) {
							const end = new Date();
							const start = new Date();
							start.setTime(
								start.getTime() - 3600 * 1000 * 24 * 90
							);
							picker.$emit('pick', [start, end]);
						}
					}
				]
			},
			searchInfo: '',
			tableData: [],
			queryParam: {
				pageNum: 1,
				pageSize: 2,
				order_id: ''
			},
			rowTotal: 0,
			currentPage4: 1,
			dateValue: [],
			formatDate: [],
			addressDialogVisible: false,
			props: {
				label: 'name',
				value: 'id',
				children: 'datas'
			},
			// currentAreaCode: [],
			areaDataSource: [],
			addressForm: {
				currentAreaCode: [],
				addressDetail: ''
			},
			addressRules: {
				addressDetail: [
					{
						required: true,
						message: '必须输入详细地址',
						trigger: 'blur'
					}
				],
				currentAreaCode: [
					{
						required: false,
						message: '必须选择省市区',
						trigger: 'blur'
					}
				]
			},
			selectedRow: {},
			options: [
				{
					value: '-1',
					label: '取消订单'
				},
				{
					value: '0',
					label: '未确认'
				},
				{
					value: '1',
					label: '待发货'
				},
				{
					value: '2',
					label: '已发货'
				},
				{
					value: '3',
					label: '待收货'
				},
				{
					value: '4',
					label: '待评价'
				},
				{
					value: '5',
					label: '完成订单'
				}
			],
			order_status: ''
		};
	},
	methods: {


		//更改订单的支付状态,发货状态
		async changPayStatus(row, flag) {
			var param = {};
			//支付状态
			if (flag == 0) {
				if (row.pay_status > 1) {
					row.pay_status = -1;
				}
				param.pay_status = row.pay_status + 1;

				if (param.pay_status > 1) {
					param.pay_status = -1;
				}

				var { data: result } = await this.$http.put(
					'http://localhost:8080/apiGateway/orderService/books/' +
						row.id,
					param
				);

				if (result.effective) {
					this.$message({
						message: '订单支付状态修改成功',
						type: 'success'
					});
					row.pay_status = param.pay_status;
				} else {
					this.$message.error('订单支付状态修改失败');
				}
			}

			if (flag == 1) {

				param.order_status = this.order_status;

				var { data: result } = await this.$http.put(
					'http://localhost:8080/apiGateway/orderService/books/' +
						row.id,
					param
				);

					if (result.effective) {
					this.$message({
						message: '订单状态修改成功',
						type: 'success'
					});
					row.order_status = param.order_status;
					this.order_status = ''
				} else {
					this.$message.error('订单状态修改失败');
					this.order_status = ''
				}

			}
		},

		// 更改订单的收货地址
		async editOrderAddress() {
			console.log(this.addressForm);

			this.$refs.addressFormRef.validate(async (valid) => {
				if (valid) {
					var param = {};
					var length = this.addressForm.currentAreaCode;
					var currentAreaCode = this.addressForm.currentAreaCode;
					if (length == 1) {
						param.provinceId = currentAreaCode[0];
					} else if (length == 2) {
						param.provinceId = currentAreaCode[0];
						param.cityId = currentAreaCode[1];
					} else {
						param.provinceId = currentAreaCode[0];
						param.cityId = currentAreaCode[1];
						param.areaId = currentAreaCode[2];
					}
					param.detail_address = this.addressForm.addressDetail;

					var address_id = this.selectedRow.address_id;

					var { data: result } = await this.$http.put(
						'http://localhost:8080/apiGateway/memberService/members/address/' +
							address_id,
						param
					);

					if (result.effective) {
						this.$message({
							message: result.message,
							type: 'success'
						});

						this.addressForm = {};
						this.addressDialogVisible = false;
						this.selectedRow = {};
					} else {
						this.$message.error(result.message);
					}
				} else {
					this.$message.error('请完整填写所需信息');
					return false;
				}
			});
		},

		//打开地址编辑窗口
		openAddressDialog(row) {
			this.selectedRow = row;
			var address_id = row.address_id;
			this.getAddressById(address_id);

			setTimeout(() => {
				this.addressDialogVisible = true;
			}, 500);
		},

		//获取收货地址
		async getAddressById(address_id) {
			var { data: result } = await this.$http.get(
				'http://localhost:8080/apiGateway/memberService/members/address/?address_id=' +
					address_id
			);

			if (result != null) {
				var strResult = JSON.stringify(result);
				var jsonObj = JSON.parse(strResult);

				this.addressForm.addressDetail = jsonObj.detail_address.toString();

				this.$message({
					message: '获取收货地址成功',
					type: 'success'
				});
			} else {
				this.$message({
					message: '获取收货地址失败',
					type: 'danger'
				});
			}
		},

		//格式化日期选择器选中的日期 - 并发送获取订单的请求 - 格式化成：年-月-日
		DateChanged() {
			console.log(this.dateValue);
			this.formatDate = [];
			this.dateValue.forEach((item, idnex) => {
				var year = item.getFullYear();
				var month = item.getMonth() + 1;
				var date = item.getDate();
				var forDate = year + '-' + month + '-' + date;
				this.formatDate.push(forDate);
			});
			console.log(this.queryParam.dates);

			//获取订单
			this.getOrder();
		},

		async handleEdit(row) {},

		//删除订单
		async handleDelete(row) {
			this.$confirm('此操作将永久删除该订单, 是否继续?', '提示', {
				confirmButtonText: '确定',
				cancelButtonText: '取消',
				type: 'warning'
			})
				.then(async () => {
					var { data: result } = await this.$http.delete(
						'http://localhost:8080/apiGateway/orderService/books/' +
							row.id
					);
					if (result.effective) {
						this.$message({
							type: 'success',
							message: '删除订单成功!'
						});
						this.getOrder();
					} else {
						this.$message({
							type: 'danger',
							message: '删除订单失败!'
						});
					}
				})
				.catch(() => {
					this.$message({
						type: 'info',
						message: '已取消删除'
					});
				});
		},

		//表格每行的颜色背景 - 不起作用待修复 -- 无用
		tableRowClassName({ row, rowIndex }) {
			console.log('状态显示');
			console.log(row);

			if (row.pay_status == 0) {
				console.log('warning');
				return 'warning-row';
			} else if (row.pay_status == -1) {
				console.log('success');
				return 'success-row';
			} else {
				return '';
			}
		},

		//当前页显示条数改变
		handleSizeChange(pageSize) {
			this.queryParam.pageSize = pageSize;
			this.getOrder();
		},

		//当前展示页页数改变
		handleCurrentChange(pageNum) {
			this.queryParam.pageNum = pageNum;
			this.getOrder();
		},

		//获取订单
		async getOrder() {
			var result;
			if (this.formatDate.length == 2) {
				result = await this.$http.get(
					'http://localhost:8080/apiGateway/orderService/books/all?dates=' +
						this.formatDate[0] +
						'&dates=' +
						this.formatDate[1],
					{ params: this.queryParam }
				);
			} else {
				result = await this.$http.get(
					'http://localhost:8080/apiGateway/orderService/books/all?date=',
					{ params: this.queryParam }
				);
			}

			result = result.data;

			if (result.effective) {
				this.$message({
					message: '成功获取订单',
					type: 'success'
				});

				result.data.list.forEach((item, index) => {
					item.create_time2 = item.create_time.substr(0, 10);
					item.total_money2 =
						'￥ ' + (item.total_money / 100).toFixed(2);
				});

				this.tableData = result.data.list;
				this.rowTotal = result.data.total;

				if (result.data.pageNum == 0) {
					this.currentPage4 = 1;
				}

				this.currentPage4 = result.data.pageNum;
			} else {
				this.$message({
					message: '获取订单失败',
					type: 'danger'
				});

				this.tableData = [];
				this.rowTotal = 0;
			}
		},

		//动态省市区地址联动
		async updateAreaCodeChange(currentAreaCode) {
			var areaDataSource = this.areaDataSource;
			console.log('=======当前选中的地址码');
			console.log(currentAreaCode);
			this.addressForm.currentAreaCode = currentAreaCode;
			console.log(this.addressForm.currentAreaCode);

			var length = this.addressForm.currentAreaCode.length;
			if (length == 3) {
				return;
			} else {
				var parentId = this.addressForm.currentAreaCode[length - 1];
				var { data: result } = await this.$http.get(
					'http://localhost:8080/apiGateway/memberService/members/areaCode/' +
						parentId
				);

				setTimeout(function() {
					if (result.effective == false) {
						return;
					} else {
						if (length == 2) {
							result.data.forEach((item, index) => {
								item.datas = null;
							});
						} else {
							result.data.forEach((item, index) => {
								item.datas = [];
							});
						}
					}
				}, 500);

				setTimeout(function() {
					areaDataSource.forEach((item, index) => {
						if (item.id == currentAreaCode[0]) {
							if (length == 1) {
								console.log('=======查询成功');
								areaDataSource[index].datas = result.data;
								return;
							} else {
								item.datas.forEach((item, index) => {
									if (item.id == currentAreaCode[1]) {
										item.datas = result.data;
									}
								});
							}
						}
					});
				}, 500);

				setTimeout(function() {
					console.log('===========当前的地址数据源');
					console.log(areaDataSource);
				}, 500);
			}
		},

		//获取省份地址
		async getProvince() {
			var { data: result } = await this.$http.get(
				'http://localhost:8080/apiGateway/memberService/members/areaCode/-1'
			);

			console.log('=======获取省份信息');
			console.log(result);

			result.data.forEach((item, index) => {
				item.datas = [];
			});

			this.areaDataSource = result.data;
		}
	},

  //进来订单列表即获取订单数据，以及中国身份数据
	created() {
		this.getOrder();

		this.getProvince();
	}
};
</script>

<style lang="less" scoped>
.el-cascader {
	width: 100%;
}

.block {
	float: right;
	margin-top: 20px;
	span {
		margin-right: 20px;
	}
}
.myRowTable {
	margin-bottom: 30px !important;
}

.myPagination {
	float: left;
	margin-top: 30px;
}

.el-table .warning-row {
	background: green !important;
	// background: #f0f9eb !important;
}
.el-table .success-row {
	background: red !important;
}

.myTag {
	cursor: pointer;
}

.el-select {
	display: inline-block;
    width: 60%;
    float: right;
}



</style>
