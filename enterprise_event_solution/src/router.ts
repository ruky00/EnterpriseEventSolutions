import { createRouter, createWebHistory } from 'vue-router';
const routes =createRouter({
   history: createWebHistory(),
   routes: [
    {
      path: '/',
      name: 'home',
      component: ()=> import("../src/views/mainPage.view.vue")
    },
    {
        path:'/login',
        name:'auth',
        component: ()=>import("../src/views/auth.view.vue")
    },
    {
      path:"/company",
      name: 'dashboard',
      component: ()=>import('../src/views/company-dashboard.view.vue')
    }
    //{
     //   path:'/:pathMatch(.*)*',
     //   component: () => import('')
    // },
  ],
})

export default routes;