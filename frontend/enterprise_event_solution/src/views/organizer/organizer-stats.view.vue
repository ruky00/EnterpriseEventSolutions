<template>
  <div class="chart-container">
    <h1>Estadísticas de Eventos</h1>
    <canvas id="barChart"></canvas>
  </div>
</template>
  
  <script lang="ts">
  import { defineComponent, onMounted, ref } from 'vue';
  import { Chart, ChartConfiguration } from 'chart.js/auto';
  import { EventService } from '@/services/event.service';
  export default defineComponent({
    name: 'OrgStats',
    setup() {
      const eventStats= ref<number[]>([]);
        const getData = async () => {
          try {
            const userData = await EventService.prototype.getStats();
            if (!userData) {
              console.error('Error al obtener los datos de usuarios.');
              
              return; 
            }
            eventStats.value = userData; 
          } catch (error) {
            console.error('Error al obtener los datos de usuarios:', error);
           
          }
        };

      const createChartConfig = (): ChartConfiguration => {
        const maxDataValue = eventStats.value.length > 0 ? Math.max(...eventStats.value) : 0;

        return {
          type: 'bar',
          data: {
            datasets: [
              {
                label: 'Usuarios registrados en mis eventos',
                data: eventStats.value,
                backgroundColor: [
                  '#15616D',
                  '#FFECD1',
                  '#FF7D00',
                  '#5B8C5A',
                  '#C84B31',
                  '#2A2A72',
                  '#F6AE2D'
                ],
                borderColor: [
                  '#15616D',
                  '#FFECD1',
                  '#FF7D00',
                  '#5B8C5A',
                  '#C84B31',
                  '#2A2A72',
                  '#F6AE2D'
                ],
                borderWidth: 4,
                fill: false,
                tension: 0.5,
                pointHoverBackgroundColor: '#15616D',
                pointHoverRadius: 8,
                showLine: true
              }
            ]
          },
          options: {
            layout: {
              padding: 10
            },
            animations: {
              tension: {
                duration: 15000,
                easing: 'easeInSine',
                from: 0.7,
                to: 0,
                loop: true
              }
            },
            scales: {
              y: {
                beginAtZero: true,
                suggestedMax: maxDataValue + 5
              },
              x: {
              ticks: {
                font: {
                  family: "'Franklin Gothic', 'Arial Narrow', Arial, sans-serif",
                  size: 14,
                  weight: 'bold',
                  lineHeight: 1.2
                },
                color: '#15616D'
              }
            },
            },
            responsive: true,
            plugins: {
              legend: {
                display: false,
                labels: {
                  font: {
                    size: 20,
                    family: "'Franklin Gothic', 'Arial Narrow', Arial, sans-serif",
                    weight: 'bold',
                    lineHeight: 3
                  }
                }
              }
            }
          }
        };
      };
  
      onMounted(async () => {
        await getData();
        const config = createChartConfig();
        const canvasTag = document.getElementById('barChart') as HTMLCanvasElement;
        new Chart(canvasTag, config);
      });
  
      return {};
    }
  });
  </script>
  
<style scoped>
.chart-container {
  position: relative;
  width: 100%;
  height: 80%; 
}

canvas {
  width: 100% !important;
  height: 100% !important;
}

p, h1 {
  text-align: left;
  font-family: 'Franklin Gothic', 'Arial Narrow', Arial, sans-serif;
  margin-top: 1%;
}
</style>
  