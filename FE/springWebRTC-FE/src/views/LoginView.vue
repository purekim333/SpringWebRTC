<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { useRoomStore } from '@/stores/RoomCounter';

const router = useRouter();
const roomStore = useRoomStore();

// 스토어에 정의된 login 메서드를 사용하므로, 로컬 상태는 최소화
const username = ref('');
const password = ref('');
const errorMessage = ref('');

const login = async () => {
  errorMessage.value = '';
  try {
    // 스토어의 login 메서드 호출 (백엔드 로그인 API 호출 및 토큰 저장)
    await roomStore.login(username.value, password.value);
    // 로그인 성공 후 홈 페이지로 이동
    router.push('/');
  } catch (error) {
    errorMessage.value = error.response?.data?.message || '로그인 실패. 다시 시도해주세요.';
  }
};
</script>

<template>
  <div class="login-container">
    <h2>로그인</h2>
    <form @submit.prevent="login">
      <div>
        <label for="username">아이디</label>
        <input id="username" v-model="username" type="text" required />
      </div>
      <div>
        <label for="password">비밀번호</label>
        <input id="password" v-model="password" type="password" required />
      </div>
      <button type="submit">로그인</button>
    </form>
    <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
  </div>
</template>

<style scoped>
.login-container {
  max-width: 400px;
  margin: 2rem auto;
  padding: 1rem;
  border: 1px solid #ddd;
  border-radius: 5px;
}
.login-container h2 {
  text-align: center;
  margin-bottom: 1rem;
}
.login-container form {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}
.login-container label {
  font-weight: bold;
}
.login-container input {
  padding: 0.5rem;
  border: 1px solid #ccc;
  border-radius: 3px;
}
.login-container button {
  padding: 0.5rem;
  background-color: #007bff;
  color: #fff;
  border: none;
  border-radius: 3px;
  cursor: pointer;
  transition: background-color 0.3s;
}
.login-container button:hover {
  background-color: #0056b3;
}
.error {
  color: red;
  text-align: center;
}
</style>
