<template>
  <div class="chart-container"> <canvas id="myChart1" height="50%" width="100%">
    
    </canvas>
     
    </div>
    
    </template>
    
    <script lang="ts">
    import { AdminService } from '@/services/admin.service';
    import Chart, { ChartConfiguration } from 'chart.js/auto';
    import { onMounted, ref
     } from 'vue';
    
    export default {
      name: 'RoleLineChart',
        
      setup() {
        const usersRole= ref<number[]>([]); // Reactive array for user counts
        const getData = async () => {
          try {
            const userData = await AdminService.prototype.getUsersRole();
            if (!userData) {
              console.error('Error al obtener los datos de usuarios.');
              // Handle visualization of error or alternative mechanism (optional)
              return; // Prevent issues if userData is unexpectedly undefined
            }
            usersRole.value = userData; // Update the array reactively
          } catch (error) {
            console.error('Error al obtener los datos de usuarios:', error);
            // Handle visualization of error or alternative mechanism (optional)
          }
        };
    
        const createChartConfig = (): ChartConfiguration => {
          return {
            type: 'bar',
            data: {
              
              datasets: [{
                label: 'Usuarios registrados al mes',
                data: usersRole.value,
                backgroundColor: [
                '#15616D',
                '#FFECD1'
                ],
                borderColor: [
                '#15616D',
                '#FFECD1'
                ], // Use the reactive usersMonth
                fill: false,
                tension: 0.5,
                pointHoverBackgroundColor: '#15616D',
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
                y:{ // Define min and max for consistent scale
                  min: 0,
                  max: 20,
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
          const canvasTag = document.getElementById('myChart1') as HTMLCanvasElement;
          new Chart(canvasTag, config);
        });
    
        return {}; // No need to return data, labels, or config directly
                  // They are accessible within the component's setup scope
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