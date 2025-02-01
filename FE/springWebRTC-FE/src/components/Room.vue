<template>
    <div class="room">
      <h3>{{ room.title }}</h3>
      <p>방 상태 : {{ room.gameStatus }}</p>
      <p>호스트: {{ room.hostNickName }}</p>
      <p>현재 참가자: {{ room.currentParticipantCount }}명</p>
      <button @click="handleJoin">참여하기</button>
    </div>
  </template>
  
  <script setup>
  import { useRouter } from 'vue-router';
  import { useRoomStore } from '@/stores/RoomCounter';
  
  // 단일 props 객체로 받기
  const props = defineProps({
    room: {
      type: Object,
      required: true,
    },
  });

  const router = useRouter();
  const roomStore = useRoomStore();

  const roomId = props.room.roomId
  const token = roomStore.getToken();
  
  const handleJoin = async () => {
    try {
      const userName = prompt('참여할 사용자 이름을 입력해주세요:');
      if (!userName) return alert('사용자 이름은 필수입니다.');
      const roomData = await roomStore.joinRoom(roomId, userName);
      router.push({ name: 'roomDetail', params: { roomId: roomId } });
    } catch (error) {
      console.error('방 참여 실패:', error);
      alert('방 참여에 실패했습니다.');
    }
  };
  </script>
  
  <style scoped>
  .room {
    border: 1px solid #ddd;
    border-radius: 8px;
    padding: 1rem;
    margin-bottom: 1rem;
    display: flex;
    flex-direction: column;
    align-items: flex-start;
  }
  
  h3 {
    font-size: 1.2rem;
    color: #333;
    margin-bottom: 0.5rem;
  }
  
  p {
    font-size: 1rem;
    color: #666;
    margin: 0.3rem 0;
  }
  
  button {
    padding: 0.5rem 1rem;
    background-color: #007bff;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    transition: background-color 0.3s;
  }
  
  button:hover {
    background-color: #0056b3;
  }
  </style>
  