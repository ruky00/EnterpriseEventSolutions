<template>
  <div>
    <div class="card text-center" style="width: 100%;" ref="ticketCard">
      <div class="row g-0">
        <div class="col-md-4 d-flex align-items-center justify-content-center" :class="{ 'd-none': isSmallScreen }">
          <img v-if="!isSmallScreen" :src="qrCodeURL" class="img-fluid rounded-start" alt="QR Code">
        </div>
        <div class="col-md-8">
          <div class="card-body">
            <h5 class="card-title">{{ ticket.event?.name }}</h5>
            <p class="card-text">{{ limitedDescription }}</p>
            <p class="card-text"><small class="text-muted">Adquirida en: {{ formattedDate }}</small></p>
          </div>
        </div>
      </div>
    </div>
    <button class="btn btn-primary mt-3" @click="downloadPDF">Descargar PDF</button>
  </div>
</template>

<script lang="ts">
import { defineComponent, onMounted, ref, computed } from 'vue';
import { Ticket } from '@/models/Ticket';
import jsPDF from 'jspdf';
import html2canvas from 'html2canvas';

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
    const limitedDescription = ref('');
    const ticketCard = ref(null);
    const formattedDate = ref('');

    const getQR = () => {
      if (props.ticket.qrCode) {
        qrCodeURL.value = 'data:image/png;base64,' + props.ticket.qrCode;
      }
    };

    const formatDescription = () => {
      if (props.ticket.event?.description) {
        limitedDescription.value = props.ticket.event.description.substring(0, 50);
        if (props.ticket.event.description.length > 50) {
          limitedDescription.value += '...';
        }
      }
    };

    const dateFormatter = new Intl.DateTimeFormat('es-ES', { year: 'numeric', month: 'long', day: 'numeric' });

    const downloadPDF = async () => {
      if (ticketCard.value) {
        const canvas = await html2canvas(ticketCard.value);
        const imgData = canvas.toDataURL('image/png');
        const pdf = new jsPDF();
        const imgProps = pdf.getImageProperties(imgData);
        const pdfWidth = pdf.internal.pageSize.getWidth();
        const pdfHeight = (imgProps.height * pdfWidth) / imgProps.width;

        pdf.addImage(imgData, 'PNG', 0, 0, pdfWidth, pdfHeight);
        pdf.save(`ticket_${props.ticket.id}.pdf`);
      }
    };

    const isSmallScreen = computed(() => window.innerWidth < 768); // 768px es el límite de pantalla pequeña según Bootstrap

    onMounted(() => {
      getQR();
      formatDescription();
      formattedDate.value = dateFormatter.format(new Date(props.ticket.creationTime));
    });

    return {
      qrCodeURL,
      limitedDescription,
      formattedDate,
      downloadPDF,
      ticketCard,
      isSmallScreen
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

button {
  margin-top: 1rem;
  padding: 0.5rem 1rem;
  border: none;
  border-radius: 5px;
  background-color: #007bff;
  color: white;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

button:hover {
  background-color: #0056b3;
}
</style>
