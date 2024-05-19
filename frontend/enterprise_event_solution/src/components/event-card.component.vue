<template>
   <template v-if="isOrg">
    
      <div class="card  text-center" style="width: 28rem; height: 10rem;">
     
        <div class="card-body">
          <h5 class="card-title">{{ evento?.name }}</h5>
          <p class="card-text">{{truncatedDescription(evento?.description!)}}</p>
          <div class="row">
            <button class="btn btn-sm btn-danger me-2" @click="deleteEvent(evento?.id!)">
              <i class="bi bi-trash"></i>
            </button>
           
                <router-link :to="{ name: 'edit-event', params: { id: evento?.id }}" class="btn btn-sm btn-primary">
                    <i class="bi bi-gear"></i>
              </router-link> 

          </div>
      </div>
      </div>
   </template>
   <template v-else>
  
        <router-link :to="{ name: 'event-view', params: { id: evento?.id }}" style="text-decoration: none">   
      <div class="card not-org text-center" style="width: 28rem; height: 10rem;">
     
        <div class="card-body">
          <h5 class="card-title">{{ evento?.name }}</h5>
          <p class="card-text">{{truncatedDescription(evento?.description!)}}</p>
         
      </div>
      </div>
    </router-link>  
   </template>
    
 
</template>


<script lang="ts">
import {EventService} from "../services/event.service";
import { useRouter } from 'vue-router';
import { Event } from '../models/Event';
import { computed, ref } from 'vue';
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
      
        const truncatedDescription = computed(() => (description: string) => {
          if (description && description.length > 80) {
            return description.substring(0, 80) + '...';
          }
          return description;
        });  

        return{
            eventos,
            router,
            deleteEvent,
            truncatedDescription

        }
    },


    
}
</script>


<style scoped>

    p, h1{
      text-align: left;
      font-family: 'Franklin Gothic', 'Arial Narrow', Arial, sans-serif;
      margin-top: 1%;
    }

    h5{
      font-family: 'Franklin Gothic', 'Arial Narrow', Arial, sans-serif;
    }

   .card-body{
      display: flex;
      align-items: center;
      flex-wrap: nowrap;
      flex-direction: column;
      justify-content: space-between;
    }

    .card {
      transition: transform 0.5s ease-in-out; 
      border-style: solid;
      border-color:var(--main-bg-dark);
    }

    .not-org:hover{
      transform: scale(1.1); 
      border-color:var(--main-bg-org);
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