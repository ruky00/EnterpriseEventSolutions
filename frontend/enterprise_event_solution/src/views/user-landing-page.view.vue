<template>
  <div class="container-fluid">
    <div class="row">
      <!-- Barra de navegación lateral -->
      <div class="col-1">
        <user_navbar />
      </div>

      <!-- Contenido principal -->
      <div class="col-11">
        <router-view />
      </div>
    </div>
  </div>
</template>



<script lang="ts">
import user_navbar from '../components/user-nav-bar.component.vue'
export default {
  components:{
    user_navbar
  },
  setup() {
    

    return {  
    };
  },
};

</script>




<style scoped>

.row{
  padding-left: 0px !important; 
  padding-right: 0px !important;
}

.container-fluid{
  padding-left: 0px ; 
  padding-right: 0px ;
}

.col-1{
  padding-right: -100px;
}




</style>