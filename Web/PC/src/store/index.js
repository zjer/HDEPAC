import Vue from 'vue'
import Vuex from 'vuex'
import createPersistedState from 'vuex-persistedstate'

Vue.use(Vuex);

const state = {
  language: 1,
};

const mutations = {
  setLang(state, lang) {
    state.language = lang;
  }
};

export default new Vuex.Store({
  state,
  mutations,
  plugins: [createPersistedState({
    storage: window.localStorage
  })]
})
