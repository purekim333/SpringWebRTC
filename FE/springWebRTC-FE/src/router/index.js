import { createRouter, createWebHistory } from 'vue-router';

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
