<template>
  <div>
    <!-- Barra de navegación superior en pantallas pequeñas -->
    <nav class="navbar navbar-dark bg-dark d-lg-none d-sm-block" style="padding: 0; z-index: 1000">
      <div class="container-fluid">
        
        <h1 class="navbar-brand" href="#">Hi, {{ user?.username }}</h1>
        <!-- Solo mostrar el botón de hamburguesa en pantallas pequeñas -->
        <button
          v-if="isSmallScreen"
          class="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarNav"
          aria-controls="navbarNav"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
          <ul class="navbar-nav">
            <li class="nav-item" v-if="user?.role == 'CLIENT'">
              <router-link :to="{ name: 'client-tickets' }" class="nav-link" exact-active-class="active-link">
                Mis Entradas
              </router-link>
            </li>
            <li class="nav-item" v-if="user?.role == 'CLIENT'">
              <ul class="collapse" id="submenu1">
                <li>
                  <router-link :to="{ name: 'client-home' }" class="nav-link" exact-active-class="active-link">
                    Empresas
                  </router-link>
                </li>
              </ul>
            </li>
            <li class="nav-item" v-if="user?.role == 'ADMIN'">
              <router-link :to="{ name: 'user-table' }" class="nav-link" exact-active-class="active-link">
                Usuarios
              </router-link>
            </li>
            <li class="nav-item" v-if="user?.role == 'ADMIN'">
              <router-link :to="{ name: 'create-org' }" class="nav-link" exact-active-class="active-link">
                Añadir Usuarios
              </router-link>
            </li>
            <li class="nav-item" v-if="user?.role == 'ORGANIZATION'">
              <router-link :to="{ name: 'organization-home' }" class="nav-link" exact-active-class="active-link">
                Mis eventos
              </router-link>
            </li>
            <li class="nav-item" v-if="user?.role == 'ORGANIZATION'">
              <router-link :to="{ name: 'organization-stats' }" class="nav-link" exact-active-class="active-link">
                Estadísticas
              </router-link>
            </li>
          </ul>
          <hr>
          <div class="dropdown pb-4">
            <a
              href="#"
              class="d-flex align-items-center text-white text-decoration-none dropdown-toggle"
              id="dropdownUser1"
              data-bs-toggle="dropdown"
              aria-expanded="false"
            >
              <img :src="getUserImage()" alt="hugenerd" width="30" height="30" class="rounded-circle">
              <span class="mx-1">{{ user?.username }}</span>
            </a>
            <ul class="dropdown-menu dropdown-menu-dark text-small shadow" aria-labelledby="dropdownUser1" style="z-index: 1000;">
              <li>
                <router-link :to="{ name: 'user-info', params: { id: 'me' } }" class="dropdown-item">
                  Perfil
                </router-link>
              </li>
              <li>
                <hr class="dropdown-divider">
              </li>
              <li><a class="dropdown-item" @click="logout">Sign out</a></li>
            </ul>
          </div>
        </div>
      </div>
    </nav>

    <!-- Barra de navegación lateral en pantallas grandes -->
    <div class="d-none d-lg-block">
      <div class="container-fluid">
        <div class="row flex-nowrap">
          <div class="col-auto col-md-12 col-xl-12 px-sm-2 px-0">
            <div class="d-flex flex-column align-items-center align-items-sm-start px-4 pt-2 text-white min-vh-100">
              <span class="fs-3 d-none d-sm-inline">Hi, {{ user?.username }}</span>
              <ul class="nav nav-pills flex-column mb-sm-auto mb-0 align-items-sm-start" id="menu">
                <li v-if="user?.role == 'CLIENT'">
                  <router-link :to="{ name: 'client-tickets' }" class="nav-link px-0" exact-active-class="active-link">
                    <i class="fs-4 bi-ticket-detailed"><span class="ms-1 d-none d-sm-inline">Mis Entradas</span></i>
                  </router-link>
                </li>
                <li v-if="user?.role == 'CLIENT'">
                  <a href="#submenu1" data-bs-toggle="collapse" class="nav-link px-0">
                    <i class="fs-4 bi-newspaper"></i> <span class="ms-1 d-none d-sm-inline">Novedades</span>
                  </a>
                  <ul class="collapse show nav flex-column ms-2" id="submenu1" data-bs-parent="#menu">
                    <li class="w-100">
                      <router-link :to="{ name: 'client-home' }" class="nav-link px-0" exact-active-class="active-link">
                        <i class="bi bi-buildings"><span class="d-none d-sm-inline"> Empresas</span></i>
                      </router-link>
                    </li>
                  </ul>
                </li>
                <li v-if="user?.role == 'ADMIN'">
                  <router-link :to="{ name: 'user-table' }" class="nav-link px-0 align-middle" exact-active-class="active-link">
                    <i class="fs-5 bi-people">
                      <span class="ms-1 d-none d-sm-inline">Usuarios</span></i>
                  </router-link>
                </li>
                <li v-if="user?.role == 'ADMIN'">
                  <router-link :to="{ name: 'create-org' }" class="nav-link px-0 align-middle" exact-active-class="active-link">
                    <i class="fs-5 bi-plus-circle"><span class=" d-sm-inline">Añadir Usuarios</span></i>
                  </router-link>
                </li>
                <li v-if="user?.role == 'ORGANIZATION'">
                  <router-link :to="{ name: 'organization-home' }"  class="nav-link px-0 align-middle" exact-active-class="active-link">
                    <i class="fs-4 bi-calendar-event"><span class="ms-1 d-none d-sm-inline">Mis eventos</span></i>
                  </router-link>
                </li>
                <li v-if="user?.role == 'ORGANIZATION'">
                  <router-link :to="{ name: 'organization-stats' }"  class="nav-link px-0 align-middle" exact-active-class="active-link">
                    <i class="fs-4 bi-graph-up"><span class="ms-1 d-none d-sm-inline">Estadísticas</span></i>
                  </router-link>
                </li>
              </ul>
              <hr>
              <div class="dropdown pb-4">
                <a href="#" class="d-flex align-items-center text-white text-decoration-none dropdown-toggle" id="dropdownUser1" data-bs-toggle="dropdown" aria-expanded="false">
                  <img :src="getUserImage()" alt="hugenerd" width="30" height="30" class="rounded-circle">
                  <span class="d-none d-sm-inline mx-1">{{ user?.username }}</span>
                </a>
                <ul class="dropdown-menu dropdown-menu-dark text-small shadow" aria-labelledby="dropdownUser1">
                  <li>
                    <router-link :to="{ name: 'user-info', params: { id: 'me' } }" class="dropdown-item">
                      Perfil
                    </router-link>
                  </li>
                  <li>
                    <hr class="dropdown-divider">
                  </li>
                  <li><a class="dropdown-item" @click="logout">Sign out</a></li>
                </ul>
              </div>
            </div>
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
import { onBeforeUnmount, onMounted, ref } from 'vue';
import { authService } from '@/services/auth.service';

export default {
  name: 'user-navbar',

  setup() {
    const router = useRouter();
    const store = useStore();
    const user = ref<User | null>(store.state.user);
    const isSmallScreen = ref(window.innerWidth <= 992);

    const getUserImage = () => {
      if (user.value && user.value!.image) {
        return user.value!.image;
      } else {
        return 'https://github.com/mdo.png'; // URL de imagen predeterminada en caso de que el Blob no esté presente
      }
    };

    const logout = async () => {
      try {
        await authService.prototype.logout();
        store.dispatch('logout');
        router.push('/');
      } catch (error) {
        console.log('Error al cerrar sesión');
      }
    };

    const checkScreenSize = () => {
      isSmallScreen.value = window.innerWidth <= 992;
    };

    onMounted(() => {
      checkScreenSize(); // Llama a checkScreenSize() en el montaje inicial
      window.addEventListener('resize', checkScreenSize); // Agrega un observador de cambio de tamaño de ventana
    });

    onBeforeUnmount(() => {
      window.removeEventListener('resize', checkScreenSize); // Elimina el observador de cambio de tamaño de ventana al desmontar el componente
    });

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
      getUserImage,
      logout,
      isSmallScreen,
      
    };
  }

}
</script>

<style scoped>
.col-auto {
  background-color: var(--main-bg-dark);
  max-width: 100%;
  overflow: auto;
}

.container-fluid {
  background-color:  var(--main-bg-dark);
  max-width: 100%;
  position: -webkit-sticky;
  position: sticky;
  top: 0;
}

.nav-link {
  display: flex;
  align-items: center;
  padding: 8px 16px;
  text-decoration: none;
  color: var(--main-bg-ultra);
}

.nav-link i {
  margin-right: 8px;
}

.nav-link span {
  flex-grow: 1;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;
  font-size: smaller;
}

.dropdown-menu {
  padding: 0;
}

i:hover {
  color: var(--main-bg-org);
}

.dropdown-item {
  padding: 8px 16px;
  text-decoration: none;
  color: var(--main-bg-ultra);
}

span {
  font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;
}

.dropdown-item:hover {
  background-color: var(--main-bg-org);
}

.nav li {
  border-bottom: 0.5px solid var(--main-bg-ultra);
  margin-bottom: 10px;
  padding-bottom: 8px;
  list-style-type: none;
  transition: all 0.3s ease; /* Transición suave */
}

.nav li:last-child {
  border-bottom: none;
  margin-bottom: 0;
  padding-bottom: 0;
}

.nav li .collapse {
  overflow: hidden;
  transition: height 0.56s ease; /* Transición suave */
}

.nav li .show .collapse {
  height: auto; /* Altura automática para la transición suave */
}

.active-link i {
  color: var(--main-bg-org);
  font-weight: bold;
}

.navbar-nav,
.nav {
  list-style-type: none;
  padding-left: 0;
  margin-bottom: 0;
}

p, h1 {
  text-align: left;
  font-family: 'Franklin Gothic', 'Arial Narrow', Arial, sans-serif;
  margin-top: 1%;
}

</style>

