<template>
  <event_component></event_component>
</template>
  
  <script lang="ts">
  
  import event_component from '../components/event.component.vue'
  
  export default {
    name: 'event-view',
    components:{
      event_component
    }



    
  }

  </script>
  
  <style scoped>
  .row{
    padding-top: 1%;
  }
  p, h1{
    text-align: left;
    font-family: 'Franklin Gothic', 'Arial Narrow', Arial, sans-serif;
    margin-top: 1%;
  }


  h2{
    text-align: left;
    font-family: 'Franklin Gothic', 'Arial Narrow', Arial, sans-serif;
  }

  </style>
  