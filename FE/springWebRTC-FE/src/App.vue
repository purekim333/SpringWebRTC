<script setup>
import { RouterLink, RouterView } from 'vue-router';
</script>

<template>
  <header>
    <div class="wrapper">
      <nav>
        <RouterLink to="/">Home</RouterLink>
        <RouterLink to="/login">로그인</RouterLink>
        <RouterLink to="/join">방목록</RouterLink>
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
