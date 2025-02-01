<template>
    <video autoplay playsinline></video>
  </template>
  
  <script>
  export default {
    name: 'OvVideo',
    props: {
      streamManager: Object,
    },
    mounted() {
      this.streamManager.addVideoElement(this.$el);
    },
    watch: {
      streamManager(newStreamManager) {
        if (newStreamManager) {
          newStreamManager.addVideoElement(this.$el);
        }
      },
    },
  };
  </script>
  
  <style scoped>
  video {
    width: 100%;
    height: auto;
    background-color: black;
  }
  </style>
  