<template>
  <h1>Organizaciones</h1>
    <div class="container">
      <div class="card-group">
      <div class="col" v-for="(organizer, index) in organizers" :key="index">

    <router-link :to="{ name: 'user-info', params: { id: organizer.id }}" style="text-decoration: none;">    
      <div class="card" style="width: 20rem; height: 20rem;">
        <img v-if="organizer" :src="organizer.image" class="card-img-top img-thumbnail img-fluid" alt="..." />
        <div class="card-body">
          <h5 class="card-title">{{organizer.username}}</h5>
          <p class="card-text">{{truncatedDescription(organizer.description!)}}</p>
         
      </div>
      </div>
    </router-link>  

    </div>
    </div>
    </div>
  </template>
  
  <script lang="ts">
import { User } from '@/models/User';
import { UserService } from '@/services/user.service';
import { computed, onMounted, ref } from 'vue';

  export default {
    name: "client-home",
    setup() {
      const organizers = ref([] as User[])
    

     const getOrganizers = async()=>{
        const response =  await UserService.prototype.getOrganizers();
        organizers.value = response;
     }

     const truncatedDescription = computed(() => (description: string) => {
      if (description && description.length > 80) {
        return description.substring(0, 80) + '...';
      }
      return description;
    });

  
      onMounted(getOrganizers);
  
      return {
        organizers,
        getOrganizers,
        truncatedDescription
      }
    }
  }
  </script>
  
<style scoped>

    p, h1{
  text-align: left;
  font-family: 'Franklin Gothic', 'Arial Narrow', Arial, sans-serif;
  margin-top: 1%;
}

h5{
  font-family: 'Franklin Gothic', 'Arial Narrow', Arial, sans-serif;
}
.container {
  display: flex;
    flex-wrap: nowrap;
    width: 100%;
    padding-left: 0px;
    margin: 0px;
    flex-direction: row;
    align-items: center;
    justify-content: center;
    margin-left: 2% ;
    margin-top: 3%;
}

.box {
  flex-grow: 1; 
  height: 100%; 
  margin: 0; 
  transition: all 0.5s ease;
  opacity: 1;
  background-repeat: no-repeat;
  background-size: cover;
}

.box:hover {
  transform: scale(1.2); 
  opacity: 200; 
}

.card-img-top {
    object-fit: cover; 
    height: 100%; 
    width: auto;
    max-height: 170px; 
  }


.card-body{
    display: flex;
    align-items: center;
    flex-wrap: nowrap;
    flex-direction: column;
    justify-content: space-between;
}

.card {
  transition: transform 0.5s ease-in-out; 
  border-style: solid;
  border-color:var(--main-bg-dark);
  overflow: hidden;
  margin-top: 3%;
}

.card:hover {
  transform: scale(1.1); 
  border-color:var(--main-bg-org);
}

</style>