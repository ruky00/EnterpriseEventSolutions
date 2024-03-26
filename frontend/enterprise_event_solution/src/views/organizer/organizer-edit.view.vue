<template>
    <div class="create-event-container">
      <h2 class="create-event-title">Edit Event</h2>
      <form @submit.prevent="updateEvent" class="create-event-form">
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
            min="1"
            class="form-control"
            placeholder="Maximum attendees"
            required
          />
        </div>
        <div class="form-group mb-3">
          <label for="date" class="form-label">Date:</label>
          <input type="date" id="date" v-model="evento.date" class="form-control" required />
        </div>
        <button type="submit" class="btn btn-primary create-event-button">Create Event</button>
      </form>
    </div>
  </template>

<script lang="ts">
import { useRouter } from 'vue-router';
import { Event } from '@/models/Event';
import { onMounted, ref } from 'vue';
import {EventService} from '../../services/event.service'
export  default {
    name:'organizer-edit',

    setup(){
        const router = useRouter();
        const evento = ref({} as Event);
        const eventId  = ref(0);

  
        onMounted(async () => {
            eventId.value = parseInt(router.currentRoute.value.params.id as string, 10)
            evento.value = await EventService.prototype.getEventById(eventId.value);

        });

        const updateEvent = async ()=>{
            console.log(evento.value)
            await  EventService.prototype.updateEvent(evento.value)
            .then(() => {
                router.back();
            });
        }


        return{
            router,
            evento,
            updateEvent
        }

    }




}

</script>

<style scoped>
.create-event-container {
  border-radius: 10px; /* Rounded corners */
  padding: 20px; /* Inner padding */
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1); /* Subtle shadow */
  max-width: 600px; /* Optional maximum width */
  margin: 0 auto; /* Center the form horizontally */
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
  background-color: #4CAF50; /* Green primary button */
  color: white; /* White text */
  border: none; /* Remove default border */
  border-radius: 5px; /* Rounded corners for button */
  padding: 10px 20px; /* Consistent padding for button */
  cursor: pointer; /* Indicate clickable behavior */
  transition: background-color 0.2s ease-in-out; /* Smooth transition on hover */
}

.create-event-button:hover {
  background-color: #3e8e41;  

}
</style>