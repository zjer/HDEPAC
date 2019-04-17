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
        <el-table-column :label="$t('message.account')" prop="username">
        </el-table-column>
        <el-table-column :label="$t('message.password')" prop="password">
        </el-table-column>
        <el-table-column :label="$t('message.age')" prop="age">
        </el-table-column>
        <el-table-column :label="$t('message.birth')" prop="birth" >
        </el-table-column>
        <el-table-column :label="$t('message.province')" prop="province">
        </el-table-column>
        <el-table-column :label="$t('message.city')" prop="city">
        </el-table-column>
        <el-table-column :label="$t('message.area')" prop="area">
        </el-table-column>
        <el-table-column :label="$t('message.gender')" prop="gender">
        </el-table-column>
        <el-table-column :label="$t('message.createtime')" prop="createtime">
        </el-table-column>
        <el-table-column :label="$t('message.updatetime')" prop="updatetime">
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
          <el-form-item :label="$t('message.account')" prop="username">
            <el-input v-model="ruleForm.username"></el-input>
          </el-form-item>
          <el-form-item :label="$t('message.password')" prop="password">
            <el-input v-model="ruleForm.password"></el-input>
          </el-form-item>
          <el-form-item :label="$t('message.createtime')" prop="createtime">
            <el-date-picker
              v-model="ruleForm.createtime"
              type="datetime"
              disabled
              :placeholder="$t('message.choseDateTime')">
            </el-date-picker>
          </el-form-item>
          <el-form-item :label="$t('message.birth')" prop="birth">
            <el-date-picker
              v-model="ruleForm.birth"
              type="date"
              format="yyyy-MM-dd"
              value-format="yyyy-MM-dd"
              :picker-options="pickerOptions"
              @blur="getAge(ruleForm.birth)"
              :placeholder="$t('message.choseDate')">
            </el-date-picker>
          </el-form-item>
          <el-form-item :label="$t('message.age')" prop="age">
            <el-input-number v-model="ruleForm.age" controls-position="right" :min="18" :max="60" disabled></el-input-number>
          </el-form-item>
          <el-form-item :label="$t('message.place')" prop="place">
            <el-select v-model="ruleForm.place.province" :placeholder="$t('message.choseProvince')" @change="choseProvince">
              <el-option
                v-for="(item, index) in provinceOptions"
                :key="index"
                :label="item.name"
                :value="item.name">
              </el-option>
            </el-select>
            <el-select v-model="ruleForm.place.city" :placeholder="$t('message.choseCity')" @change="choseCity" @focus="getCity">
              <el-option
                v-for="(item, index) in cityOptions"
                :key="index"
                :label="item.name"
                :value="item.name">
              </el-option>
            </el-select>
            <el-select v-model="ruleForm.place.area" :placeholder="$t('message.choseArea')" @focus="getArea">
              <el-option
                v-for="(item, index) in areaOptions"
                :key="index"
                :label="item.name"
                :value="item.name">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item :label="$t('message.gender')" prop="gender">
            <el-radio-group v-model="ruleForm.gender">
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
  import { getLocal, formatDate } from "../../config/mUtils";
  import axios from 'axios'

  export default {
    name: "userManage",
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
        mapJson: '../../../static/map/map.json',
        provinceOptions: [],
        cityOptions: [],
        areaOptions: [],
        pickerOptions: {
          disabledDate: (time) => {
            return time.getTime() > new Date(1999, 11, 31)
          }
        },
        ruleForm: {
          userid: 0,
          username: '',
          password: '',
          createtime: '',
          age: 18,
          birth: '',
          place: {
            province: '',
            city: '',
            area: ''
          },
          gender: 1,
          admin: 0,
          state: 1
        },
        rules: {
          username: [{ required: true, message:this.$t('message.inputUsername'), trigger: 'blur' }],
          password: [{ required: true, message:this.$t('message.inputPassword'), trigger: 'blur' }],
          age: [{ required: true, message:this.$t('message.inputAge'), trigger: 'blur' }],
          birth: [{ required: true, message:this.$t('message.choseBirth'), trigger: 'blur' }],
          place: [{ required: true, message:this.$t('message.chosePlace'), trigger: 'change' }],
          gender: [{ required: true, message:this.$t('message.choseGender'), trigger: 'blur' }],
          admin: [{ required: true, message:this.$t('message.choseAdmin'), trigger: 'blur' }],
          state: [{ required: true, message:this.$t('message.choseState'), trigger: 'blur' }],
        }
      }
    },
    created() {
      this.getUsers();
      this.watchHeight();
      this.resetLabelWidth();
      this.getProvince();
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
          this.labelWidth = 85 + 'px';
        } else {
          this.labelWidth = 70 + 'px';
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
              res.data.rows.list.forEach(item => {
                item.createtime = item.createtime.substring(0, item.createtime.indexOf(".")).replace("T", " ");
                item.updatetime = item.updatetime.substring(0, item.updatetime.indexOf(".")).replace("T", " ");
                item.gender = item.gender === 1 ? this.$t('message.man') : this.$t('message.woman');
                item.state = item.state === 1 ? true : false;
                item.admin = item.admin === 1 ? this.$t('message.yes') : this.$t('message.no');
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
        this.getUsers();
      },
      handleCurrentChange(val) {
        this.PageIndex = val;
        this.getUsers();
      },
      handleAdd() {
        this.editTitle = this.$t('message.add');
        this.dialogVisible = true;
        this.ruleForm = {
          userid: 0,
          username: '',
          password: '',
          createtime: formatDate(new Date()),
          age: 18,
          birth: '',
          place: {
            province: '',
            city: '',
            area: ''
          },
          gender: 1,
          admin: 0,
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
              this.getUsers();
            }
          })
      },
      handleDelete() {
        if (this.multipleSelection.length > 0) {
          let str = '';
          let arr = [];
          this.multipleSelection.forEach(ele => {
            arr.push(ele.userid);
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
              this.getUsers();
            }
          })
      },
      handleResetPWD() {
        if (this.multipleSelection.length > 0) {
          let str = '';
          let arr = [];
          this.multipleSelection.forEach(ele => {
            arr.push(ele.userid);
          });
          str = arr.join(',');
          console.log(str);
          this.resetPWD(str);
        } else {
          this.$message({
            type: 'warning',
            message: this.$t('message.choseResetPWDData')
          });
        }
      },
      updateState(id, state) {
        let param = {
          userid: id,
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
              this.getUsers();
            }
          })
      },
      setState(val) {
        this.updateState(val.userid, val.state);
      },
      handleEdit(index, row) {
        this.editTitle = this.$t('message.edit');
        this.dialogVisible = true;
        this.ruleForm = {
          userid: row.userid,
          username: row.username,
          password: row.password,
          createtime: row.createtime,
          age: row.age,
          birth: row.birth,
          place: {
            province: row.province,
            city: row.city,
            area: row.area,
          },
          gender: row.gender === this.$t('message.man') ? 1 : 0,
          admin: row.admin === this.$t('message.yes') ? 1 : 0,
          state: row.state === true ? 1 : 0,
        };
        this.isnew = 1;
      },
      addUser() {
        let param = {
          userid: 0,
          username: this.ruleForm.username,
          password: this.ruleForm.password,
          age: this.ruleForm.age,
          birth: this.ruleForm.birth,
          province: this.ruleForm.place.province,
          city: this.ruleForm.place.city,
          area: this.ruleForm.place.area,
          gender: this.ruleForm.gender,
          admin: this.ruleForm.admin,
          state: this.ruleForm.state,
        };
        this.fetch.ajax('/user/addUser', param, 'POST')
          .then(res => {
            if (res.data.state) {
              console.log(res);
              this.dialogVisible = false;
              this.$message({
                type: "success",
                message: this.$t('message.saveSuccess')
              });
              this.getUsers();
            }
          })
      },
      updateUser() {
        let param = {
          userid: parseInt(this.ruleForm.userid),
          username: this.ruleForm.username,
          password: this.ruleForm.password,
          age: this.ruleForm.age,
          birth: this.ruleForm.birth,
          province: this.ruleForm.place.province,
          city: this.ruleForm.place.city,
          area: this.ruleForm.place.area,
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
              this.getUsers();
            }
          })
      },
      delUser(val) {
        let param = {
          userid: val
        };
        this.fetch.ajax('/user/delUser', param, 'POST')
          .then(res => {
            if (res.data.state) {
              console.log(res);
              this.$message({
                type: "success",
                message: this.$t('message.deleteSuccess')
              });
              this.getUsers();
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
          this.delUser(row.userid);
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
      getProvince() {
        this.provinceOptions = [];
        axios.get(this.mapJson)
          .then(res => {
            if (res.status === 200) {
              res.data.forEach(ele => {
                let province = {
                  code: ele.code,
                  name: ele.name,
                };
                this.provinceOptions.push(province);
              })
            }
          })
      },
      choseProvince() {
        this.cityOptions = [];
        this.ruleForm.place.city = '';
        this.areaOptions = [];
        this.ruleForm.place.area = '';
      },
      choseCity() {
        this.areaOptions = [];
        this.ruleForm.place.area = '';
      },
      getCity() {
        this.cityOptions = [];
        let hasProvince = this.ruleForm.place.province;
        if (hasProvince !== null || hasProvince !== '') {
          axios.get(this.mapJson)
            .then(res => {
              if (res.status === 200) {
                res.data.forEach(ele => {
                  if (ele.name === hasProvince) {
                    ele.children.forEach(item => {
                      let city = {
                        code: item.code,
                        name: item.name,
                      };
                      this.cityOptions.push(city);
                    });
                  }
                })
              }
            })
        } else {
          this.cityOptions = [];
        }
      },
      getArea() {
        this.areaOptions = [];
        let hasProvince = this.ruleForm.place.province;
        let hasCity = this.ruleForm.place.city;
        if (hasProvince !== null || hasProvince !== '' || hasCity !== null || hasCity !== '') {
          axios.get(this.mapJson)
            .then(res => {
              if (res.status === 200) {
                res.data.forEach(ele => {
                  if (ele.name === hasProvince) {
                    ele.children.forEach(item => {
                      if (item.name === hasCity) {
                        item.children.forEach(list => {
                          let area = {
                            code: list.code,
                            name: list.name,
                          };
                          this.areaOptions.push(area);
                        })
                      }
                    });
                  }
                })
              }
            })
        } else {
          this.areaOptions = [];
        }
      },
      submit(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            if (this.isnew === 0) {
              this.addUser();
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
