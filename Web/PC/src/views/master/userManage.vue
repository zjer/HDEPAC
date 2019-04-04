<template>
  <div>
    <head-top></head-top>
    <div class="search-box" id="searchBox">
      <el-form :model="ruleForm" ref="ruleForm">
        <el-form-item :label="$t('message.WMSName')" prop="CompanyName" :label-width="nameW">
          <el-input clearable v-model="ruleForm.CompanyName"></el-input>
        </el-form-item>
        <el-form-item :label="$t('message.WMSCode')" prop="CompanyCode" :label-width="companyW">
          <el-input clearable v-model="ruleForm.CompanyCode"></el-input>
        </el-form-item>
        <div class="button-line">
          <el-form-item>
            <el-tooltip class="item" effect="dark" :content="$t('message.Search')" placement="top">
              <el-button type="primary" @click="submitForm('ruleForm')" icon="iconfont icon-sousuo"></el-button>
            </el-tooltip>
            <el-tooltip class="item" effect="dark" :content="$t('message.Reset')" placement="top">
              <el-button type="info" @click="resetForm('ruleForm')" icon="iconfont icon-zhongzhi"></el-button>
            </el-tooltip>
          </el-form-item>
        </div>
      </el-form>
    </div>
    <div class="table_container">
      <el-tooltip class="item" effect="dark" :content="$t('message.Add')" placement="top">
        <el-button class="addBtn" type="primary" icon="iconfont icon-xinzeng-wubiankuang" @click="handleAdd()"></el-button>
      </el-tooltip>
      <el-table
        v-loading='isloading'
        ref="multipleTable"
        :data="tableData"
        :row-key="row => row.index"
        style="width: 100%"
        class="tableStyle"
        :max-height="tableHeight"
        :height="tableHeight"
        :header-cell-style="{
                    'background-color': '#f8f8f9',
                }">
        <el-table-column :label="$t('message.WMSName')" prop="CompanyName">
        </el-table-column>
        <el-table-column :label="$t('message.CompanyEnglishName')" prop="CompanyENName">
        </el-table-column>
        <el-table-column :label="$t('message.Domain')" prop="DoMain">
        </el-table-column>
        <el-table-column :label="$t('message.Operation')" width="130">
          <template slot-scope="scope">
            <el-tooltip class="item" effect="dark" :content="$t('message.SetUpAdministrator')" placement="top">
              <el-button icon="iconfont icon-renyuanguanli" class="hoverBtn" @click="handleSetAdmin(scope.$index, scope.row)"></el-button>
            </el-tooltip>
            <el-tooltip class="item" effect="dark" :content="$t('message.Edit')" placement="top">
              <el-button icon="iconfont icon-bianji-kong" class="hoverBtn" @click="handleEdit(scope.$index, scope.row)"></el-button>
            </el-tooltip>
            <el-tooltip class="item" effect="dark" :content="$t('message.Delete')" placement="top">
              <el-button v-if="scope.row.IsSystemAdmin!=2"  icon="iconfont icon-shanchu-kong" class="hoverBtn" @click="handleDelete(scope.$index, scope.row)"></el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
      <div class="Pagination">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-sizes="[10, 20, 30, 40]"
          :page-size="PageSize"
          background
          layout="total, prev, pager, next, sizes"
          :total="count">
        </el-pagination>
      </div>
      <el-dialog :title="editTitle" :visible.sync="dialogAddVisible" width="600px" :close-on-click-modal='false'>
        <el-form :model="addForm" :rules="rules" ref="addForm" :label-width="labelWidth">
          <el-form-item :label="$t('message.CompanyName')" prop="CompanyName">
            <el-input v-model="addForm.CompanyName" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item :label="$t('message.CompanyEnglishName')" prop="CompanyENName">
            <el-input v-model="addForm.CompanyENName" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item :label="$t('message.CompanyCode')" prop="CompanyCode">
            <el-input v-model="addForm.CompanyCode" :disabled="isEdit"></el-input>
          </el-form-item>
          <el-form-item :label="$t('message.Domain')" prop="DoMain">
            <el-input v-model="addForm.DoMain" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item :label="$t('message.NameFormat')" prop="NameFormat">
            <el-radio-group v-model="addForm.NameFormat">
              <el-radio :label="2">2</el-radio>
              <el-radio :label="3">3</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item :label="$t('message.ShowPrivacy')" prop="IsShowPrivacy">
            <el-radio-group v-model="addForm.IsShowPrivacy">
              <el-radio :label="1">{{$t('message.Yes_y')}}</el-radio>
              <el-radio :label="0">{{$t('message.No_n')}}</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item :label="$t('message.IsNeedSendInformation')" prop="IsNeedSendInformation">
            <el-radio-group v-model="addForm.IsNeedSendInformation">
              <el-radio :label="true">{{$t('message.Yes')}}</el-radio>
              <el-radio :label="false">{{$t('message.No')}}</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-tooltip class="item" effect="dark" :content="$t('message.Cancel')" placement="top">
            <el-button @click="dialogAddVisible = false" icon="iconfont icon-guanbi-wukuang"></el-button>
          </el-tooltip>
          <el-tooltip class="item" effect="dark" :content="$t('message.Save')" placement="top">
            <el-button type="primary" @click="add('addForm')" icon="iconfont icon-gou-wukuang"></el-button>
          </el-tooltip>
        </div>
      </el-dialog>
      <el-dialog :title="editTitle" :visible.sync="dialogSetAdminVisible" width="700px" :close-on-click-modal='false'>
        <el-form :model="setForm" :rules="setrules" ref="setForm" :label-width="labelWidths">
          <el-form-item :label="$t('message.UserAccount')" prop="Name">
            <el-input v-model="setForm.Name" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item :label="$t('message.LoginName')" prop="LoginName">
            <el-input v-model="setForm.LoginName" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item :label="$t('message.Email')" prop="Email">
            <el-input v-model="setForm.Email"  auto-complete="off"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-tooltip class="item" effect="dark" :content="$t('message.Cancel')" placement="top">
            <el-button @click="dialogSetAdminVisible = false" icon="iconfont icon-guanbi-wukuang"></el-button>
          </el-tooltip>
          <el-tooltip class="item" effect="dark" :content="$t('message.Save')" placement="top">
            <el-button type="primary" @click="setAdmin('setForm')" icon="iconfont icon-gou-wukuang"></el-button>
          </el-tooltip>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
  import HeadTop from '../../components/headTop';

  export default {
    name: "userManage",
    components: { HeadTop },
    data () {
      return {}
    },
    created() {
      this.getUsers();
    },
    methods: {
      getUsers() {
        let param = {};
        this.fetch.ajax('/user/getUsers', param, 'GET')
          .then(res => {
            if (res.data.state) {
              console.log(res);
            } else {
            }
          })
      },
    }
  }
</script>

<style scoped>

</style>
