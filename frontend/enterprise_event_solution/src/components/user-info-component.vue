<template>
  <h1 v-if="isMe"> {{ user.username }} profile</h1>
    <div class="container-flex">
      <div v-if="user.role === 'ORGANIZATION' && !isMe" class="row logo">
        <img :src="getLogo()" alt="Logo organizacion" height="80px">
      </div>
      <div v-if="user.role === 'ORGANIZATION' && !isMe" class="row align-items-start">
        <div class="col-auto">
          <h1>Descripción</h1>
        </div>
        </div>
        <div v-if="user.role === 'ORGANIZATION' && !isMe" class="row align-items-start">
        <div class="col-auto">
          <p>{{ user.description }}</p>
        </div>
        </div>
        <div v-if="user.role === 'ORGANIZATION' && !isMe">
        <div class="row"><h4>Eventos de empresa disponibles</h4></div>
        <div  class="col-12" v-for="(evento, index) in eventos" :key="index">
        <event_cards
        :evento="evento"
        :is-org="false"
        >
        </event_cards>
        </div>
        </div>

        
        <div v-if="isMe" class="user-form-container">
      <div class="form-header">
        <h2>Edita tu perfil</h2>
      </div>
      <div class="form-body">
        <div class="row">
          <!-- Columna izquierda -->
          <div class="col-md-6">
            <div class="form-group">
              <label for="email">Email</label>
              <input type="email" id="email" v-model="user.email" class="form-control" placeholder="Ingresa tu correo electrónico">
            </div>
            <div class="form-group">
              <label for="username">Username</label>
              <input type="text" id="username" v-model="user.username" class="form-control" placeholder="Ingresa tu nombre de usuario">
            </div>
            <div v-if="user.role === 'ORGANIZATION'" class="form-group">
              <label for="description">Descripción</label>
              <textarea type="text" id="description" v-model="user.description" class="form-control"></textarea>
            </div>
            <div class="form-group">
              <label for="newPassword">Nueva contraseña</label>
              <input type="password" id="newPassword" v-model="newPassword" class="form-control" placeholder="Ingresa tu nueva contraseña">
            </div>

          </div>
          <!-- Columna derecha -->
          <div class="col-md-6">
            <div class="form-group">
              <label for="newProfileImage">Nueva imagen de perfil</label>
              <input type="file" id="newProfileImage" @change="handleFileChange" class="form-control-file">
            </div>
            <div class="form-group">
              <img v-if="newImage!=null" :src=user.image alt="Imagen de perfil" class="profile-image">
              <img v-else :src="newImage" alt="Imagen de perfil" class="profile-image">
            </div>
          </div>
        </div>
        <button @click="editUser" class="btn btn-primary">Guardar cambios</button>
      </div>
    </div>
  </div>
</template>
  
  <script lang="ts">
  import { ref, onMounted, watch } from 'vue';
  import { useRouter } from 'vue-router';
  import { User } from '../models/User';
  import { UserService } from '@/services/user.service';
  import event_cards from "../components/event-card.component.vue"
  import { useStore } from 'vuex';
  import {EventService} from '../services/event.service'
import { authService } from '@/services/auth.service';


  export default {
    name: 'user_info',
    components:{event_cards},
    setup() {
      const user = ref({} as User);
      const router = useRouter();
      const isMe = ref(router.currentRoute.value.params.id === 'me');
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
          if (isMe.value) {
            const response = await authService.prototype.getUserInfoFromServer();
            if(response){
            user.value = response;
            }
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
        newImage.append('image',files[files.length-1] ); // Puedes ver la información del archivo seleccionado en la consola
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

    watch(() => router.currentRoute.value.params.id, () => {
      isMe.value = router.currentRoute.value.params.id === 'me';
      fetchUser();
    });
      return {
        user,
        isMe,
        editUser,
        getLogo,
        newPassword,
        handleFileChange,
        eventos,
        newImage
      };
    }
  };
  </script>
  
<style scoped>
p, h1,h4{
  text-align: left;
  font-family: 'Franklin Gothic', 'Arial Narrow', Arial, sans-serif;
}

p{
  font-size: large;
  text-align: left;
}

label,input,h2{
  font-family: 'Franklin Gothic', 'Arial Narrow', Arial, sans-serif;
}
.user-form-container{
    margin-left: 1%;
    box-shadow: #b1b1b1 0px 10px 25px 15px,  #b1b1b1 10px -15px 10px 0px; 
    border-radius: 10px 0  10px 0px;;
    margin-right: 1%;
    margin-top: 2%;
}


.row .logo{
    width: 30%;
   
    padding-top: 20px;
    padding-left: 0px;
}



.row {
    margin-bottom: 30px;
}


.col-auto {
  display: flex;
  flex-direction: column;
}

.user-form-container {
  margin-top: 20px;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.form-header {
  margin-bottom: 20px;
}

.form-header h2 {
  margin: 0;
  font-size: 24px;
  color: #333;
}

.form-group {
  margin-bottom: 20px;
}

.form-group label {
  display: block;
  font-weight: bold;
  margin-bottom: 5px;
}

.form-control {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  font-size: 16px;
}

.form-control-file {
  width: 100%;
  padding: 10px;
}

.profile-image {
  width: 100%;
  max-width: 200px;
  height: auto;
  border-radius: 5px;
  margin-top: 10px;
}

.btn-primary {
  padding: 10px 20px;
  background-color: #007bff;
  color: #fff;
  border: none;
  border-radius: 5px;
  font-size: 16px;
  cursor: pointer;
}

.btn-primary:hover {
  background-color: #0056b3;
}

input[type="file"]{
  margin-left:170px;
}

</style>