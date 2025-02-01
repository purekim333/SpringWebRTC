<template>
  <div id="app">
    <div v-if="!isJoined">
      <!-- 방 참여 버튼만 표시 -->
      <button @click="handleJoinSession">Join Session</button>
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
import { useRoute } from 'vue-router';

export default {
  name: 'RoomDetailView',
  components: {
    UserVideo,
  },
  setup() {
    const roomStore = useRoomStore();
    const route = useRoute();

    // 세션 ID는 라우터 파라미터에서 가져옴
    const sessionId = route.params.roomId;

    // OpenVidu 관련 변수들
    const OV = ref(null);
    const session = ref(null);
    const mainStreamManager = ref(null);
    const publisher = ref(null);
    const subscribers = ref([]);
    const isJoined = ref(false);

    // 스토어에 저장된 OpenVidu 토큰 사용
    const openviduToken = computed(() => roomStore.getOpenViduToken());
    // 사용자 이름: 만약 store에 닉네임이 저장되어 있으면 사용하고, 그렇지 않으면 기본값을 사용
    const userName = computed(() => roomStore.nickname || 'Anonymous');

    const handleJoinSession = async () => {
      if (!openviduToken.value) {
        console.error('OpenVidu 토큰이 없습니다. 방을 생성하거나 참가해야 합니다.');
        return;
      }

      // OpenVidu 초기화 및 세션 생성
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

      try {
        // OpenVidu 세션에 연결
        await session.value.connect(openviduToken.value, { clientData: userName.value });
        console.log('Connected to OpenVidu session');
        isJoined.value = true;

        // 퍼블리셔 초기화 (자신의 비디오 스트림 퍼블리싱)
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
      sessionId,
      handleJoinSession,
      leaveSession,
      mainStreamManager,
      publisher,
      subscribers,
      updateMainVideoStreamManager,
      isJoined,
      openviduToken,
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
