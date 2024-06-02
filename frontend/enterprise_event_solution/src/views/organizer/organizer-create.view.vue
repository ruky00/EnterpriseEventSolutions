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
        <div class="form-group ">
          <label for="description" class="form-label">Description:</label>
          <textarea
            id="description"
            v-model="evento.description"
            class="form-control"
            placeholder="Describe your event"
            required
          ></textarea>
        </div>
        <div class="form-group ">
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
        <div class="form-group ">
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
        <div class="form-group ">
          <label for="description" class="form-label">Password (Optional):</label>
          <input
            id="password"
            v-model="evento.encodedPassword"
            class="form-control"
            placeholder="Event password if necesary"
            
          />
        </div>
        <div class="form-group">
          <label for="date" class="form-label">Date:</label>
          <input type="date" id="date" v-model="evento.date" class="form-control" required />
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
import {EventService} from '../../services/event.service'
export default{
    name: "create-event",
    

    setup(){
        const router = useRouter();
        const evento = ref({} as Event);
        const seleccionado = ref(false)
        const createEvent =  async()=>{
          seleccionado.value=true
            await EventService.prototype.createEvent(evento.value)
            .then(() => {
                seleccionado.value=false
                router.back();
            });
        }

        return{
            evento,
            router,
            createEvent,
            seleccionado
        }

    }
}

</script>


<style scoped>

p, h1{
  text-align: left;
  font-family: 'Franklin Gothic', 'Arial Narrow', Arial, sans-serif;
  margin-top: 1%;
}

.create-event-container {
  border-radius: 10px; /* Rounded corners */
  padding: 20px; /* Inner padding */
  box-shadow: #b1b1b1 0px 10px 15px -1px,  #b1b1b1 0px -10px 10px 0px; 
  max-width: 600px; /* Optional maximum width */
  max-width: 100%; /* Center the form horizontally */
  margin: 4%;
}

.create-event-title {
  color: #333; /* Darker text for heading */
  text-align: center; /* Center the title */
  margin-bottom: 20px; /* Add some space after the title */
}



.form-group {
  margin-bottom: 15px; /* Consistent margin for form elements */
}

.form-label {
  color: #666; /* Lighter text for labels */
  font-weight: bold; /* Emphasize labels */
}

.form-control {
  border-radius: 5px; /* Rounded corners for inputs */
  border-color: #ccc; /* Light border color */
  padding: 10px; /* Consistent padding for inputs */
}

.form-control:focus {
  border-color: #888; /* Highlight border on focus */
  outline: none; /* Remove default outline */
}

.create-event-button {
  background-color: var(--main-bg-org); /* Green primary button */
  color: white; /* White text */
  border: none; /* Remove default border */
  border-radius: 5px; /* Rounded corners for button */
  padding: 10px 20px; /* Consistent padding for button */
  cursor: pointer; /* Indicate clickable behavior */
  transition: background-color 0.2s ease-in-out; /* Smooth transition on hover */
}

.create-event-button:hover {
  background-color: var(--main-bg-dark);  

}
</style>