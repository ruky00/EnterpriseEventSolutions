<template>
   
    <div class="row">
        
      <div class="col-12" v-for="(evento, index) in eventos" :key="index">
        <div class="card">
          <img class="card-img"  alt="Card image">
          <div class="card-img-overlay">
            <h5 class="card-title">{{ evento.name }}</h5>
            <p class="card-text">{{ evento.description }}</p>
            <div class="d-flex justify-content-end align-items-end">
            
            <button class="btn btn-sm btn-danger me-2" @click="deleteEvent(evento.id!)">
              <i class="bi bi-trash"></i>
            </button>
           
                <router-link :to="{ name: 'edit-event', params: { id: evento.id }}" class="btn btn-sm btn-primary">
                    <i class="bi bi-gear"></i>
            </router-link>
            
          </div>  
          </div>
        </div>
      </div>
    </div>
    <div class="row">
        <div class="col-12">
            <div class="d-flex justify-content-end mb-3">
               <router-link :to="`event/create`"> <i class="bi bi-plus"></i> Crear Nuevo Evento</router-link> 
            </div>
        </div>
    </div>
 
</template>

<script lang="ts">
import { useRouter } from 'vue-router';
import { Event } from '@/models/Event';
import { onMounted, ref } from 'vue';
import {EventService} from '../../services/event.service'
export  default {
    name: 'organization-home',
    setup(){
      const router = useRouter();
      const eventos = ref([] as Array<Event>);

    const loadEvents = async () => {
      try {
        const eventData = await EventService.prototype.getMyEvents();
        if (eventData) {
          eventos.value = eventData;
        } else {
          console.error('No se pudieron obtener los eventos');
        }
      } catch (error) {
        console.error('Error al obtener los eventos:', error);
      }
    };
      onMounted(loadEvents);

        const deleteEvent = async(id: number)=>{
            await EventService.prototype.deleteEvent(id),
            await loadEvents()
        }
        

        return{
            eventos,
            router,
            deleteEvent
        }
    },


    
}


</script>


<style scoped>
    .card{
        height: 105px;
        margin-top: 40px;
        margin-right: 30px;
    }

    p{
        margin: 0px;
    }
    .btn{
        width: 40px;
        height: 33px;

    }
    .buttons-container {
        position: absolute;
        bottom: 0;
        right: 0;
        margin: 10px;
    }

    @media screen and (max-width: 576px) {
    /* Ajuste de altura para pantallas pequeñas */
    .card {
      height: 130px; /* Cambia la altura para pantallas menores o iguales a 576px */
    }
  }

  @media screen and (min-width: 577px) and (max-width: 768px) {
    /* Ajuste de altura para pantallas medianas */
    .card {
      height: 150px; /* Cambia la altura para pantallas mayores a 576px y menores o iguales a 768px */
    }
  }
</style>