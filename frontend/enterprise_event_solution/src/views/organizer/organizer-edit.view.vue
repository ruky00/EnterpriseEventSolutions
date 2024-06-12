<template>
  <h1>Edit Event</h1>
  <div class="create-event-container">
    <h2 class="create-event-title">Edit Event</h2>
    <form @submit.prevent="validateAndUpdateEvent" class="create-event-form">
      <div class="form-group mb-3">
        <label for="name" class="form-label">Name:</label>
        <input
          type="text"
          id="name"
          v-model="evento.name"
          class="form-control"
          placeholder="Enter Event Name"
          required
        />
      </div>
      <div class="form-group mb-3">
        <label for="description" class="form-label">Description:</label>
        <textarea
          id="description"
          v-model="evento.description"
          class="form-control"
          placeholder="Describe your event"
          required
        ></textarea>
      </div>
      <div class="form-group mb-3">
        <label for="price" class="form-label">Price:</label>
        <input
          type="number"
          id="price"
          v-model="evento.price"
          min="0"
          step="0.01"
          class="form-control"
          placeholder="Price (optional)"
        />
      </div>
      <div class="form-group mb-3">
        <label for="maxCapacity" class="form-label">Max Capacity:</label>
        <input
          type="number"
          id="maxCapacity"
          v-model="evento.max_capacity"
          :min="initialMaxCapacity"
          class="form-control"
          placeholder="Maximum attendees"
          required
        />
        <div v-if="error" class="error-message">{{ error }}</div>
      </div>
      <div class="form-group mb-3">
        <label for="date" class="form-label">Date:</label>
        <input
          type="date"
          id="date"
          v-model="evento.date"
          :min="minDate"
          class="form-control"
        />
      </div>
      <button v-if="!seleccionado" type="submit" class="btn btn-primary create-event-button">
        Edit Event
      </button>
      <button v-else class="btn btn-primary" type="button" disabled>
        <span class="spinner-grow spinner-grow-sm" role="status" aria-hidden="true"></span>
        Editando...
      </button>
    </form>
  </div>
</template>

<script lang="ts">
import { useRouter } from 'vue-router';
import { Event } from '@/models/Event';
import { onMounted, ref } from 'vue';
import { EventService } from '../../services/event.service';

export default {
  name: 'organizer-edit',

  setup() {
    const router = useRouter();
    const evento = ref({} as Event);
    const eventId = ref(0);
    const seleccionado = ref(false);
    const minDate = ref(new Date().toISOString().split('T')[0]);
    const initialMaxCapacity = ref(0);
    const error = ref('');

    onMounted(async () => {
      eventId.value = parseInt(router.currentRoute.value.params.id as string, 10);
      evento.value = await EventService.prototype.getEventById(eventId.value);
      initialMaxCapacity.value = evento.value.max_capacity; // Store the initial max capacity
    });

    const validateAndUpdateEvent = async () => {
      error.value = '';
      if (evento.value.max_capacity < initialMaxCapacity.value) {
        error.value = `Max Capacity cannot be less than ${initialMaxCapacity.value}`;
        return;
      }
      seleccionado.value = true;
      console.log(evento.value);
      await EventService.prototype.updateEvent(evento.value)
        .then(() => {
          seleccionado.value = false;
          router.back();
        });
    };

    return {
      router,
      evento,
      validateAndUpdateEvent,
      seleccionado,
      minDate,
      initialMaxCapacity,
      error
    };
  }
};
</script>

<style scoped>
p, h1 {
  text-align: left;
  font-family: 'Franklin Gothic', 'Arial Narrow', Arial, sans-serif;
  margin-top: 1%;
}

.create-event-container {
  border-radius: 10px; 
  padding: 20px; 
  box-shadow: #b1b1b1 0px 10px 15px -1px, #b1b1b1 0px -10px 10px 0px; 
  max-width: 600px; 
  max-width: 100%; 
  margin: 4%;
}

.create-event-title {
  color: #333; 
  text-align: center; 
  margin-bottom: 20px; 
}

.form-group {
  margin-bottom: 15px; 
}

.form-label {
  color: #666; 
  font-weight: bold; 
}

.form-control {
  border-radius: 5px; 
  border-color: #ccc; 
  padding: 10px; 
}

.form-control:focus {
  border-color: #888; 
  outline: none; 
}

.create-event-button {
  background-color: var(--main-bg-org); 
  color: white;
  border: none; 
  border-radius: 5px; 
  padding: 10px 20px; 
  cursor: pointer; 
  transition: background-color 0.2s ease-in-out; 
}

.create-event-button:hover {
  background-color: var(--main-bg-dark);  
}

.error-message {
  color: red;
  margin-top: 5px;
}
</style>
