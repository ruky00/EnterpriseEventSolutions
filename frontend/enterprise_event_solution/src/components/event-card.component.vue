<template>
   <template v-if="isOrg">
    <div class="row">
      <div class="col-12">
        <div class="card org">
          <img class="card-img">
          <div class="card-img-overlay">
            <h5 class="card-title">{{ evento?.name }}</h5>
            <p class="card-text">{{ evento?.description }}</p>
            <div class="d-flex justify-content-end align-items-end">
            
            <button v-if="isOrg" class="btn btn-sm btn-danger me-2" @click="deleteEvent(evento?.id!)">
              <i class="bi bi-trash"></i>
            </button>
           
                <router-link v-if="isOrg" :to="{ name: 'edit-event', params: { id: evento?.id }}" class="btn btn-sm btn-primary">
                    <i class="bi bi-gear"></i>
            </router-link>
            
          </div>  
          </div>
        </div>
      </div>

    </div>
    <div v-if="isOrg" class="row">
        <div class="col-12">
            <div class="d-flex justify-content-end mb-3">
               <router-link :to="`event/create`"> <i class="bi bi-plus"></i> Crear Nuevo Evento</router-link> 
            </div>
        </div>
    </div>
   </template>
   <template v-else>
    <div class="row">
     <router-link :to="{ name: 'event-view', params: { id: evento?.id }}"> 
      <div class="col-12">
        <div class="card client">
          <img class="card-img">
          <div class="card-img-overlay">
            <h5 class="card-title">{{ evento?.name }}</h5>
            <p class="card-text">{{ evento?.description }}</p>
            <div class="d-flex justify-content-end align-items-end">
          </div>  
          </div>
        </div>
      </div>
    </router-link>  
    </div>
   </template>
    
 
</template>


<script lang="ts">
import {EventService} from "../services/event.service";
import { useRouter } from 'vue-router';
import { Event } from '../models/Event';
import { ref } from 'vue';
export  default {
    name: 'event_cards',
    props:{
      evento: Object as ()=> Event,
      isOrg: Boolean,
    },
    setup(){
      const router = useRouter();
      const eventos = ref ({} as Event)
        const deleteEvent = async(id: number)=>{
            await EventService.prototype.deleteEvent(id)
        }
      
        return{
            eventos,
            router,
            deleteEvent,

        }
    },


    
}
</script>


<style scoped>
    .card{
      height: 105px;
      margin-top: 40px;
      margin-right: 30px;
      background-repeat: no-repeat;
      background-size: cover;
      font-family: 'Franklin Gothic', 'Arial Narrow', Arial, sans-serif;
      transition: all 0.7s ease;
      }

    .card .client:hover{
      transform: scale(1.06);
      transition: all 0.7s ease;
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