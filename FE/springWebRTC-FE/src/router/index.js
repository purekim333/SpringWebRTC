import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '@/views/HomeView.vue'
import CreateRoomView from '@/views/CreateRoomView.vue'
import LoginView from '@/views/LoginView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: () => import('@/views/HomeView.vue'), // Lazy Loading
    },
    {
      path: '/create',
      name: 'create',
      component: () => import('@/views/CreateRoomView.vue'), // Lazy Loading
    },
    {
      path: '/login',
      name: 'login',
      component: LoginView,
    },
    {
      path: '/rooms',
      name: 'rooms',
      component: () => import('@/views/RoomListView.vue'), // Lazy Loading
    },
    {
      path: '/rooms/:roomId',
      name: 'roomDetail',
      component: () => import('@/views/RoomDetailView.vue')
    },
  ],
});

router.beforeEach((to, from, next) => {
  console.log(`Navigating to ${to.name} from ${from.name}`);
  next();
});


export default router;
