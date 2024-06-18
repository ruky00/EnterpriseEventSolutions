<template>
  <h1>{{ event.name }}<i v-if="isPrivate" class="bi bi-lock" data-bs-toggle="tooltip" data-bs-placement="right" title="Este evento requiere contraseña"></i></h1>
  <div class="container-flex">
    <div class="row">
      <div class="col-12 descripcion">
        <p id="descripcion">{{ event.description }}</p>
      </div>
    </div>
    <div class="row">
      <div class="col-2 fecha"><p id="fecha">Fecha: {{ formattedDate }}</p></div>
      <div class="col-2 capacidad"><p>Capacidad: {{ event.current_capacity }}/{{ event.max_capacity }}</p></div>
    </div>
    <div v-if="haveTicket()" class="row">
      <div class="col-2">
        <button v-if="!seleccionado && !complete && isPrivate" class="btn btn-primary" @click="openConfirmationBanner">Inscribirse</button>
        <button v-if="!seleccionado && !complete && !isPrivate" class="btn btn-primary" @click="buyTicket">Inscribirse</button>
        <button v-if="!seleccionado && complete" class="btn btn-primary" type="button" disabled>
          Evento Lleno
        </button>
        <button v-if="seleccionado" class="btn btn-primary" type="button" disabled>
          <span class="spinner-grow spinner-grow-sm" role="status" aria-hidden="true"></span>
          Adquiriendo Entrada...
        </button>
      </div>
    </div>
    <div v-else class="row">
      <div class="col-12">
        <button class="btn btn-primary" disabled>
          <h5 class="card-title">Ya estás registrado en este evento.</h5>
          <h5 class="card-text">¡Esperamos verte allí!</h5>
        </button>
      </div>
    </div>
  </div>
  <div v-if="showConfirmationBanner" class="overlay">
    <div class="confirmation-banner">
      <p>Introduzca la contraseña</p>
      <input v-model="password" type="password" placeholder="Contraseña" />
      <button @click="confirmBuyTicket">Enviar</button>
      <button @click="cancelDeleteUser">Cancelar</button>
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
    const tickets = ref([] as Ticket[]);
    const password = ref('');
    const seleccionado = ref(false);
    const isPrivate = ref(false);
    const showConfirmationBanner = ref(false);

    const setEvent = async () => {
      try {
        const eventData = await EventService.prototype.getEventById(Number(eventId));
        event.value = eventData;
        isPrivate.value = event.value.privateEvent;
        console.log(event.value);
        if (!eventData) throw new Error('No se ha encontrado el evento');
      } catch (error) {
        console.log('Error: ', error);
      }
      await getAllTickets();
    };

    const complete = ref(false);

    const openConfirmationBanner = () => {
      showConfirmationBanner.value = true;
    };

    const confirmBuyTicket = async () => {
      showConfirmationBanner.value = false;
      await buyTicket();
    };

    const buyTicket = async () => {
      seleccionado.value = true;
      try {
        const ticketData = await EventService.prototype.buyTicket(Number(eventId), password.value);
        ticket.value = ticketData;
        await getAllTickets();
      } catch (error) {
        console.log('Error: ', error);
      } finally {
        seleccionado.value = false;
        await setEvent();
      }
    };

    const getAllTickets = async () => {
      try {
        const ticketsData = await UserService.prototype.getTickets();
        tickets.value = ticketsData;
      } catch (error) {
        console.log('Error: ', error);
      }
    };

    const haveTicket = () => {
      for (let i = 0; i < tickets.value.length; i++) {
        const ticket = tickets.value[i];
        if (ticket.event?.id === Number(eventId)) {
          return false;
        }
      }
      return true;
    };

    const cancelDeleteUser = () => {
      showConfirmationBanner.value = false;
    };

    const dateFormatter = new Intl.DateTimeFormat('es-ES', { year: 'numeric', month: 'long', day: 'numeric' });
    const formattedDate = ref('');
    onMounted(async () => {
      await setEvent();
      formattedDate.value = dateFormatter.format(new Date(event.value.date));
      complete.value = event.value.current_capacity >= event.value.max_capacity;
    });

    return {
      router,
      event,
      eventId,
      haveTicket,
      buyTicket,
      openConfirmationBanner,
      confirmBuyTicket,
      cancelDeleteUser,
      password,
      dateFormatter,
      formattedDate,
      ticket,
      getAllTickets,
      seleccionado,
      showConfirmationBanner,
      isPrivate,
      complete
    };
  },
};
</script>

<style scoped>
p, h1 {
  text-align: left;
  font-family: 'Franklin Gothic', 'Arial Narrow', Arial, sans-serif;
  margin-top: 1%;
}

p {
  font-weight: 600;
  font-size: larger;
}

h5 {
  font-family: 'Franklin Gothic', 'Arial Narrow', Arial, sans-serif;
}

.container-flex {
  display: flex;
  flex-direction: column;
}

.row {
  display: flex;
  justify-content: space-around;
  margin-top: 4%;
}

.col-12 {
  padding-bottom: 30px;
}

p {
  font-family: 'Franklin Gothic', 'Arial Narrow', Arial, sans-serif;
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

.btn:disabled {
  background-color: var(--main-bg-dark);
}

.overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  z-index: 1000;
}

.confirmation-banner {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: #fff;
  border: 1px solid #ccc;
  border-radius: 5px;
  padding: 10px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
  z-index: 1001;
}

.confirmation-banner p {
  margin: 0;
  margin-bottom: 10px;
}

.confirmation-banner button {
  margin-right: 10px;
  padding: 5px 10px;
  border: 1px solid #ccc;
  border-radius: 3px;
  cursor: pointer;
}

.confirmation-banner button:hover {
  background-color: #e7e5e5;
}

.confirmation-banner input {
  margin-bottom: 10px;
  padding: 5px;
  border: 1px solid #ccc;
  border-radius: 3px;
}

.confirmation-banner input:focus {
  outline: none;
  border-color: var(--main-bg-org);
}

.bi {
  margin-left: 10px;
  position: relative;
  display: inline-block;
  font-size: 0.75em;
}
</style>
