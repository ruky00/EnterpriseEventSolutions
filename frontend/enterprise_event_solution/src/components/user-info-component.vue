<template>
    <div class="container-flex">
      <div v-if="user.role === 'ORGANIZATION'" class="row logo">
        <img :src="getLogo()" alt="Logo organizacion" height="80px">
      </div>
      <div v-if="user.role === 'ORGANIZATION'" class="row align-items-start">
        <div class="col-auto">
          <h4>Descripcion</h4>
        </div>
        </div>
        <div v-if="user.role === 'ORGANIZATION'" class="row align-items-start">
        <div class="col-auto">
          <p>{{ user.description }}</p>
        </div>
        </div>
        <div v-if="user.role === 'ORGANIZATION'">
        <div  class="col-12" v-for="(evento, index) in eventos" :key="index">
        <event_cards
        :evento="evento"
        :is-org="false"
        >
        </event_cards>
        </div>
        </div>



        <div v-if="isMe">
            <div v-if="user.role === 'ORGANIZATION'" class="row logo">
        <img :src="getLogo()" alt="Logo organizacion" height="80px">
      </div>
      <div v-if="user.role === 'ORGANIZATION'" class="row align-items-start">
        <div class="col-auto">
          <h4>Descripcion</h4>
        </div>
        </div>
        <div v-if="user.role === 'ORGANIZATION'" class="row align-items-start">
        <div class="col-auto">
          <textarea v-model="user.description"></textarea>
        </div>
        </div>
        <div class="row align-items-center">
            <div class="col-auto">Email</div>
            <div class="col-auto"><input type="email" v-model="user.email"/></div>
        </div>
        <div class="row align-items-center">
            <div class="col-auto">Username</div>
            <div class="col-auto"><input type="text" v-model="user.username"/></div>
        </div>
        <div class="row align-items-center">
            <div class="col-auto"> New Password</div>
            <div class="col-auto"><input type="password" v-model="newPassword"/></div>
        </div>
        <div class="row align-items-center">
            <div class="col-auto"> New Profile Image</div>
            <div class="col-auto"><input type="file" @change="handleFileChange"/></div>
        </div>
        <button @click="editUser()">Save Changes</button>

        </div>


      </div>

  </template>
  
  <script lang="ts">
  import { ref, onMounted } from 'vue';
  import { useRouter } from 'vue-router';
  import { User } from '../models/User';
  import { UserService } from '@/services/user.service';
  import { useStore } from 'vuex';
  import {EventService} from '../services/event.service'


  export default {
    name: 'user_info',
    setup() {
      const user = ref({} as User);
      const router = useRouter();
      const isMe = router.currentRoute.value.params.id === 'me';
      const newPassword = ""
      const newImage = new FormData();
      const store = useStore();
      const eventos = ref([] as Array<Event>);
      
      const loadEvents = async () => {
      try {
        const eventData = await EventService.prototype.getEventsByOrg(user.value.username);
        if (eventData) {
          eventos.value = eventData;
          console.log(eventos.value)
        } else {
          console.error('No se pudieron obtener los eventos');
        }
      } catch (error) {
        console.error('Error al obtener los eventos:', error);
      }
    };

      const fetchUser = async () => {
        try {
          if (isMe) {
            const response = await UserService.prototype.getMe();
            user.value = response;
          } else {
            const response = await UserService.prototype.getOrganizersById(parseInt(router.currentRoute.value.params.id as string, 10));
            user.value = response;
            loadEvents();
          }
        } catch (error) {
          console.error('Error fetching user information:', error);
        }
      };
      
      const handleFileChange = (event: Event) => {
      const files = (event.target as HTMLInputElement).files; // Obtener los archivos seleccionados
      if (files && files.length > 0) {
        // Hacer algo con los archivos, como guardarlos en una variable de datos o procesarlos
        newImage.append('image', files[0]); // Puedes ver la información del archivo seleccionado en la consola
        // Aquí puedes enviar el archivo al servidor o realizar cualquier otra acción necesaria
      }
    };


      const editUser = async () => {
        try {
          // Realiza la llamada al servicio para actualizar el usuario
          user.value.encodedPassword = newPassword;
          await UserService.prototype.updateUser(user.value);
          await UserService.prototype.updateUserImage(newImage);
          // Actualiza la información del usuario después de la edición
          store.dispatch('updateUser', user.value);
          await fetchUser();
        } catch (error) {
          console.error('Error updating user information:', error);
        }
      };
      
      const getLogo = ()=>{
        if (user.value.logo) {
        
        return user.value.logo;
        } else {
            return 'https://github.com/mdo.png'; // URL de imagen predeterminada en caso de que el Blob no esté presente
        }
      }

      onMounted(fetchUser);
  
      return {
        user,
        isMe,
        editUser,
        getLogo,
        newPassword,
        handleFileChange,
        eventos
      };
    }
  };
  </script>
  
<style scoped>

.container-flex{
    margin-left: 5px;
}

.row .logo{
    width: 30%;
   
    padding-top: 20px;
    padding-left: 0px;
}



.row {
    margin-bottom: 30px;
}

p{
    text-align: left;
}

.col-auto {
  display: flex;
  flex-direction: column;
}

input[type="email"],
input[type="text"],
input[type="password"],
textarea {
  width: 100%;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

textarea {
  height: 100px;
}

button {
  padding: 10px 20px;
  background-color: #007bff;
  color: #fff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button:hover {
  background-color: #0056b3;
}

input[type="file"] {
  display: none;
}

label {
  display: inline-block;
  background-color: #007bff;
  color: #fff;
  padding: 10px 20px;
  border-radius: 4px;
  cursor: pointer;
}

label:hover {
  background-color: #0056b3;
}


</style>
  