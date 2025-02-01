<template>
    <div>
        <h1>방 목록입니당.</h1>
        <div v-if="rooms.length > 0">
            <Room
                v-for="room in rooms"
                :key="room.roomId"
                :room = room
            />
        </div>
        <div v-else>
         <p>현재 대기 중인 방이 없습니다.</p>
        </div>
    </div>
</template>

<script setup>
import Room from '@/components/Room.vue';
import { ref, onMounted } from 'vue';
import { useRoomStore } from '@/stores/RoomCounter';

const rooms = ref([])
const roomStore = useRoomStore();

onMounted(() => {
  roomStore.fetchRooms().then((response) => {
    rooms.value = response?.data || []; // 반환값을 바로 rooms에 할당
  }).catch((error) => {
    console.error('방 목록을 가져오는데 실패했습니다:', error);
  });
});
</script>

<style scoped>

</style>