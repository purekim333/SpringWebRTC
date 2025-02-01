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
    <div>
      <label for="nickname">사용자 이름:</label>
      <input id="nickname" v-model="nickname" type="text" required />
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
const nickname = ref('');

const roomStore = useRoomStore();

const handleSubmit = async () => {
  try {
    const response = await roomStore.createRoom(title.value, password.value, nickname.value);
    alert('방이 생성되었습니다.');
    console.log("response:", response);
    const roomId = response.data.roomId;
    title.value = '';
    password.value = '';
    nickname.value = '';
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
