<template>
  <div class="create-room-view">
    <h1>방 생성</h1>
    <!-- CreateRoomForm 컴포넌트 렌더링 -->
    <CreateRoomForm />
  </div>
</template>

<script setup>
import CreateRoomForm from '@/components/CreateRoomForm.vue';
</script>

<style scoped>
.create-room-view {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 2rem;
}

h1 {
  font-size: 2rem;
  color: #007bff;
  margin-bottom: 1rem;
}
</style>
