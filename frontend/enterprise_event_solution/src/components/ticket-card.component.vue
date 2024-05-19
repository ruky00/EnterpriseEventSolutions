<template>
    <div>
      <div class="card text-center"  >
        <div class="row g-0">
          <div class="col-md-4 d-flex align-items-center justify-content-center">
            <img :src="qrCodeURL" class="img-fluid rounded-start" alt="QR Code">
          </div>
          <div class="col-md-8">
            <div class="card-body">
              <h5 class="card-title">{{ ticket.event?.name }}</h5>
              <p class="card-text">{{ limitedDescription }}</p> <!-- Utiliza la descripción limitada -->
              <p class="card-text"><small class="text-muted">Adquirida en: {{ formattedDate }}</small></p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script lang="ts">
  import { defineComponent, onMounted, ref } from 'vue';
  import { Ticket } from '@/models/Ticket';
  
  export default defineComponent({
    name: 'TicketCard',
    props: {
      ticket: {
        type: Object as () => Ticket,
        required: true
      }
    },
    setup(props) {
      const qrCodeURL = ref('');
      const limitedDescription = ref(''); // Agrega referencia para la descripción limitada
  
      const getQR = () => {
        if (props.ticket.qrCode) {
          qrCodeURL.value = 'data:image/png;base64,' + props.ticket.qrCode;
        }
      };
  
      const formatDescription = () => {
        if (props.ticket.event?.description) {
          // Limita la descripción a 50 caracteres
          limitedDescription.value = props.ticket.event.description.substring(0, 50);
          if (props.ticket.event.description.length > 50) {
            limitedDescription.value += '...'; // Agrega puntos suspensivos si la descripción es más larga
          }
        }
      };
  
      const dateFormatter = new Intl.DateTimeFormat('es-ES', { year: 'numeric', month: 'long', day: 'numeric' });
      const formattedDate = ref('');
  
      onMounted(() => {
        getQR();
        formatDescription();
        formattedDate.value = dateFormatter.format(new Date(props.ticket.creationTime));
      });
  
      return {
        qrCodeURL,
        limitedDescription,
        formattedDate
      };
    }
  });
  </script>
  
  <style scoped>
  .card-body {
    display: flex;
    align-items: center;
    flex-wrap: nowrap;
    flex-direction: column;
    justify-content: space-between;
  }
  </style>
  