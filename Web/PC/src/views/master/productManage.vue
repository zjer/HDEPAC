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
        <el-button-group>
          <el-tooltip class="item" effect="dark" :content="$t('message.resetPWD')" placement="top">
            <el-button class="addBtn" icon="iconfont icon-zhongzhi" @click="handleResetPWD()"></el-button>
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
            <el-input v-model="ruleForm.producttype"></el-input>
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
        }
      }
    },
    created() {
      this.getProducts();
      this.watchHeight();
      this.resetLabelWidth();
    },
    computed:{
      curLang() {
        return this.$store.state.language
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
      delUsers(val) {
        let param = {
          idLists: val
        };
        this.fetch.ajax('/user/delUsers', param, 'POST')
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
            this.delUsers(str);
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
      resetPWD(val) {
        let param = {
          idLists: val
        };
        this.fetch.ajax('/user/resetPWD', param, 'POST')
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
      handleResetPWD() {
        if (this.multipleSelection.length > 0) {
          let str = '';
          let arr = [];
          this.multipleSelection.forEach(ele => {
            arr.push(ele.productid);
          });
          str = arr.join(',');
          console.log(str);
          this.resetPWD(str);
        } else {
          this.$message({
            type: 'success',
            message: this.$t('message.choseResetPWDData')
          });
        }
      },
      updateState(id, state) {
        let param = {
          productid: id,
          state: state === true ? 1 : 0
        };
        this.fetch.ajax('/user/updateState', param, 'POST')
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
          createtime: row.createtime,
          age: row.age,
          birth: row.birth,
          place: {
            province: row.place ? row.place.split('-')[0] : '',
            city: row.place ? row.place.split('-')[1] : '',
            area: row.place ? row.place.split('-')[2] : '',
          },
          gender: row.gender === this.$t('message.man') ? 1 : 0,
          admin: row.admin === this.$t('message.yes') ? 1 : 0,
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
          createtime: this.ruleForm.createtime,
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
      updateUser() {
        let param = {
          productid: this.ruleForm.productid,
          productcode: this.ruleForm.productcode,
          productname: this.ruleForm.productname,
          age: this.ruleForm.age,
          birth: this.ruleForm.birth,
          place: this.ruleForm.place.province + '-' + this.ruleForm.place.city + '-' + this.ruleForm.place.area,
          gender: this.ruleForm.gender,
          admin: this.ruleForm.admin,
          state: this.ruleForm.state,
        };
        this.fetch.ajax('/user/updateUser', param, 'POST')
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
      delUser(val) {
        let param = {
          productid: val
        };
        this.fetch.ajax('/user/delUser', param, 'POST')
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
          this.delUser(row.productid);
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
              this.updateUser();
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
