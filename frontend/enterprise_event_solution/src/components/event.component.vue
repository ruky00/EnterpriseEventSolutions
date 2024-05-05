<template>
  <div class="container-flex">
      <div class="row">
          <div class="col-12 nombre">
            <h1>{{ event.name }}</h1>
          </div>
      </div>
      <div class="row">
          <div class="col-12 descripcion">
             <p id="descripcion">{{ event.description }}</p> 
          </div>
      </div>
      <div class="row">
         <div class="col-2 fecha"><p id="fecha">Fecha: {{formattedDate}}</p></div>
         <div class="col-2 capacidad"><p>Capacidad: {{ event.current_capacity }}/{{ event.max_capacity }}</p></div>
      </div>
      <div v-if="haveTicket()" class="row">
          <div class="col-12">
            <button class="full-width-btn" @click="buyTicket">Inscribirse</button>
          </div>
      </div>
      <div v-else class="row">
          <div class="col-12">
            <div class="card-body">
              <h5 class="card-title">Ya estás registrado en este evento</h5>
              <p class="card-text">¡Esperamos verte allí!</p>
            </div>     
          </div>
      </div>
  </div>
  
  </template>
    
    <script lang="ts">
    import { EventService } from '../services/event.service';
    import { useRouter } from 'vue-router';
    import { Event } from '../models/Event';
    import { onMounted, ref } from 'vue';
import { Ticket } from '@/models/Ticket';
import { UserService } from '@/services/user.service';
    
    export default {
      name: 'event_component',
      setup() {
        const router = useRouter();
        const event = ref({} as Event);
        const eventId = router.currentRoute.value.params['id'];
        const ticket = ref({} as Ticket);
        const tickets = ref([] as Ticket[]) 
        const setEvent = async () => {
          try {
            const eventData = await EventService.prototype.getEventById(Number(eventId));
            event.value = eventData;
            
            if (!eventData) throw new Error('No se ha encontrado el evento');
          } catch (error) {
            console.log('Error: ', error);
          }
         await getAllTickets();
        };
        const buyTicket = async()=>{
          try{
            const ticketData = await EventService.prototype.buyTicket(Number(eventId));
            ticket.value = ticketData;
           
          }catch(error){
            console.log('Error: ', error);
          }
        }
        const getAllTickets= async() =>{
          try{
            const ticketsData = await UserService.prototype.getTickets();
            tickets.value = ticketsData
          }catch(error){
            console.log('Error: ', error);
          }
          
        }
        const haveTicket = () => {
            for (let i = 0; i < tickets.value.length; i++) {
              const ticket = tickets.value[i];
              if (ticket.event?.id === Number(eventId)) {

                return false; // Si encuentra un ticket para el evento actual, retorna true
              }
            }
          
            return true; // Si no se encuentra ningún ticket para el evento actual, retorna false
          };


        const dateFormatter = new Intl.DateTimeFormat('es-ES',{ year: 'numeric', month: 'long', day: 'numeric' });
        
  // Formatea la fecha utilizando el objeto dateFormatter
        const formattedDate = dateFormatter.format(event.value.date);
        onMounted(setEvent);
        
        return {
          router,
          event,
          eventId,
          haveTicket,
          buyTicket,
          dateFormatter,
          formattedDate,
          ticket,
          getAllTickets
          
        };
      },
    };
    </script>
    
    <style scoped>
    .container-flex{
      height: 40vh;
      background: #f5d0d0b4;
      margin-top:10px;
      border-radius: 20px;
    }
    .col-12{
      padding-bottom: 30px;
    }
    #descripcion{
      text-align: left;
      text-indent: 60px;
     
    }
    p{
  
      font-family: 'Franklin Gothic', 'Arial Narrow', Arial, sans-serif;
    }
    h1{
      font-family: 'Franklin Gothic', 'Arial Narrow', Arial, sans-serif;
    }
    .full-width-btn {
    width: 80%; /* Hace que el botón ocupe todo el ancho del contenedor */
    background-color: var(--main-bg-org); /* Color de fondo azul */
    color: #fff; /* Color del texto blanco */
    border: none; /* Elimina el borde */
    padding: 10px 15px; /* Añade un poco de espacio interior al botón */
    border-radius: 5px; /* Bordes redondeados */
    cursor: pointer; /* Cambia el cursor al pasar por encima del botón */
    }
  
   .full-width-btn:hover{
    background-color: var(--main-bg-org-hover);
    }
    .card-body{
      margin-left: 11%;
      background-color: var(--main-bg-dark);
      width: 80%;
      border-radius: 50px;
  
    }
    .card-body p{
      color: white;
  
    }
    .card-body h5{
    
      color: white;
    }
  
    </style>