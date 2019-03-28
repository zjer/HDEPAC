<template>
  <div>
    <weather v-if="$store.state.showWeather"></weather>
    <news v-if="$store.state.showNews"></news>
  </div>
</template>

<script>
  import Weather from "./weather";
  import News from "./news";

  export default {
    name: 'homeMain',
    components: { News, Weather },
  }
</script>

<style scoped>

</style>
