import { createRouter,  createWebHashHistory } from 'vue-router';
import { authGuard } from './guards/authguard';
const routes =createRouter({
   history: createWebHashHistory(),
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
        path:'/login',
        name:'auth',
        component: ()=>import("../src/views/auth.view.vue")
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
          component: ()=>import('../src/views/admin/admin-home.view.vue'),
          meta: { requiresAuth: true, roles: ['ADMIN'] },
          beforeEnter: authGuard,
          
        },
        {
          path: 'admin/createOrganization',
          name: 'create-org',
          component: ()=>import('../src/views/admin/create-org.view.vue'),
          meta: { requiresAuth: true, roles: ['ADMIN'] },
          beforeEnter: authGuard,
         
        },
        {
          path: 'admin/usersTable',
          name: 'user-table',
          component: ()=>import('../src/views/admin/admin-userTable.view.vue'),
          meta: { requiresAuth: true, roles: ['ADMIN'] },
          beforeEnter: authGuard,
         
        },
        {
          path: 'client',
          name: 'client-home',
          component: ()=>import('../src/views/client/client-home.view.vue'),
          meta: { requiresAuth: true, roles: ['CLIENT'],
          beforeEnter: authGuard,
           }
          
        },
        {
          path: 'client/tickets',
          name: 'client-tickets',
          component: ()=>import('../src/views/client/client-tickets.view.vue'),
          meta: { requiresAuth: true, roles: ['CLIENT'],
          beforeEnter: authGuard,

          }
        },
        {
          path: 'organization',
          name: 'organization-home',
          component: ()=>import('../src/views/organizer/organizer-home.view.vue'),
          meta: { requiresAuth: true, roles: ['ORGANIZATION']},
          beforeEnter: authGuard,
        },
        {
        path: 'organization/stats',
        name: 'organization-stats',
        component: ()=>import('../src/views/organizer/organizer-stats.view.vue'),
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
        props: true 
      }
      },
      {
        path: ':pathMatch(.*)',
        name: 'Error',
        component: ()=>import('../src/views/error.view.vue'),
        props: (route) => {
          const errorCode = route.query.code ? parseInt(route.query.code as string) : 404;
          return { errorCode };
        }
      }

      ]
    },


    {
      path: '/unauthorized',
      name: 'Unauthorized',
      component: () => import('../src/views/error.view.vue'),
      props: { errorCode: 403 } // Pasar el código de error 403 como prop
    },


    {
      path: '/:pathMatch(.*)',
      name: 'Error',
      component: ()=>import('../src/views/error.view.vue'),
      props: { errorCode: 404 }
    }

  ],
  
})


export default routes;