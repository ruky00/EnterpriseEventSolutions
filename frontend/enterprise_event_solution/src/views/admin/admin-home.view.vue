<template>
    <h1>Dashboard</h1>
    <div class="container">
      <div class="row mb-4">
        <div class="col-md-6 mb-4 shadow-box">
          <h3>Usuarios Registrados por mes</h3>
          <UserLinechart></UserLinechart>
        </div>
        <div class="col-md-5 mb-4 shadow-box">
          <h3>Tipos de Usuarios</h3>
          <RoleLineChart></RoleLineChart>
        </div>
      </div>
      <div class="row">
        <div class="col-md-8 mb-4 shadow-box">
          <div class="row"><div class="col"><h3>Eventos registrados al Mes</h3></div></div>
          <div class="row"><div class="col"><EventLineChart></EventLineChart></div></div>
         
        </div>
        <div class="col-md-3 shadow-box">
          <h3>Crear Organizador</h3>
          <p>Añade organizaciones desde aquí para que puedan añadir nuevos eventos.</p>
          <router-link :to="{ name:'create-org'}">
            <button class="btn-primary">Añadir Organizador</button>
          </router-link>
        </div>
      </div>
    </div>
  </template>

<script lang="ts">
import UserLinechart from "../../components/user-month.component.vue";
import RoleLineChart from "../../components/user-role.component.vue";
import EventLineChart from "../../components/events-month.component.vue";
export default ({
    name:"admin-home",
    components: {
        UserLinechart,
        RoleLineChart,
        EventLineChart
    },
    setup(){
            

        return{
          
        }
    }
  
})
</script>


<style scoped>
p, h1 {
  text-align: left;
  font-family: 'Franklin Gothic', 'Arial Narrow', Arial, sans-serif;
  margin-top: 1%;
}

.container {
  margin: 0 auto;
  padding: 0 15px;
  max-width: 1200px;
}

.row {
  display: flex;
  flex-wrap: wrap;
  margin: 0 -15px;
}

.col-md-6, .col-md-8, .col-md-4 {
  padding: 20px;
  box-sizing: border-box;
  margin-bottom: 20px; 
}

h3 {
  font-family: 'Franklin Gothic', 'Arial Narrow', Arial, sans-serif;
}

.btn-primary {
  padding: 0.75rem 1.5rem;
  background-color: var(--main-bg-org);
  color: #fff;
  border: none;
  border-radius: 9px;
  font-size: 1rem;
  cursor: pointer;
  transition: background-color 0.4s ease-in-out;
  margin-bottom: 1%;
}

.btn-primary:hover {
  background-color: var(--main-bg-dark);
}

button:hover {
  background-color: var(--main-bg-dark);
  color: white;
  transition: 0.4s;
}

.mb-4 {
  margin-bottom: 1.5rem;
}

.shadow-box {
  border-radius: 15px;
  box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1), 0px 8px 15px rgba(0, 0, 0, 0.1);
  background: white;
  padding-bottom: 40px;
}

.col-md-3,.col-md-5{
  margin-left: 5%;
}
.col-md-3{
  max-height: 28vh;
  padding-bottom: 0px;
  padding-top: 20px;
}
</style>