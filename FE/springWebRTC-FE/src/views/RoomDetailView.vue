<template>
  <div id="app">
    <div v-if="!isJoined">
      <!-- 방 생성 또는 참가 폼 -->
      <form @submit.prevent="handleJoinSession">
        <input v-model="mySessionId" placeholder="Session ID" required />
        <input v-model="myUserName" placeholder="Your Name" required />
        <button type="submit">Join Session</button>
      </form>
    </div>
    <div v-else>
      <!-- 비디오 스트림 표시 -->
      <div id="main-video">
        <UserVideo :stream-manager="mainStreamManager" />
      </div>
      <div id="video-container">
        <UserVideo
          :stream-manager="publisher"
          @click.native="updateMainVideoStreamManager(publisher)"
        />
        <UserVideo
          v-for="sub in subscribers"
          :key="sub.stream.connection.connectionId"
          :stream-manager="sub"
          @click.native="updateMainVideoStreamManager(sub)"
        />
      </div>
      <button @click="leaveSession">Leave Session</button>
    </div>
  </div>
</template>

<script>
import { ref, computed } from 'vue';
import { OpenVidu } from 'openvidu-browser';
import UserVideo from '@/components/UserVideo.vue';
import { useRoomStore } from '@/stores/RoomCounter';

export default {
  name: 'RoomDetailView',
  components: {
    UserVideo,
  },
  setup() {
    const roomStore = useRoomStore();
    const OV = ref(null);
    const session = ref(null);
    const mainStreamManager = ref(null);
    const publisher = ref(null);
    const subscribers = ref([]);
    const isJoined = ref(false);

    const mySessionId = ref('');
    const myUserName = ref('');

    const token = computed(() => roomStore.token);
    const userName = computed(() => roomStore.nickname);

    const handleJoinSession = async () => {
      if (!token.value) {
        console.error('토큰이 없습니다. 방을 생성하거나 참가해야 합니다.');
        return;
      }

      // OpenVidu 초기화
      OV.value = new OpenVidu();
      session.value = OV.value.initSession();

      // 이벤트 핸들러 설정
      session.value.on('streamCreated', ({ stream }) => {
        const subscriber = session.value.subscribe(stream, undefined);
        subscribers.value.push(subscriber);
      });

      session.value.on('streamDestroyed', ({ stream }) => {
        const index = subscribers.value.indexOf(stream.streamManager, 0);
        if (index >= 0) {
          subscribers.value.splice(index, 1);
        }
      });

      session.value.on('exception', ({ exception }) => {
        console.warn(exception);
      });

      // 세션에 연결
      try {
        await session.value.connect(token.value, { clientData: userName.value });
        console.log('Connected to OpenVidu session');
        isJoined.value = true;

        // 퍼블리셔 초기화
        publisher.value = OV.value.initPublisher(undefined, {
          audioSource: undefined,
          videoSource: undefined,
          publishAudio: true,
          publishVideo: true,
          resolution: '640x480',
          frameRate: 30,
          mirror: false,
        });

        mainStreamManager.value = publisher.value;
        await session.value.publish(publisher.value);
        console.log('Publishing local stream');
      } catch (error) {
        console.error('Error connecting to the session:', error.code, error.message);
      }
    };

    const leaveSession = () => {
      if (session.value) {
        session.value.disconnect();
        session.value = null;
      }
      OV.value = null;
      mainStreamManager.value = null;
      publisher.value = null;
      subscribers.value = [];
      isJoined.value = false;
      console.log('Left the session');
    };

    const updateMainVideoStreamManager = (stream) => {
      if (mainStreamManager.value === stream) return;
      mainStreamManager.value = stream;
    };

    return {
      mySessionId,
      myUserName,
      handleJoinSession,
      leaveSession,
      mainStreamManager,
      publisher,
      subscribers,
      updateMainVideoStreamManager,
      isJoined,
      token,
      userName,
    };
  },
};
</script>

<style scoped>
#main-video {
  width: 50%;
  float: left;
}
#video-container {
  width: 50%;
  float: left;
}
video {
  width: 100%;
  max-width: 300px;
  margin: 0.5rem;
}
button {
  display: block;
  margin: 1rem auto;
  padding: 0.5rem 1rem;
}
</style>
