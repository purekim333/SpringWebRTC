<template>
  <form class="create-room-form" @submit.prevent="handleSubmit">
    <div>
      <label for="title">방 제목:</label>
      <input id="title" v-model="title" type="text" required />
    </div>
    <div>
      <label for="password">비밀번호:</label>
      <input id="password" v-model="password" type="password" />
    </div>
    <button type="submit">방 생성</button>
  </form>
</template>

<script setup>
import { ref } from 'vue';
import { useRoomStore } from '@/stores/RoomCounter';
import { useRouter } from 'vue-router';

const router = useRouter();
const title = ref('');
const password = ref('');

const roomStore = useRoomStore();

const handleSubmit = async () => {
  try {
    // 로그인 후 JWT 토큰이 스토어에 저장되어 있으므로, 이를 이용해 인증된 상태에서 방 생성 API 호출
    const response = await roomStore.createRoom(title.value, password.value);
    alert('방이 생성되었습니다.');
    console.log("response:", response);
    // 백엔드 응답 형식에 따라 방 ID는 response.data.data.roomId에 있음
    const roomId = response.data.roomId;
    // 입력 필드 초기화
    title.value = '';
    password.value = '';
    // 방 상세 페이지로 이동 (라우터 이름은 실제 설정에 맞게 조정)
    router.push({ name: 'roomDetail', params: { roomId: roomId } });
  } catch (error) {
    console.error('방 생성 실패:', error);
    alert('방 생성에 실패했습니다.');
  }
};
</script>

<style scoped>
.create-room-form {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  max-width: 400px;
  margin: 0 auto;
}

label {
  font-weight: bold;
  margin-bottom: 0.5rem;
}

input {
  padding: 0.5rem;
  border: 1px solid #ddd;
  border-radius: 5px;
}

button {
  padding: 0.7rem;
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
