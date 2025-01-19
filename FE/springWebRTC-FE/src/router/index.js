import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '@/views/HomeView.vue'
import CreateRoomView from '@/views/CreateRoomView.vue'
import JoinRoomView from '@/views/JoinRoomView.vue'


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
    },
    {
      path: '/create',
      name: 'create',
      component: CreateRoomView,
    },
    {
      path: '/join',
      name: 'join',
      component: JoinRoomView,
    }
  ],
})

export default router
