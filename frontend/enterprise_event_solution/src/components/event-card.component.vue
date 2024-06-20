<template>
  <template v-if="isOrg">
    <div class="card text-center" style="width: 28rem; height: 12rem;">
      <div class="card-body">
        <h5 class="card-title">{{ evento?.name }}</h5>
        <p class="card-text">{{ truncatedDescription(evento?.description!) }}</p>
        <div class="progress fixed-progress">
          <div class="progress-bar" :style="{ width: progressPercentage + '%' }" role="progressbar" :aria-valuenow="progressPercentage" aria-valuemin="0" aria-valuemax="100">
            {{ progressPercentage }}%
          </div>
        </div>
        <p class="progress-text">{{ evento?.current_capacity }} / {{ evento?.max_capacity }} Plazas ocupadas</p>
        <div class="row">
          <button class="btn btn-sm btn-danger me-2" @click="deleteEvent(evento!)">
            <i class="bi bi-trash"></i>
          </button>
          <router-link :to="{ name: 'edit-event', params: { id: evento?.id }}" class="btn btn-sm btn-primary">
            <i class="bi bi-gear"></i>
          </router-link>
        </div>
      </div>
    </div>
    <div v-if="showConfirmationBanner" class="overlay">
      <div class="confirmation-banner">
        <p>¿Estás seguro de que deseas eliminar el evento?</p>
        <button @click="confirmDeleteEvent">Sí</button>
        <button @click="cancelDeleteEvent">No</button>
      </div>
    </div>
  </template>
  <template v-else>
    <router-link :to="{ name: 'event-view', params: { id: evento?.id }}" style="text-decoration: none">
      <div class="card not-org text-center" style="width: 28rem; height: 10rem;">
        <div class="card-body">
          <h5 class="card-title">{{ evento?.name }}</h5>
          <p class="card-text">{{ truncatedDescription(evento?.description!) }}</p>
        </div>
      </div>
    </router-link>
  </template>
</template>

<script lang="ts">
import { EventService } from "../services/event.service";
import { useRouter } from 'vue-router';
import { Event } from '../models/Event';
import { computed, defineComponent, ref, watch } from 'vue';

export default defineComponent({
  name: 'event_cards',
  props: {
    evento: Object as () => Event,
    isOrg: Boolean,
    emitReloadEvents: {
      type: Function,
      default: null
    }
  },
  setup(props) {
    const router = useRouter();
    const eventos = ref({} as Event)
    const showConfirmationBanner = ref(false);
    const selectedEventToDelete = ref<Event | null>(null);

    const truncatedDescription = computed(() => (description: string) => {
      if (description && description.length > 80) {
        return description.substring(0, 80) + '...';
      }
      return description;
    });

    const progressPercentage = computed(() => {
      if (props.evento?.current_capacity && props.evento?.max_capacity) {
        return Math.floor((props.evento.current_capacity / props.evento.max_capacity) * 100);
      }
      return 0;
    });

    const deleteEvent = (event: Event) => {
      selectedEventToDelete.value = event;
      showConfirmationBanner.value = true;
    };

    const cancelDeleteEvent = () => {
      selectedEventToDelete.value = null;
      showConfirmationBanner.value = false;
    };

    const confirmDeleteEvent = async () => {
      try {
        if (selectedEventToDelete.value) {
          await EventService.prototype.deleteEvent(selectedEventToDelete.value.id!);
          showConfirmationBanner.value = false;
        }
      } catch (error) {
        alert("No se ha podido eliminar al usuario");
      }
    };

    watch(
      showConfirmationBanner,
      async (newVal, oldVal) => {
        if (!newVal && oldVal) {
          props.emitReloadEvents();
        }
      },
      { immediate: true }
    );

    return {
      eventos,
      router,
      deleteEvent,
      truncatedDescription,
      showConfirmationBanner,
      confirmDeleteEvent,
      cancelDeleteEvent,
      progressPercentage
    }
  }
});
</script>

<style scoped>
p, h1 {
  text-align: left;
  font-family: 'Franklin Gothic', 'Arial Narrow', Arial, sans-serif;
  margin-top: 1%;
}

h5 {
  font-family: 'Franklin Gothic', 'Arial Narrow', Arial, sans-serif;
}

.card-body {
  display: flex;
  align-items: center;
  flex-wrap: nowrap;
  flex-direction: column;
  justify-content: space-between;
}

.card {
  transition: transform 0.5s ease-in-out;
  border-style: solid;
  border-color: var(--main-bg-dark);
}

.not-org:hover {
  transform: scale(1.1);
  border-color: var(--main-bg-org);
}

p {
  margin: 0px;
}

.btn {
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
  .card {
    height: 130px;
  }
}

@media screen and (min-width: 577px) and (max-width: 768px) {
  .card {
    height: 150px;
  }
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
  background-color: #eee;
}

.progress {
  width: 100%;
  height: 20px;
  background-color: #e9ecef;
  border-radius: 5px;
  overflow: hidden;
}

.progress-bar {
  background-color: #15616D;
  height: 100%;
  transition: width 0.6s ease;
}

.progress-text {
  margin-top: 10px;
  font-weight: bold;
}
</style>
