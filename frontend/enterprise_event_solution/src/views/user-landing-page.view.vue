<template>
  <div class="container-fluid px-0">
    <div class="row gx-0">
      <!-- Barra de navegación lateral en pantallas grandes -->
      <div :class="[isNavbarTop ? 'navbar-top' : 'navbar-side', 'col-lg-2', 'd-sm-block', 'bg-dark', 'p-0']">
        <user-navbar />
      </div>

      <!-- Contenido principal -->
      <div :class="contentClass">
        <div class="container">
          <div class="row"> 
            <router-link :to="`/user/${userType}`"><i class="bi bi-arrow-left"></i></router-link>
          </div>
        </div>
        <router-view />
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, computed, onMounted, onBeforeUnmount } from 'vue';
import UserNavbar from '../components/user-nav-bar.component.vue';
import store from '@/store';

export default defineComponent({
  components: {
    UserNavbar,
  },
  setup() {
    const isNavbarTop = ref(false);
    let userType = store.state.userRoles.toLowerCase();

    const contentClass = computed(() => ({
      'col-lg-10': !isNavbarTop.value,
      'col-md-12': !isNavbarTop.value,
      'col-sm-12': !isNavbarTop.value,
      'col-12': !isNavbarTop.value,
      'sticky-content': !isNavbarTop.value, // Aplica la clase 'sticky-content' si la barra de navegación está en la parte superior
    }));

    const handleResize = () => {
      isNavbarTop.value = window.innerWidth <= 992; // Establece isNavbarTop en true si el ancho de la pantalla es menor o igual a 992px
    };

    onMounted(() => {
      handleResize(); // Llama a handleResize() en el montaje inicial
      window.addEventListener('resize', handleResize); // Agrega un observador de cambio de tamaño de ventana
    });

    onBeforeUnmount(() => {
      window.removeEventListener('resize', handleResize); // Elimina el observador de cambio de tamaño de ventana al desmontar el componente
    });

    return {
      isNavbarTop,
      contentClass,
      userType
    };
  },
});
</script>

<style scoped>
@import "../assets/styles.css";
.container-fluid {
  padding: 0; /* Quita el padding superior e inferior */
}

.row {
  margin-right: 0;
  margin-left: 0;
}

/* Estilos adicionales para la barra de navegación lateral */
.navbar-side .user-navbar-container {
  position: relative; /* Restaurar la posición relativa */
}

/* Estilos para la barra de navegación superior */
.navbar-top .user-navbar-container {
  position: static; /* Restaura la posición normal */
  height: auto; /* Altura automática según el contenido */
}

/* Estilos adicionales si es necesario */
.sticky-content {
  position: sticky;
  top: 0;
  height: 100vh;
  overflow-y: auto;
}

.col-lg-10{
  padding-left: 1%;
}
.bi{
  font-size: 2em;
}

.container{
  display: flex;
  padding: 0px;
  margin: 0px;
}
a{
  color: var(--main-bg-org) !important
}

a:hover{
  color: var(--main-bg-org-hover) !important
}
</style>
