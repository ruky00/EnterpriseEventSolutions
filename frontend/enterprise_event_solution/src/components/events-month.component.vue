<template>
    <canvas id="myChart3" height="20%" width="90%">
    
    </canvas>
     
    
    
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
          return {
            type: 'line',
            data: {
              labels,
              datasets: [{
                label: 'Eventos registrados al mes',
                data: eventsMonth.value, // Use the reactive usersMonth
                fill: false,
                 borderColor: '#15616D',
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
              animations: {
                tension: {
                  duration: 15000,
                  easing: 'easeInSine',
                  from: 0.7,
                  to: 0,
                  loop: true,
                },
              },
              scales: {
                y: { // Define min and max for consistent scale
                  min: 0,
                  max: 100,
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
          await getData(); // Fetch data first
          const config = createChartConfig(); // Create config after data is ready
          const canvasTag = document.getElementById('myChart3') as HTMLCanvasElement;
          new Chart(canvasTag, config);
        });
    
        return {}; // No need to return data, labels, or config directly
                  // They are accessible within the component's setup scope
      },
    };
    </script>
    
    <style scoped>
    
    
    </style>