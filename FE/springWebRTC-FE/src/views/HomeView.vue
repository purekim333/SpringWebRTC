<template>
  <div>
    <h1>방 목록</h1>
    <ul>
      <li v-for="room in rooms" :key="room.roomId">
        {{ room.name }}
        <router-link :to="`/join-room/${room.roomId}`">참여하기</router-link>
      </li>
    </ul>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'HomeView',
  data() {
    return {
      rooms: [], // 채팅방 목록
    };
  },
  created() {
    this.fetchRooms();
  },
  methods: {
    async fetchRooms() {
      try {
        const response = await axios.get('http://localhost:8080/api/rooms');
        this.rooms = response.data;
      } catch (error) {
        console.error('Failed to fetch rooms:', error);
      }
    },
  },
};
</script>
