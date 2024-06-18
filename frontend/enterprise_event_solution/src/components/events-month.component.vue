<template>
   <div class="chart-container"><canvas id="myChart3" height="20%" width="90%"></canvas></div> 
</template>
    
    <script lang="ts">
    import { AdminService } from '@/services/admin.service';
    import Chart, { ChartConfiguration } from 'chart.js/auto';
    import { onMounted, ref
     } from 'vue';
    
    export default {
      name: 'EventLineChart',
    
     setup() {
        const eventsMonth = ref<number[]>([]); // Reactive array for user counts
    
        const labels = ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'];
    
        const getData = async () => {
          try {
            const userData = await AdminService.prototype.getEventsMonth();
            if (!userData) {
              console.error('Error al obtener los datos de usuarios.');
              // Handle visualization of error or alternative mechanism (optional)
              return; // Prevent issues if userData is unexpectedly undefined
            }
            eventsMonth.value = userData; // Update the array reactively
          } catch (error) {
            console.error('Error al obtener los datos de usuarios:', error);
            // Handle visualization of error or alternative mechanism (optional)
          }
        };
    
        const createChartConfig = (): ChartConfiguration => {
          const maxDataValue = eventsMonth.value.length > 0 ? Math.max(...eventsMonth.value) : 0;

          return {
            type: 'line',
            data: {
              labels,
              datasets: [{
                label: 'Eventos registrados al mes',
                data: eventsMonth.value, // Use the reactive usersMonth
                fill: true,
                borderColor: '#FF6384',
                backgroundColor: '#FFB1C1',
                tension: 0.5,
                pointHoverBackgroundColor: '#FF7D00',
                pointHoverRadius: 8,
                borderWidth: 4,
                showLine: true,
              }],
            },
            options: {
              layout: {
                padding: 10,
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
                  size: 12,
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
                      lineHeight: 3,
                    },
                  },
                },
              },
            },
          };
        };
    
        onMounted(async () => {
          await getData(); 
          const config = createChartConfig(); 
          const canvasTag = document.getElementById('myChart3') as HTMLCanvasElement;
          new Chart(canvasTag, config);
        });
    
        return {}; 
                 
      },
    };
    </script>
    
<style scoped>
    .chart-container {
      position: relative;
      width: 100%;
      height: 100%;
    }

    canvas {
      width: 100% !important;
      height: 100% !important;
    }

</style>