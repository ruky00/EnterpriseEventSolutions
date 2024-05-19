<template>
    <h1>Mis Entradas</h1>
    
        <div class="card-group">
            <div  class="col" v-for="(ticket, index) in tickets" :key="index"> 
                <TicketCard
                :ticket="ticket"
                />
            </div>
        </div>

</template>

<script lang="ts">
import TicketCard from '@/components/ticket-card.component.vue';
import { Ticket } from '@/models/Ticket';
import { UserService } from '@/services/user.service';
import { onMounted, ref } from 'vue';


export default{
    name: 'TicketList',
    components:{
        TicketCard
    },
    setup(){
        const tickets = ref([] as Ticket[])

        const getTickets = async ()=>{
            try{
             const ticketsData = await UserService.prototype.getTickets();
             tickets.value = ticketsData;
              console.log(tickets.value)
            }catch(error){
                console.log(error)
            }
        }

        onMounted(getTickets)


        return{
            tickets,
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

.container{
    display: flex;
    flex-wrap: nowrap;
    width: 100%;
    padding-left: 0px;
    margin: 0px;
    flex-direction: row;
    align-items: center;

    margin-top: 3%;
    }
.col{
    margin-top: 2;
    margin-bottom: 2%;
    margin-left: 1%;
}

.card-body{
    display: flex;
    align-items: center;
    flex-wrap: nowrap;
    flex-direction: column;
    justify-content: space-between;
}
.card-group{
    margin-top: 3%;
}


</style>