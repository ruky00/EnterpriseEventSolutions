<template>
    <div class="row"><h1>Users Table</h1></div>
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
      // Implement logic to reload users (e.g., call `getUsers`)
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
h1{
    font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;
    display: flex;
    margin-left: 1%;
}
.row{
    margin-top: 2%;
}

.row .table{
    margin-top: 2%;
}


</style>