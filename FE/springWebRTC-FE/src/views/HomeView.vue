<template>
  <div>
    <h1>홈입니당</h1>
    <div v-if="isLoggedIn">
      <p>로그인 되어 있습니다.</p>
    </div>
    <div v-else>
      <p>로그인이 필요합니다.</p>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue';
import { useRoomStore } from '@/stores/RoomCounter';

const roomStore = useRoomStore();

// 토큰이 있으면 로그인된 것으로 판단
const isLoggedIn = computed(() => roomStore.getToken() !== '');
</script>

<style scoped>
/* 필요에 따라 추가 스타일 적용 */
</style>
