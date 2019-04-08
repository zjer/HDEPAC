import Vue from 'vue'
import VueI18n from 'vue-i18n'
import locale from 'element-ui/lib/locale'
import messages from './langs'
import { setLocal, getLocal } from "../config/mUtils";

Vue.use(VueI18n);

let langKey = getLocal('lang');
if (langKey !== null) {
  if (langKey === 'en_US') {
    langKey = 'en_US';
  } else {
    langKey = 'zh_CN';
  }
} else {
  langKey = 'zh_CN';
  setLocal('lang', 'zh_CN');
}

const i18n = new VueI18n({
  locale: langKey,
  messages,
  silentTranslationWarn: true
});
locale.i18n((key, value) => i18n.t(key, value)); //为了实现element插件的多语言切换

export default i18n
