import axios from 'axios';
import { defineStore } from 'pinia';
import { useRouter } from 'vue-router';
import { ref } from 'vue';

export const useRoomStore = defineStore('roomStore', () => {
  const BASE_URL = "http://localhost:8080/api/v1";
  const token = ref('');
  const openviduToken = ref(''); // 사용자 토큰(rtc)

  const login = async (userName, password) => {
    try {
      const response = await axios.post(`${BASE_URL}/auth/login`, {
        username: userName,
        password: password
      });
      token.value = response.data.data.accessToken;
      return response.data;
    } catch (error) {
      console.error("로그인 실패: ", error);
      throw error;
    }
  }

  // 서버에서 방 목록 가져오기
  const fetchRooms = async () => {
    try {
      const response = await axios.get(`${BASE_URL}/rooms`, {
        headers: { Authorization: `Bearer ${token.value}` }
      });
      console.log('방 목록:', response.data);
      return response.data;
    } catch (error) {
      console.error('목록 가져오기 실패:', error);
      throw error;
    }
  };

  // 방 생성 및 참가
  const createRoom = async (title, password) => {
    try {
      const response = await axios.post(
        `${BASE_URL}/rooms`,
        {
          title: title,
          password: password
        },
        {
          headers: { Authorization: `Bearer ${token.value}` }
        }
      );
      console.log('방 생성 성공:', response.data);
      // 백엔드 응답에서 OpenVidu 토큰 (ws:// URL 형태) 추출
      openviduToken.value = response.data.data.token;
      return response.data;
    } catch (error) {
      console.error('방 만들기 실패:', error);
      throw error;
    }
  };
  
  

  // 특정 방 참가
  const joinRoom = async (roomId, password) => {
    try {
      const response = await axios.post(
        `${BASE_URL}/rooms/${roomId}/join`,
        {
          password: password,
        },
        {
          headers: { Authorization: `Bearer ${token.value}` }
        }
      );
      console.log('방 참가 성공:', response.data);
      openviduToken.value = response.data.data.token;
      return response.data;
    } catch (error) {
      console.error('방 참가 실패:', error);
      throw error;
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
      const response = await axios.get(`${BASE_URL}/rooms/${roomId}`, {
        headers: { Authorization: `Bearer ${token.value}` }
      });
      return response.data;
    } catch (error) {
      console.error('방 상세 정보 가져오기 실패:', error);
      throw error;
    }
  };

  const getToken = () => {
    return token.value;
  }

  const getOpenViduToken = () => {
    return openviduToken.value;
  }

  
  return {
    login,
    fetchRooms,
    createRoom,
    joinRoom,
    leaveRoom,
    fetchRoomDetail,
    getToken,
    getOpenViduToken
  };
});
