<template>
   <h1>Eventos de {{user?.username}}</h1>
   <div class="container-flex">
          <div v-if="eventos.length === 0" class="empty-events">
            <h4>Esta organización aún no tiene eventos</h4>
          </div>
        <div class="card-group">
        <div  class="col" v-for="(evento, index) in eventos" :key="index">
        <event_cards
        :evento="evento"
        :is-org="true"
        :emitReloadEvents="emitReloadEvents"
        >
        </event_cards>
        </div>
    </div>
    <div class="col">
            <div class="d-flex justify-content-end mb-3">
               <router-link :to="`event/create`"><button class="btn btn-primary"><i class="bi bi-plus"></i> Crear Nuevo Evento</button></router-link> 
            </div>
        </div>
    </div>
    
</template>

<script lang="ts">
import { useRouter } from 'vue-router';
import { Event } from '@/models/Event';
import { onMounted, ref } from 'vue';
import {EventService} from '../../services/event.service'
import event_cards from '@/components/event-card.component.vue'
import store from '@/store';
import {User} from '@/models/User'
export  default {
    name: 'organization-home',
    components:{
      event_cards
    },
    setup(){
      const router = useRouter();
      const eventos = ref([] as Array<Event>);
      const user = ref<User | null>(store.state.user);
    const loadEvents = async () => {
      try {
        console.log(store.state.isAuthenticated)
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

      const emitReloadEvents = () => {
      loadEvents();
    };

        const deleteEvent = async(id: number)=>{
            await EventService.prototype.deleteEvent(id),
            await loadEvents()
        }
        

        return{
            eventos,
            router,
            deleteEvent,
            user,
            emitReloadEvents
        }
    },


    
}


</script>


<style scoped>


  .container-flex{
    margin-top: 10%;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    
    align-items: center;
  }

    p, h1{
  text-align: left;
  font-family: 'Franklin Gothic', 'Arial Narrow', Arial, sans-serif;
  margin-top: 1%;
}

.empty-events {
  display: flex;
  justify-content: center; 
  align-items: center; 
  height: 50vh; 
}

.btn-primary {
      padding: 0.75rem 1.5rem;
      background-color: var(--main-bg-org); 
      color: #fff; 
      border: none; 
      border-radius: 9px; 
      font-size: 1rem;
      cursor: pointer;
      transition: background-color 0.4s ease-in-out;
      margin-bottom: 1%;
    }


    .btn-primary:hover {
      background-color: var(--main-bg-dark);
    }

.col{
  margin-top: 4%;
}

</style>