<template>
    <div class="container-fluid">
        <div class="row flex-nowrap">
            <div class="col-auto col-md-12 col-xl-12 px-sm-2 px-0 ">
                <div class="d-flex flex-column align-items-center align-items-sm-start px-4 pt-2 text-white min-vh-100">
                   
                        <span class="fs-5 d-none d-sm-inline">Wellcome Back {{user?.username}}!</span>
                    
                    <ul class="nav nav-pills flex-column mb-sm-auto mb-0 align-items-center align-items-sm-start" id="menu">
                        <li v-if="user?.role == 'CLIENT'">
                            <a href="#submenu1" data-bs-toggle="collapse" class="nav-link px-0 align-middle">
                                <i class="fs-4 bi-speedometer2"></i> <span class="ms-1 d-none d-sm-inline">Novedades</span> </a>
                            <ul class="collapse show nav flex-column ms-1" id="submenu1" data-bs-parent="#menu">
                                <li class="w-100">
                                    <a href="#" class="nav-link px-0"> <span class="d-none d-sm-inline">Empresas</span></a>
                                </li>
                                <li>
                                    <a href="#" class="nav-link px-0"> <span class="d-none d-sm-inline">Eventos</span></a>
                                </li>
                            </ul>
                        </li>
                        <li v-if="user?.role == 'CLIENT'">
                            <a href="#" class="nav-link px-0 align-middle">
                                <i class="fs-4 bi-table"></i> <span class="ms-1 d-none d-sm-inline">Mis Entradas</span></a>
                        </li>
                        <li v-if="user?.role == 'ADMIN'">
                            <a href="#submenu1" data-bs-toggle="collapse" class="nav-link px-0 align-middle">
                                <i class="fs-4 bi-speedometer2"></i> <span class="ms-1 d-none d-sm-inline">Usuarios</span> </a>
                        </li>
                        <li v-if="user?.role == 'ADMIN'">
                            <a href="#" class="nav-link px-0 align-middle">
                                <i class="fs-4 bi-table"></i> <span class="ms-1 d-none d-sm-inline">Eventos</span></a>
                        </li>
                        <li v-if="user?.role == 'ORGANIZATION'">
                            <router-link :to="{ name:'organization-home'} " data-bs-toggle="collapse" class="nav-link px-0 align-middle">
                                <i class="fs-4 bi-speedometer2"></i> <span class="ms-1 d-none d-sm-inline">Mis eventos</span> 
                            </router-link>
                        </li>
                        <li v-if="user?.role == 'ORGANIZATION'">
                            <a href="#" class="nav-link px-0 align-middle">
                                <i class="fs-4 bi-table"></i> <span class="ms-1 d-none d-sm-inline">White List</span></a>
                        </li>
                       
                    </ul>
                    <hr>
                    <div class="dropdown pb-4">
                        <a href="#" class="d-flex align-items-center text-white text-decoration-none dropdown-toggle" id="dropdownUser1" data-bs-toggle="dropdown" aria-expanded="false">
                            <img :src="getUserImage()" alt="hugenerd" width="30" height="30" class="rounded-circle">
                            <span class="d-none d-sm-inline mx-1">{{user?.username}}</span>
                        </a>
                        <ul class="dropdown-menu dropdown-menu-dark text-small shadow" aria-labelledby="dropdownUser1">
                            <li><a class="dropdown-item" href   ="#">Ajustes</a></li>
                            <li><router-link :to="{name:'user-info', params:{id: 'me'} }" class="dropdown-item" href="#">Perfil</router-link></li>
                            <li>
                                <hr class="dropdown-divider">
                            </li>
                            <li><a class="dropdown-item" href="#">Sign out</a></li>
                        </ul>
                    </div>
                </div>
            </div>
          
        </div>
    </div>
    </template>
    
    <script lang="ts">
    import { useRouter } from 'vue-router';
    import { useStore } from 'vuex';
    import { User } from '@/models/User';
    import { ref } from 'vue';
    
    export default {
      name: 'user-navbar',
    
      setup() {
        const router = useRouter();
        const store = useStore();
        const user = ref<User | null>(store.state.user);
    
        const getUserImage = () => {
          if (user.value!.image) {
            return user.value!.image;
          } else {
            return 'https://github.com/mdo.png'; // URL de imagen predeterminada en caso de que el Blob no esté presente
          }
        };
        
        // Observa cambios en el estado del usuario y actualiza la variable local 'user'
        // cada vez que cambie el estado del store
        store.watch(
          () => store.state.user,
          (newValue: User) => {
            user.value = newValue;
          }
        );
    
        return {
          router,
          store,
          user,
          getUserImage
        };
      }
    };
    </script>
    
    <style scoped>
      .col-auto{
        background-color: var(--main-bg-dark);
        max-width:200px;
        overflow:auto;
        
      }

      .container-fluid{
        position: -webkit-sticky;
  position: sticky;
  top: 0;
      }
      span{
        color: var(--main-bg-ultra);
        font:var(--font-family-main);
        font-size: smaller;
      }
    
      i{
        size: 4px;
      }
    
    
    </style>