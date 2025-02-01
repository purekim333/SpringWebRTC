<template>
  <div v-if="streamManager">
    <ov-video :stream-manager="streamManager" />
    <div><p>{{ clientData }}</p></div>
  </div>
</template>

<script>
import OvVideo from './OvVideo.vue';

export default {
  name: 'UserVideo',
  components: {
    OvVideo,
  },
  props: {
    streamManager: {
      type: Object,
      required: true,
    },
  },
  computed: {
    clientData() {
      const data = this.getConnectionData();
      return data.clientData;
    },
  },
  methods: {
    getConnectionData() {
      const { connection } = this.streamManager.stream;
      try {
        return JSON.parse(connection.data);
      } catch (e) {
        // 만약 JSON 파싱에 실패하면, connection.data가 단순 문자열이라고 가정하고 fallback 처리
        return { clientData: connection.data };
      }
    },
  },
};
</script>

<style scoped>
div {
  text-align: center;
}
p {
  margin-top: 0.5rem;
  font-weight: bold;
}
</style>
