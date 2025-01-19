<template>
  <div>
    <h1>Room: {{ roomId }}</h1>
    <div v-for="msg in messages" :key="msg.id">
      {{ msg.sender }}: {{ msg.content }}
    </div>
    <input v-model="message" placeholder="Enter your message" />
    <button @click="sendMessage">Send</button>
  </div>
</template>

<script>
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';

export default {
  name: 'JoinRoomView',
  props: ['roomId'], // URL에서 roomId를 전달받음
  data() {
    return {
      stompClient: null,
      messages: [],
      message: '',
    };
  },
  mounted() {
    this.connectToRoom();
  },
  methods: {
    connectToRoom() {
      const socket = new SockJS('http://localhost:8080/ws');
      this.stompClient = Stomp.over(socket);

      this.stompClient.connect({}, () => {
        this.stompClient.subscribe(`/subscribe/chat/${this.roomId}`, (msg) => {
          this.messages.push(JSON.parse(msg.body));
        });
      });
    },
    sendMessage() {
      if (!this.message.trim()) return;
      this.stompClient.send(`/publish/chat/${this.roomId}`, {}, JSON.stringify({
        sender: 'User1', // 임시 사용자 이름
        content: this.message,
        roomId: this.roomId,
      }));
      this.message = '';
    },
  },
};
</script>
