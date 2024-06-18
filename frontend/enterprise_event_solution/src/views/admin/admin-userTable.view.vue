<template>
  <h1>Users Table</h1>
    <div class="row table">
        <userTables
            :users="users"
            :pagination="true"
            :emitReloadUsers ="emitReloadUsers"
        />
    </div>
    

</template>


<script lang="ts">
import { User } from '@/models/User';
import userTables from '../../components/user-table.component.vue'
import { onMounted, ref } from 'vue';
import { AdminService } from '@/services/admin.service';
export default{
    name:'admin-table',
    components:{
        userTables
    },

    setup(){
        const users = ref([] as User[])
        const getUsers = async()=>{
            try{
                const usersData = await AdminService.prototype.getUsers();
                users.value=usersData;

        }catch(error){
            console.log(error)
        }
    }

    const emitReloadUsers = () => {
      
      getUsers();
    };

    onMounted(getUsers)
    return{
        users,
        emitReloadUsers
        
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

.row{
    margin-top: 2%;
}

.row .table{
    margin-top: 2%;
}


</style>