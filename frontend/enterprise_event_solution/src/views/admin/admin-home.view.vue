<template>
    <div class="flex-container">

       <div class="row"><h1>Dashboard</h1></div>
        <div class="row">
            <div class="col-6">
                <div class="row"><h3>Usuarios Registrados por mes</h3></div>
                <div class="row">
                    <UserLinechart></UserLinechart>
                </div>
            </div>
                
            <div class="col-6">
                <div class="row"><h3>Tipos de Usuarios</h3></div>
                <div class="row"><RoleLineChart></RoleLineChart></div>    
            </div>
        </div>
        <div class="row">
            <div class="col-8">
                <div class="row"><h3>Eventos registrados al Mes</h3></div>
                <div class="row"><EventLineChart></EventLineChart></div>   
            </div>
            <div class="col-4">
                <h3>Crear Organizador</h3>
                <p>Añade organizaciones desde aquí para que puedan añadir nuevos eventos.</p>
                <router-link :to="{ name:'create-org'}"><button>Añadir Organizador</button></router-link>
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


 h1{
    margin-top: -3%;
    margin-bottom: -3%;
    display: flex;
    font-size: 2em;
    font-family: 'Franklin Gothic', 'Arial Narrow', Arial, sans-serif;
 } 
 .flex-container{
    margin-left: 1%;
    margin-right: 2%;
 }
 .row{
    padding-top: 3%;
    margin-right: 30px;
    display: flex;
    justify-content: space-between;
 }
 .col-6{
    border-radius: 20px;
    box-shadow: var(--main-bg-light) 0px 10px 15px -3px, var(--main-bg-light) 0px -15px 10px 0px;
    margin-left: 0px;
 }
 .col-8{ 
    border-radius: 20px;
    box-shadow: var(--main-bg-light) 0px 10px 15px -3px, var(--main-bg-light) 0px -15px 10px 0px;
    margin-left: 0%;
    
    height: 20%;
 }
 h3{

    font-family: 'Franklin Gothic', 'Arial Narrow', Arial, sans-serif;
 }

 .col-4{
   
    height: 37vh;
    border-radius: 15px;
    box-shadow: var(--main-bg-org) 0px 10px 15px -3px, var(--main-bg-light) 0px -15px 10px 0px;
    background: #f7b679;
    border-width: 5px;
 }

 .col-4 h3{
    color: black;
    margin-top: 8%;
 }

 .col-4  p{
    color: black;
    font-weight: 500;
 }

 button{
    border-radius: 15px;
    background-color: var(--main-bg-light);
    margin-top: 10%;
    cursor: pointer;
    transition: 0.4s;

 }

 button:hover{
    background-color: var(--main-bg-dark);
    color:white;
    transition: 0.4s;
 }
</style>
