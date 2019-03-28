import Vue from 'vue'
import locale from 'element-ui/lib/locale';
import VueI18n from 'vue-i18n'
import { getLocal } from "../config/mUtils";

Vue.use(VueI18n);

const messages = {
  'zh_CN': require('./langs/cn'),
  'en_US': require('./langs/en')
};

const i18n = new VueI18n({
  locale: getLocal('lang') || 'zh_CN',
  messages: messages,
  silentTranslationWarn: true
});
locale.i18n((key, value) => i18n.t(key, value)); //为了实现element插件的多语言切换

export default i18n
