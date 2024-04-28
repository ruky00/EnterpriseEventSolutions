import { createRouter, createWebHistory } from 'vue-router';
import { authGuard } from './guards/authguard';
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
      component: ()=>import('../src/views/company-dashboard.view.vue'),
      meta: { requiresAuth: true, roles:['ORGANIZATION']},
    }, 
    {
      path:"/user",
      name: 'user-landing',
      component: ()=>import('../src/views/user-landing-page.view.vue'),
      meta: { requiresAuth: true, roles:['CLIENT','ORGANIZATION','ADMIN'] },
      beforeEnter: authGuard,
      children: [
        {
          path: 'admin',
          name: 'admin-home',
          component: ()=>import('../src/views/user-landing-page.view.vue'),
          meta: { requiresAuth: true, roles: ['ADMIN'] }
        },
        {
          path: 'client',
          name: 'client-home',
          component: ()=>import('../src/views/client/client-home.view.vue'),
          meta: { requiresAuth: true, roles: ['CLIENT'] }
        },
        {
          path: 'organization',
          name: 'organization-home',
          component: ()=>import('../src/views/organizer/organizer-home.view.vue'),
          meta: { requiresAuth: true, roles: ['ORGANIZATION']},
          beforeEnter: authGuard,
        },
        {
            path:'event/:id/edit',
            name: 'edit-event',
            component: ()=>import('../src/views/organizer/organizer-edit.view.vue'),
            meta: { requiresAuth: true, roles: ['ORGANIZATION'],
            beforeEnter: authGuard,
          }
        },
        {
          path:'event/:id',
          name: 'event-view',
          component: ()=>import('../src/views/event.view.vue'),
          meta: { requiresAuth: true, roles: ['CLIENT','ORGANIZATION','ADMIN'],
          beforeEnter: authGuard,
        }},
        {
          path:'event/create',
            name: 'create-event',
            component: ()=>import('../src/views/organizer/organizer-create.view.vue'),
            meta: { requiresAuth: true, roles: ['ORGANIZATION'],
            beforeEnter: authGuard,
          
        }
      },
      {
        path:':id',
        name:'user-info',
        component:()=>import('../src/views/user-info.view.vue'),
        meta: { requiresAuth: true, roles:['CLIENT','ORGANIZATION','ADMIN'],
        beforeEnter: authGuard,
      }
      }
      ]
    },

    
    //{
     //   path:'/:pathMatch(.*)*',
     //   component: () => import('')
    // },
  ],
  
})


export default routes;