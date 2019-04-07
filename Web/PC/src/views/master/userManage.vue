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
            <el-button class="addBtn" type="primary" icon="iconfont icon-shanchu-kong" @click="handleDel()"></el-button>
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
        <el-table-column :label="$t('message.account')" prop="username">
        </el-table-column>
        <el-table-column :label="$t('message.password')" prop="password">
        </el-table-column>
        <el-table-column :label="$t('message.age')" prop="age">
        </el-table-column>
        <el-table-column :label="$t('message.birth')" prop="birth">
        </el-table-column>
        <el-table-column :label="$t('message.place')" prop="place">
        </el-table-column>
        <el-table-column :label="$t('message.sex')" prop="sex">
        </el-table-column>
        <el-table-column :label="$t('message.registtime')" prop="registtime" width="200px">
        </el-table-column>
        <el-table-column :label="$t('message.admin')" prop="admin">
        </el-table-column>
        <el-table-column :label="$t('message.state')" prop="state">
          <template slot-scope="scope">
            <el-switch
              :width="30"
              v-model="scope.row.state"
              active-color="#13ce66"
              inactive-color="#ff4949"
              @change="setState">
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column :label="$t('message.operate')" align="center">
          <template slot-scope="scope">
            <el-tooltip class="item" effect="dark" :content="$t('message.edit')" placement="top">
              <el-button icon="iconfont icon-bianji-kong" class="hoverBtn" @click="handleEdit(scope.$index, scope.row)"></el-button>
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
      <el-dialog :title="editTitle" :visible.sync="dialogVisible" width="600px" :close-on-click-modal='false'>
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" :label-width="labelWidth">
          <el-form-item :label="$t('message.account')" prop="username">
            <el-input v-model="ruleForm.username"></el-input>
          </el-form-item>
          <el-form-item :label="$t('message.password')" prop="password">
            <el-input v-model="ruleForm.password"></el-input>
          </el-form-item>
          <el-form-item :label="$t('message.registtime')" prop="registtime">
            <el-date-picker
              v-model="ruleForm.registtime"
              type="datetime"
              disabled
              :placeholder="$t('message.choseDateTime')">
            </el-date-picker>
          </el-form-item>
          {{ this.ruleForm.registtime }}
          <el-form-item :label="$t('message.age')" prop="age">
            <el-input-number v-model="ruleForm.age" controls-position="right" :min="18" :max="60"></el-input-number>
          </el-form-item>
          <el-form-item :label="$t('message.birth')" prop="birth">
            <el-date-picker
              v-model="ruleForm.birth"
              type="date"
              format="yyyy-MM-dd"
              value-format="yyyy-MM-dd"
              :placeholder="$t('message.choseDate')">
            </el-date-picker>
          </el-form-item>
          <el-form-item :label="$t('message.place')" prop="place">
            <el-select v-model="ruleForm.place.province" placeholder="请选择">
              <el-option
                v-for="item in provinceOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
            <el-select v-model="ruleForm.place.city" placeholder="请选择">
              <el-option
                v-for="item in cityOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item :label="$t('message.sex')" prop="sex">
            <el-radio-group v-model="ruleForm.sex">
              <el-radio :label="1">{{ $t('message.man') }}</el-radio>
              <el-radio :label="0">{{ $t('message.woman') }}</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item :label="$t('message.admin')" prop="admin">
            <el-radio-group v-model="ruleForm.admin">
              <el-radio :label="1">{{ $t('message.yes') }}</el-radio>
              <el-radio :label="0">{{ $t('message.no') }}</el-radio>
            </el-radio-group>
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
  import { getLocal } from "../../config/mUtils";

  export default {
    name: "userManage",
    components: { HeadTop },
    data () {
      return {
        isloading: true,
        tableHeight: 1030 + 'px',
        tableData: [],
        PageIndex: 1,
        PageSize: 20,
        count: 0,
        multipleSelection: [],
        editTitle: '',
        dialogVisible: false,
        labelWidth: 0 + 'px',
        isnew: 0,
        provinceOptions: [
          {
            label: '江苏',
            value: '江苏'
          }
        ],
        cityOptions: [
          {
            label: '无锡',
            value: '无锡'
          }
        ],
        ruleForm: {
          username: '',
          password: '',
          age: 18,
          birth: '',
          place: {
            province: '',
            city: ''
          },
          sex: 1,
          admin: 0,
          state: 1
        },
        rules: {
          username: [{ required: true, message:this.$t('message.inputUsername'), trigger: 'blur' }],
          password: [{ required: true, message:this.$t('message.inputPassword'), trigger: 'blur' }],
          age: [{ required: true, message:this.$t('message.inputAge'), trigger: 'blur' }],
          birth: [{ required: true, message:this.$t('message.choseBirth'), trigger: 'blur' }],
          place: [{ required: true, message:this.$t('message.chosePlace'), trigger: 'blur' }],
          sex: [{ required: true, message:this.$t('message.choseSex'), trigger: 'blur' }],
          admin: [{ required: true, message:this.$t('message.choseAdmin'), trigger: 'blur' }],
          state: [{ required: true, message:this.$t('message.choseState'), trigger: 'blur' }],
        }
      }
    },
    created() {
      this.getUsers();
      this.resetLabelWidth();
    },
    watch:{
    },
    methods: {
      resetLabelWidth() {
        if (getLocal('lang') === 'en_US') {
          this.labelWidth = 80 + 'px';
        } else {
          this.labelWidth = 60 + 'px';
        }
      },
      getUsers() {
        let param = {
          pageIndex: this.PageIndex,
          pageSize: this.PageSize
        };
        this.tableData = [];
        this.fetch.ajax('/user/getUsers', param, 'POST')
          .then(res => {
            if (res.data.state) {
              console.log(res);
              res.data.rows.list.forEach(item => {
                item.registtime = item.registtime.substring(0, item.registtime.indexOf(".")).replace("T", " ");
                item.state = item.state === 1 ? true : false;
                this.tableData.push(item);
              });
              console.log(this.tableData);
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
        this.getUsers();
      },
      handleCurrentChange(val) {
        this.PageIndex = val;
        this.getUsers();
      },
      handleAdd() {
        this.editTitle = this.$t('message.add');
        this.dialogVisible = true;
        let myDate = new Date();
        console.log(myDate.toLocaleTimeString());
        this.ruleForm = {
          username: '',
          password: '',
          registtime: '2019-04-08 00:00:00',
          age: 18,
          birth: '',
          place: {
            province: '',
            city: ''
          },
          sex: 1,
          admin: 0,
          state: 1
        };
        this.isnew = 0;
      },
      handleDel() {
        if (this.multipleSelection.length > 0) {} else {
          this.$message({
            message: this.$t('message.choseDelData'),
            type: 'warning'
          });
        }
      },
      handleResetPWD() {
        if (this.multipleSelection.length > 0) {} else {
          this.$message({
            message: this.$t('message.choseResetPWDData'),
            type: 'warning'
          });
        }
      },
      setState() {},
      handleEdit(index, row) {
        this.editTitle = this.$t('message.edit');
        this.dialogVisible = true;
        this.ruleForm = {
          username: row.username,
          password: row.password,
          registtime: row.registtime,
          age: row.age,
          birth: row.birth,
          place: {
            province: row.place,
            city: row.place
          },
          sex: row.sex,
          admin: row.admin,
          state: row.state,
        };
        this.isnew = 1;
      },
      addUser() {
        let param = {
          userid: 0,
          username: this.ruleForm.username,
          password: this.ruleForm.password,
          registtime: this.ruleForm.registtime,
          age: this.ruleForm.age,
          birth: this.ruleForm.birth,
          place: this.ruleForm.place.province + '-' + this.ruleForm.place.city,
          sex: this.ruleForm.sex,
          admin: this.ruleForm.admin,
          state: this.ruleForm.state,
        };
        this.fetch.ajax('/user/addUser', param, 'POST')
          .then(res => {
            if (res.data.state) {
              console.log(res);
              this.dialogVisible = false;
              this.getUsers();
            }
          })
      },
      submit(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            if (this.isnew === 0) {
              this.addUser();
            }
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      resetForm() {
        this.$refs['ruleForm'].resetFields();
      }
    }
  }
</script>

<style scoped>

</style>
