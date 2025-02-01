import axios from 'axios';
import { defineStore } from 'pinia';
import { useRouter } from 'vue-router';
import { ref } from 'vue';

export const useRoomStore = defineStore('roomStore', () => {
  const BASE_URL = "http://localhost:8080/api/v1";
  const router = useRouter();
  const token = ref(''); // 사용자 토큰(rtc)
  const nickname = ref('');

  // 서버에서 방 목록 가져오기
  const fetchRooms = async () => {
    try {
      const response = await axios.get(`${BASE_URL}/rooms`);
      console.log('방 목록:', response);
      return response.data;
    } catch (error) {
      console.error('목록 가져오기 실패:', error);
    }
  };

  // 방 생성 및 참가
  const createRoom = async (title, password, userName) => {
    try {
      const response = await axios.post(`${BASE_URL}/rooms`, {
        title: title,
        password: password,
        nickname: userName,
      });
      console.log('방 생성 성공:', response);
      const tokenURL = response.data.data.token;
      // token.value = tokenURL;
      token.value = tokenURL.split("token=")[1];
      nickname.value = userName;
    //   token.value = tokenURL[1];
      return response.data
    } catch (error) {
      console.error('방 만들기 실패:', error);
    }
  };
  
  

  // 특정 방 참가
  const joinRoom = async (roomId, userName, password) => {
    try {
      const response = await axios.post(`${BASE_URL}/rooms/${roomId}/join`, {
        nickname: userName,
        password: password,
      });
      console.log('방 참가 성공:', response);
      const tokenURL = response.data.data.token
      token.value = tokenURL.split("token=tok_")[1];
      nickname.value = userName;
      return response.data
    } catch (error) {
      console.error('방 참가 실패:', error);
    }
  };

  // 특정 방 떠나기
  const leaveRoom = async (roomId) => {
    try {
      const response = await axios.post(`${BASE_URL}/rooms/${roomId}/leave`, {});
      return response.data;
    } catch (error) {
      console.error('방 나가기 실패:', error);
    }
  };

  // 방 상세 정보 가져오기
  const fetchRoomDetail = async (roomId) => {
    try {
      const response = await axios.get(`${BASE_URL}/rooms/${roomId}`);
      return response.data;
    } catch (error) {
      console.error('방 상세 정보 가져오기 실패:', error);
      throw error;
    }
  };

  // OpenVidu 서버를 통해 웹캠 연결
  const connectToWebcam = async (token) => {
    try {
      const OV = new OpenVidu(); // OpenVidu 객체 생성
      const session = OV.initSession();

      session.on('streamCreated', (event) => {
        const subscriber = session.subscribe(event.stream, 'video-container'); // 'video-container'는 HTML 요소 ID
        console.log('스트림 구독:', subscriber);
      });

      await session.connect(token);
      console.log('웹캠 연결 성공');
    } catch (error) {
      console.error('웹캠 연결 실패:', error);
    }
  };

  const getToken = () => {
    return token.value;
  }

  
  return {
    token,
    nickname,
    fetchRooms,
    createRoom,
    joinRoom,
    leaveRoom,
    fetchRoomDetail,
    getToken
  };
});
