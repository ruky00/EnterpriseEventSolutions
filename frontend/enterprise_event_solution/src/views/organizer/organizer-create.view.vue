<template>
  <h1>Create Event</h1>
  <div class="create-event-container">
    <h2 class="create-event-title">Create Event</h2>
    <form @submit.prevent="createEvent" class="create-event-form">
      <div class="form-body">
        <div class="form-group">
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
        <div class="form-group">
          <label for="description" class="form-label">Description:</label>
          <textarea
            id="description"
            v-model="evento.description"
            class="form-control"
            placeholder="Describe your event"
            required
          ></textarea>
        </div>
        <div class="form-group">
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
        <div class="form-group">
          <label for="maxCapacity" class="form-label">Max Capacity:</label>
          <input
            type="number"
            id="maxCapacity"
            v-model="evento.max_capacity"
            min="1"
            class="form-control"
            placeholder="Maximum attendees"
            required
          />
        </div>
        <div class="form-group">
          <label for="password" class="form-label">Password (Optional):</label>
          <input
            type="password"
            id="password"
            v-model="password"
            class="form-control"
            placeholder="Event password if necessary"
          />
        </div>
        <div class="form-group">
          <label for="confirmPassword" class="form-label">Confirm Password:</label>
          <input
            type="password"
            id="confirmPassword"
            v-model="confirmPassword"
            class="form-control"
            placeholder="Confirm password"
          />
        </div>
        <div class="form-group">
          <label for="date" class="form-label">Date:</label>
          <input type="date" id="date" v-model="evento.date" :min="minDate" class="form-control" required />
        </div>
        <button v-if="!seleccionado" type="submit" class="btn btn-primary create-event-button">Create Event</button>
        <button v-else class="btn btn-primary" type="button" disabled>
          <span class="spinner-grow spinner-grow-sm" role="status" aria-hidden="true"></span>
          Creando...
        </button>
      </div>
    </form>
  </div>
</template>

<script lang="ts">
import { useRouter } from 'vue-router';
import { Event } from '@/models/Event';
import { ref } from 'vue';
import { EventService } from '../../services/event.service';

export default {
  name: 'create-event',
  setup() {
    const router = useRouter();
    const evento = ref({} as Event);
    const password = ref('');
    const confirmPassword = ref('');
    const seleccionado = ref(false);
    const minDate = ref(new Date().toISOString().split('T')[0]); 

    const createEvent = async () => {
      if (password.value !== confirmPassword.value) {
        alert('Passwords do not match');
        return;
      }
      if (password.value != '') {
        evento.value.encodedPassword = password.value;
      }
      
      seleccionado.value = true;
      try {
        await EventService.prototype.createEvent(evento.value);
        seleccionado.value = false;
        router.back();
      } catch (error) {
        alert('Error creating event');
        seleccionado.value = false;
      }
    };

    return {
      evento,
      password,
      confirmPassword,
      router,
      createEvent,
      seleccionado,
      minDate
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
</style>
