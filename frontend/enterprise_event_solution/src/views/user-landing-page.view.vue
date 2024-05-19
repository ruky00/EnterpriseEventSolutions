<template>
  <div class="container-fluid px-0">
    <div class="row">
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
import UserNavbar from '../components/user-nav-bar.component.vue'

export default {
  components: {
    UserNavbar
  },
  data() {
    return {
      isNavbarTop: false
    };
  },
  computed: {
    contentClass() {
      return {
        'col-lg-10': !this.isNavbarTop,
        'col-md-12': !this.isNavbarTop,
        'col-sm-12': !this.isNavbarTop,
        'col-12': !this.isNavbarTop,
        'sticky-content': !this.isNavbarTop // Aplicar clase para el contenido pegajoso cuando la barra de navegación está en la parte superior
      };
    }
  },
  mounted() {
    this.isNavbarTop = window.innerWidth < 576; // Cambia a true si el ancho de la ventana es menor que 576px
  }
};
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
