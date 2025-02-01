<script setup>
import { RouterLink, RouterView } from 'vue-router';
import { computed } from 'vue';
import { useRoomStore } from '@/stores/RoomCounter';

const roomStore = useRoomStore();

// 토큰이 존재하면 로그인한 것으로 판단 (빈 문자열이 아니면 true)
const isLoggedIn = computed(() => !!roomStore.getToken());
</script>

<template>
  <header>
    <div class="wrapper">
      <nav>
        <RouterLink to="/">Home</RouterLink>
        <template v-if="!isLoggedIn">
          <RouterLink to="/login">로그인</RouterLink>
        </template>
        <template v-else>
          <!-- 로그인 상태라면 프로필/로그아웃 링크 표시 (필요에 따라 컴포넌트를 연결) -->
          <RouterLink to="/profile">프로필</RouterLink>
          <RouterLink to="/logout">로그아웃</RouterLink>
        </template>
        <RouterLink to="/rooms">방목록</RouterLink>
        <RouterLink to="/create">방생성</RouterLink>
      </nav>
    </div>
  </header>

  <main>
    <RouterView />
  </main>
</template>

<style scoped>
/* Header Styles */
header {
  display: flex;
  flex-direction: column;
  align-items: center; /* 중앙 정렬 */
  line-height: 1.5;
  padding: 1rem 0;
  background-color: #f5f5f5;
}

/* Logo Styles */
.logo {
  font-size: 1.5rem;
  font-weight: bold;
  color: #333;
  margin-bottom: 1rem;
}

/* Navigation Styles */
nav {
  display: flex;
  justify-content: center; /* 중앙 정렬 */
  align-items: center;
  gap: 1.5rem; /* 링크 간 간격 */
  width: 100%;
  font-size: 1rem;
}

nav a {
  text-decoration: none;
  color: #007bff;
  padding: 0.5rem 1rem;
  border-radius: 5px;
  transition: background-color 0.3s, color 0.3s;
}

nav a:hover {
  background-color: #007bff;
  color: #fff;
}

nav a.router-link-exact-active {
  font-weight: bold;
  border-bottom: 2px solid #007bff;
}

/* Main Styles */
main {
  text-align: center;
  padding: 2rem;
  color: #333;
}

/* Responsive Styles */
@media (min-width: 1024px) {
  header {
    flex-direction: row;
    justify-content: space-between;
    padding: 1rem 2rem;
  }

  .logo {
    margin-bottom: 0;
  }

  nav {
    justify-content: flex-end;
  }
}
</style>
