<template>
    <div class="container">
      <!-- Generar dinámicamente las cajas según el número especificado -->
    <router-link :to="{ name: 'user-info', params: { id: organizer.id }}" v-for="(organizer, index) in organizers.slice(0,8)" :key="index" class="box" :style="{ backgroundImage: 'url(' + organizer.image + ')' }">
    </router-link>
    </div>
  </template>
  
  <script lang="ts">
import { User } from '@/models/User';
import { UserService } from '@/services/user.service';
import { onMounted, ref } from 'vue';

  export default {
    name: "client-home",
    setup() {
      const organizers = ref([] as User[])
    

     const getOrganizers = async()=>{
        const response =  await UserService.prototype.getOrganizers();
        organizers.value = response;
     }

  
      onMounted(getOrganizers);
  
      return {
        organizers,
        getOrganizers
      }
    }
  }
  </script>
  
<style scoped>


.container {
  display: flex;
  flex-wrap: wrap;
  width: 100%;
  height: 100vh; 
  padding-left: 0px;
  margin: 0px;/* Ajusta la altura como desees */
}

.box {
  flex-grow: 1; /* Hace que las cajas ocupen todo el ancho disponible */
  height: 100%; /* Altura de cada caja */
  margin: 0; /* Elimina el margen entre las cajas */
  transition: all 0.5s ease;
  opacity: 1;
  background-repeat: no-repeat;
  background-size: cover;/* Transición suave al hacer hover */
}

/* Cambia el tamaño y la opacidad al hacer hover */
.box:hover {
  transform: scale(1.2); /* Aumenta el tamaño al hacer hover */
  opacity: 200; /* Reduce la opacidad al hacer hover */
}
</style>