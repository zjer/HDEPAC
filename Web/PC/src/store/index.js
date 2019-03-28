import Vue from 'vue'
import Vuex from 'vuex'
import createPersistedState from 'vuex-persistedstate'

Vue.use(Vuex);

const state = {
  showGuide: true,
  showWeather: false,
  showNews: false
};

const mutations = {
  setGuide(state, guide) {
    state.showGuide = guide;
  },
  setWeather(state, weather) {
    state.showWeather = weather;
  },
  setNews(state, news) {
    state.showNews = news;
  },
};

export default new Vuex.Store({
  state,
  mutations,
  plugins: [createPersistedState({
    storage: window.localStorage
  })]
})
