<template>
  <div class="container-fluid px-0">
    <div class="row gx-0">
      <!-- Barra de navegación lateral en pantallas grandes -->
      <div :class="[isNavbarTop ? 'navbar-top' : 'navbar-side', 'col-lg-2', 'd-none', 'd-sm-block', 'bg-dark', 'p-0']">
        <user-navbar />
      </div>

      <!-- Contenido principal -->
      <div :class="contentClass">
        <router-view />
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, computed, onMounted } from 'vue';
import UserNavbar from '../components/user-nav-bar.component.vue';

export default defineComponent({
  components: {
    UserNavbar,
  },
  setup() {
    const isNavbarTop = ref(false);

    const contentClass = computed(() => ({
      'col-lg-10': !isNavbarTop.value,
      'col-md-12': !isNavbarTop.value,
      'col-sm-12': !isNavbarTop.value,
      'col-12': !isNavbarTop.value,
      'sticky-content': !isNavbarTop.value,
    }));

    onMounted(() => {
      isNavbarTop.value = window.innerWidth < 576; 
    });

    return {
      isNavbarTop,
      contentClass,
    };
  },
});
</script>

<style scoped>
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
</style>
