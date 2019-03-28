<template>
  <div class="mask">
    <div class="guide">
      <van-steps :active="step" active-color="#38f">
        <van-step>{{ $t('choseLang') }}</van-step>
        <van-step>{{ $t('getCity') }}</van-step>
        <van-step>{{ $t('addModules') }}</van-step>
      </van-steps>
      <van-panel class="newPanel">
        <div v-if="step === 0">
          <van-radio-group v-model="langRadio">
            <van-cell-group>
              <van-cell
                v-for="(item, index) in langLists"
                clickable
                :key="index"
                :title="item.text"
                @click="langRadio = item.code">
                <van-radio :name="item.code" />
              </van-cell>
            </van-cell-group>
          </van-radio-group>
        </div>
        <div v-else-if="step === 1">
          <van-radio-group v-model="cityRadio">
            <van-cell-group>
              <van-cell
                v-for="(item, index) in cityLists"
                clickable
                :key="index"
                :title="item.text"
                @click="cityRadio = item.code">
                <van-radio :name="item.code" />
              </van-cell>
            </van-cell-group>
          </van-radio-group>
        </div>
        <div v-else>
          <van-checkbox-group v-model="moduleResult" @change="getModules(moduleResult)">
            <van-cell-group>
              <van-cell
                v-for="(item, index) in moduleLists"
                clickable
                :key="index"
                :title="item.text"
                @click="toggle(index)">
                <van-checkbox :name="item.code" ref="checkboxes" />
              </van-cell>
            </van-cell-group>
          </van-checkbox-group>
        </div>
        <div slot="footer">
          <van-button size="small" :disabled="step === 0" @click="preStep">{{ $t('preStep') }}</van-button>
          <van-button type="info" size="small" :disabled="canComplete" @click="complete" v-if="step === 2">{{ $t('complete') }}</van-button>
          <van-button type="info" size="small" :disabled="canNext" @click="nextStep" v-else>{{ $t('nextStep') }}</van-button>
        </div>
      </van-panel>
    </div>
  </div>
</template>

<script>
  import { setLocal, getLocal } from "../../config/mUtils";

  export default {
    name: 'guide',
    data () {
      return {
        step: 0,
        langRadio: getLocal('lang') || 'zh_CN',
        cityRadio: getLocal('city') || 'wx',
        moduleResult: [],
        showModules: [],
        langLists: [
          {code: 'zh_CN', text: this.$t('chinese')},
          {code: 'en_US', text: this.$t('english')}
        ],
        cityLists: [
          {code: 'wx', text: this.$t('wuXi')},
          {code: 'cz', text: this.$t('changZhou')},
          {code: 'nt', text: this.$t('nanTong')}
        ],
        moduleLists: [
          {code: 'setWeather', text: this.$t('weather')},
          {code: 'setNews', text: this.$t('news')}
        ],
        canComplete: true,
        canNext: false
      }
    },
    methods: {
      preStep() {
        if (this.step > 0) {
          this.step -= 1;
        } else {
          this.step = 0;
        }
      },
      nextStep() {
        this.step = ++this.step % 3;
      },
      toggle(index) {
        this.$refs.checkboxes[index].toggle();
      },
      getModules(val) {
        console.log(val);
        if (val.length > 0) {
          this.canComplete = false;
          this.showModules = val;
        }
      },
      complete() {
        console.log(this.showModules);
        this.$i18n.locale = this.langRadio;
        setLocal('lang', this.langRadio);
        setLocal('city', this.cityRadio);
        this.$store.commit('setGuide', false);
        this.showModules.forEach(item => {
          this.$store.commit(item, true);
        });
      },
    }
  }
</script>

<style scoped>

</style>
