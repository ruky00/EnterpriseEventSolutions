import { createRouter, createWebHistory } from 'vue-router';
const routes =createRouter({
   history: createWebHistory(),
   routes: [
    {
      path: '/',
      name: 'home',
      component: ()=> import("../src/views/mainPage.view.vue")
    },
    //{
     //   path:'/:pathMatch(.*)*',
     //   component: () => import('')
    // },
  ],
})

export default routes;