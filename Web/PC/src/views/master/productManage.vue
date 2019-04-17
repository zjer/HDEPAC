<template>
  <div>
    <head-top></head-top>
    <div class="table_container">
      <div class="tableBtns">
        <el-button-group>
          <el-tooltip class="item" effect="dark" :content="$t('message.add')" placement="top">
            <el-button class="addBtn" type="primary" icon="iconfont icon-xinzeng-kong" @click="handleAdd()"></el-button>
          </el-tooltip>
          <el-tooltip class="item" effect="dark" :content="$t('message.del')" placement="top">
            <el-button class="addBtn" type="primary" icon="iconfont icon-shanchu-kong" @click="handleDelete()"></el-button>
          </el-tooltip>
        </el-button-group>
      </div>
      <el-table
        v-loading='isloading'
        ref="multipleTable"
        :data="tableData"
        :row-key="row => row.index"
        style="width: 100%"
        class="tableStyle"
        :max-height="tableHeight"
        :height="tableHeight"
        @selection-change="handleSelectionChange"
        :header-cell-style="{
          'background-color': '#f8f8f9',
        }">
        <el-table-column
          type="selection"
          width="55">
        </el-table-column>
        <el-table-column :label="$t('message.productcode')" prop="productcode">
        </el-table-column>
        <el-table-column :label="$t('message.productname')" prop="productname">
        </el-table-column>
        <el-table-column :label="$t('message.producttype')" prop="producttype">
        </el-table-column>
        <el-table-column :label="$t('message.createtime')" prop="createtime" >
        </el-table-column>
        <el-table-column :label="$t('message.state')" prop="state">
          <template slot-scope="scope">
            <el-switch
              :width="30"
              v-model="scope.row.state"
              active-color="#13ce66"
              inactive-color="#ff4949"
              @change="setState(scope.row)">
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column :label="$t('message.operate')" align="center">
          <template slot-scope="scope">
            <el-tooltip class="item" effect="dark" :content="$t('message.edit')" placement="top">
              <el-button icon="iconfont icon-bianji-kong" class="hoverBtn" @click="handleEdit(scope.$index, scope.row)"></el-button>
            </el-tooltip>
            <el-tooltip class="item" effect="dark" :content="$t('message.del')" placement="top">
              <el-button icon="iconfont icon-shanchu-kong" class="hoverBtn" @click="handleDel(scope.$index, scope.row)"></el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
      <div class="Pagination">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="PageIndex"
          :page-sizes="[20, 30, 50]"
          :page-size="PageSize"
          background
          layout="total, sizes, prev, pager, next, jumper"
          :total="count">
        </el-pagination>
      </div>
      <el-dialog :title="editTitle" :visible.sync="dialogVisible" width="670px" :close-on-click-modal='false'>
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" :label-width="labelWidth">
          <el-form-item :label="$t('message.productcode')" prop="productcode">
            <el-input v-model="ruleForm.productcode"></el-input>
          </el-form-item>
          <el-form-item :label="$t('message.productname')" prop="productname">
            <el-input v-model="ruleForm.productname"></el-input>
          </el-form-item>
          <el-form-item :label="$t('message.producttype')" prop="producttype">
            <el-select v-model="ruleForm.producttype" placeholder="请选择">
              <el-option
                v-for="(item, index) in options"
                :key="index"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item :label="$t('message.createtime')" prop="createtime">
            <el-date-picker
              v-model="ruleForm.createtime"
              type="datetime"
              disabled
              :placeholder="$t('message.choseDateTime')">
            </el-date-picker>
          </el-form-item>
          <el-form-item :label="$t('message.state')" prop="state">
            <el-radio-group v-model="ruleForm.state">
              <el-radio :label="1">{{ $t('message.activation') }}</el-radio>
              <el-radio :label="0">{{ $t('message.disable') }}</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-tooltip class="item" effect="dark" :content="$t('message.cancel')" placement="top">
            <el-button @click="dialogVisible = false" icon="iconfont icon-guanbi-wukuang"></el-button>
          </el-tooltip>
          <el-tooltip class="item" effect="dark" :content="$t('message.save')" placement="top">
            <el-button type="primary" @click="submit('ruleForm')" icon="iconfont icon-gou-wukuang"></el-button>
          </el-tooltip>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
  import HeadTop from '../../components/headTop';
  import { getLocal, formatDate } from "../../config/mUtils";

  export default {
    name: "productManage",
    components: { HeadTop },
    data () {
      return {
        isloading: true,
        tableHeight: 0 + 'px',
        tableData: [],
        PageIndex: 1,
        PageSize: 20,
        count: 0,
        multipleSelection: [],
        editTitle: '',
        dialogVisible: false,
        labelWidth: 0 + 'px',
        isnew: 0,
        ruleForm: {
          productid: 0,
          productcode: '',
          productname: '',
          producttype: '',
          createtime: '',
          state: 1
        },
        rules: {
          productcode: [{ required: true, message:this.$t('message.inputproductcode'), trigger: 'blur' }],
          productname: [{ required: true, message:this.$t('message.inputproductname'), trigger: 'blur' }],
          producttype: [{ required: true, message:this.$t('message.inputAge'), trigger: 'blur' }],
          state: [{ required: true, message:this.$t('message.choseState'), trigger: 'blur' }],
        },
        options: [
          {
            label: '灯饰',
            value: 0
          },
          {
            label: '橱柜',
            value: 1
          },
        ]
      }
    },
    created() {
      this.getProducts();
      this.watchHeight();
      this.resetLabelWidth();
    },
    computed:{
      curLang() {
        return this.$store.state.language;
      }
    },
    watch:{
      curLang: function (val) {
        console.log(val);
        this.resetLabelWidth();
      }
    },
    mounted() {
      setTimeout(() => {
        let winHeight = document.documentElement.clientHeight - 252;
        this.tableHeight = winHeight;
      }, 1);
    },
    methods: {
      watchHeight() {
        setTimeout(() => {
          const that = this;
          window.onresize = function temp() {
            let winHeight = document.documentElement.clientHeight - 252;
            that.tableHeight = winHeight;
          };
        }, 1);
      },
      resetLabelWidth() {
        if (getLocal('lang') === 'en_US') {
          this.labelWidth = 100 + 'px';
        } else {
          this.labelWidth = 70 + 'px';
        }
      },
      getProducts() {
        let param = {
          pageIndex: this.PageIndex,
          pageSize: this.PageSize
        };
        this.tableData = [];
        this.fetch.ajax('/product/getProducts', param, 'POST')
          .then(res => {
            if (res.data.state) {
              res.data.rows.list.forEach(item => {
                item.createtime = item.createtime.substring(0, item.createtime.indexOf(".")).replace("T", " ");
                item.state = item.state === 1 ? true : false;
                this.tableData.push(item);
              });
              this.count = res.data.rows.total;
              this.isloading = false;
            }
          })
      },
      handleSelectionChange(val) {
        this.multipleSelection = val;
      },
      handleSizeChange(val) {
        this.PageIndex = 1;
        this.PageSize = val;
        this.getProducts();
      },
      handleCurrentChange(val) {
        this.PageIndex = val;
        this.getProducts();
      },
      handleAdd() {
        this.editTitle = this.$t('message.add');
        this.dialogVisible = true;
        this.ruleForm = {
          productid: 0,
          productcode: '',
          productname: '',
          createtime: formatDate(new Date()),
          producttype: '',
          state: 1
        };
        this.isnew = 0;
      },
      delProducts(val) {
        let param = {
          idLists: val
        };
        this.fetch.ajax('/product/delProducts', param, 'POST')
          .then(res => {
            if (res.data.state) {
              console.log(res);
              this.$message({
                type: "success",
                message: this.$t('message.deleteSuccess')
              });
              this.getProducts();
            }
          })
      },
      handleDelete() {
        if (this.multipleSelection.length > 0) {
          let str = '';
          let arr = [];
          this.multipleSelection.forEach(ele => {
            arr.push(ele.productid);
          });
          str = arr.join(',');
          console.log(str);
          this.$confirm(this.$t('message.deleteIt'), 'Warning', {
            confirmButtonText: ' ',
            cancelButtonText: ' ',
            confirmButtonClass: 'icon iconfont icon-gou-wukuang',
            cancelButtonClass: 'icon iconfont icon-guanbi-wukuang',
            type: 'warning'
          }).then(() => {
            this.delProducts(str);
          }).catch(() => {
            this.$message({
              type: 'info',
              message: this.$t('message.deleteCanceled')
            });
          });
        } else {
          this.$message({
            message: this.$t('message.choseDelData'),
            type: 'warning'
          });
        }
      },
      updateState(id, state) {
        let param = {
          productid: id,
          state: state === true ? 1 : 0
        };
        this.fetch.ajax('/product/updateState', param, 'POST')
          .then(res => {
            if (res.data.state) {
              console.log(res);
              this.$message({
                type: "success",
                message: this.$t('message.updateSuccess')
              });
            }
          })
      },
      setState(val) {
        this.updateState(val.productid, val.state);
      },
      handleEdit(index, row) {
        this.editTitle = this.$t('message.edit');
        this.dialogVisible = true;
        this.ruleForm = {
          productid: row.productid,
          productcode: row.productcode,
          productname: row.productname,
          producttype: row.producttype,
          state: row.state === true ? 1 : 0,
        };
        this.isnew = 1;
      },
      addProduct() {
        let param = {
          productid: 0,
          productcode: this.ruleForm.productcode,
          productname: this.ruleForm.productname,
          producttype: this.ruleForm.producttype,
          state: this.ruleForm.state,
        };
        this.fetch.ajax('/product/addProduct', param, 'POST')
          .then(res => {
            if (res.data.state) {
              console.log(res);
              this.dialogVisible = false;
              this.$message({
                type: "success",
                message: this.$t('message.saveSuccess')
              });
              this.getProducts();
            }
          })
      },
      updateProduct() {
        let param = {
          productid: this.ruleForm.productid,
          productcode: this.ruleForm.productcode,
          productname: this.ruleForm.productname,
          producttype: this.ruleForm.producttype,
          state: this.ruleForm.state
        };
        this.fetch.ajax('/product/updateProduct', param, 'POST')
          .then(res => {
            if (res.data.state) {
              console.log(res);
              this.dialogVisible = false;
              this.$message({
                type: "success",
                message: this.$t('message.saveSuccess')
              });
              this.getProducts();
            }
          })
      },
      delProduct(val) {
        let param = {
          productid: val
        };
        this.fetch.ajax('/product/delProduct', param, 'POST')
          .then(res => {
            if (res.data.state) {
              console.log(res);
              this.$message({
                type: "success",
                message: this.$t('message.deleteSuccess')
              });
              this.getProducts();
            }
          })
      },
      handleDel(index, row) {
        this.$confirm(this.$t('message.deleteIt'), 'Warning', {
          confirmButtonText: ' ',
          cancelButtonText: ' ',
          confirmButtonClass: 'icon iconfont icon-gou-wukuang',
          cancelButtonClass: 'icon iconfont icon-guanbi-wukuang',
          type: 'warning'
        }).then(() => {
          this.delProduct(row.productid);
        }).catch(() => {
          this.$message({
            type: 'info',
            message: this.$t('message.deleteCanceled')
          });
        });
      },
      getAge(val) {
        let newDate = new Date();
        let curYear = newDate.getFullYear();
        let choseYear = val.substring(0, 4);
        console.log(choseYear);
        this.ruleForm.age = curYear - choseYear;
      },
      submit(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            if (this.isnew === 0) {
              this.addProduct();
            } else {
              this.updateProduct();
            }
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      }
    }
  }
</script>

<style scoped>

</style>
