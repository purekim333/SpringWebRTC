<template>
  <div>
    <h1>방 만들기</h1>
    <input v-model="roomName" placeholder="Enter room name" />
    <button @click="createRoom">Create</button>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'CreateRoomView',
  data() {
    return {
      roomName: '', // 새로운 방 이름
    };
  },
  methods: {
    async createRoom() {
      if (!this.roomName.trim()) {
        alert('Room name is required.');
        return;
      }
      try {
        const response = await axios.post('http://localhost:8080/api/rooms', {
          name: this.roomName,
        });
        const roomId = response.data.roomId;
        this.$router.push(`/join-room/${roomId}`);
      } catch (error) {
        console.error('Failed to create room:', error);
      }
    },
  },
};
</script>
