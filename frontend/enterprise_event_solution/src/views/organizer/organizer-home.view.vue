<template>
   <h1>Eventos de {{user?.username}}</h1>
    <div class="row">
      <div  class="col-12" v-for="(evento, index) in eventos" :key="index">
        <event_cards
        :evento="evento"
        :is-org="true"
        >
        </event_cards>
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
            deleteEvent,
            user
        }
    },


    
}


</script>


<style scoped>
  h1{
    margin-top: 2%;
    margin-bottom: 1%;
    display: flex;
    font-size: 2em;
    font-family: 'Franklin Gothic', 'Arial Narrow', Arial, sans-serif;
  }
</style>