<template>

<h1>Organization Creation</h1>

<div class="form-header" v-if="user">
        <h2>Crear Perfil para Organización</h2>
      {{ user.email }}
      <div class="form-body">
        <div class="row">
          <!-- Columna izquierda -->
          <div class="col-md-6">
            <div class="form-group">
              <label for="email">Correo de la Organizacion</label>
              <input type="email" id="email" v-model="user.email" class="form-control" placeholder="Ingresa tu correo electrónico" required>
            </div>
            <div class="form-group">
              <label for="username">Nombre de la Organizacion</label>
              <input type="text" id="username" v-model="user.username" class="form-control" placeholder="Ingresa tu nombre de usuario" required>
            </div>
            <div class="form-group">
              <label for="description">Descripción de la Organizacion</label>
              <textarea type="text" id="description" v-model="user.description" class="form-control" placeholder="Ingresa información sobre la empresa" required></textarea>
            </div>
            <div class="form-group">
              <label for="newPassword">Contraseña ya incluida</label>
              <input type="password" id="newPassword" v-model="newPassword" class="form-control" placeholder="La contraseña es qwer1234" disabled>
            </div>

          </div>
          <!-- Columna derecha -->
          <div class="col-md-6">
            <div class="row custom-file-input">
             <div class="form-group">
              <label for="profileImage" >Imagen de perfil Organizacion</label>
              <input type="file" id="profileImage" @change="handleFileChange1" required>
            </div>
            <div class="form-group">
                <img :src="vistaPreviaImagenPerfil" alt="Imagen de perfil" class="imagen-perfil"/>
              
            </div>
          </div>
       
            <div class="row custom-file-input"> 
              <div class="form-group">
              <label for="logoImage">Logo de la Organizacion</label>
              <input type="file" id="logoImage" @change="handleFileChange2" class="custom-file-input" required >
            </div>
            <div class="form-group">
                <img :src="vistaPreviaImagenLogo" alt="Logotipo" class="imagen-perfil" />
            </div>
        </div>
          </div>
        </div>
            
         
        </div>
        <button @click="registerOrg" class="btn btn-primary">Registrar Organizador</button>
    
</div>
</template>

<script lang="ts">
  import { ref } from 'vue';
  import { User } from '../../models/User';
import { AdminService } from '@/services/admin.service';
export default{
    name:'create-org',
    setup(){
        const user = ref({} as User)
        const newPassword = "qwert1234";
        const newImage = new FormData();
        const newLogo =  new FormData();
        const vistaPreviaImagenPerfil = ref('');
        const vistaPreviaImagenLogo = ref('');
        const registerOrg = async ()=>{
            user.value.encodedPassword = newPassword;
            try{
                const userData = await AdminService.prototype.postOrganizer(user.value)
                user.value = userData
                await AdminService.prototype.postOrgImage(newImage,user.value.id)
                await AdminService.prototype.postOrgLogo(newLogo,user.value.id)

                alert('Se ha registrado correctamente el usuario')
            }catch(error){
                console.log(error);
                alert('El usuario que intentas crear ya está en el sistema')
            }
        }

        

        const handleFileChange1 = (event: Event) => {
        const files = (event.target as HTMLInputElement).files; // Obtener los archivos seleccionados
            if (files && files.length > 0) {
                // Hacer algo con los archivos, como guardarlos en una variable de datos o procesarlos
                vistaPreviaImagenPerfil.value=URL.createObjectURL(files[files.length-1]);//Mostrar la imagen en un img
                newImage.append('image',files[files.length-1] ); // Puedes ver la información del archivo seleccionado en la consola
                // Aquí puedes enviar el archivo al servidor o realizar cualquier otra acción necesaria
            }
            };
        const handleFileChange2 = (event: Event) => {
        const files2 = (event.target as HTMLInputElement).files; // Obtener los archivos seleccionados

            if (files2 && files2.length > 0) {
                // Hacer algo con los archivos, como guardarlos en una variable de datos o procesarlos
                vistaPreviaImagenLogo.value=URL.createObjectURL(files2[files2.length-1]);
                newLogo.append('image',files2[files2.length-1] ); // Puedes ver la información del archivo seleccionado en la consola
                // Aquí puedes enviar el archivo al servidor o realizar cualquier otra acción necesaria
            }
            else{
                console.log('No se ha subido imagen ')
            }
            };
        
        



        return{
            user,
            registerOrg,
            newPassword,
            handleFileChange1,
            handleFileChange2,
            vistaPreviaImagenPerfil,
            vistaPreviaImagenLogo

            
        }
    }
}


</script>

<style scoped>

.form-header{
    margin-top: 2%;
    margin-left: 1%;
    border-radius: 15px;
    background: linear-gradient(to top right, var(--main-bg-light), var(--main-bg-org)); 
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);    
    margin-right: 1%;
}

.form-body{
    margin-top: 2%;
    margin-left: 1%;
}


    p, h1{
  text-align: left;
  font-family: 'Franklin Gothic', 'Arial Narrow', Arial, sans-serif;
  margin-top: 1%;
}

p{
  font-size: large;
  text-align: left;
}

textarea, label,input,h2{
  font-family: 'Franklin Gothic', 'Arial Narrow', Arial, sans-serif;
  color: black;
}

label

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
  padding: 0.75rem 1.5rem;
  background-color: var(--main-bg-org); 
  color: #fff; 
  border: none; 
  border-radius: 4px; 
  font-size: 1rem;
  cursor: pointer;
  transition: background-color 0.4s ease-in-out;
  margin-bottom: 1%;
}


.btn-primary:hover {
  background-color: #0056b3;
}

input[type="text"],
input[type="email"] {
  padding: 0.5rem;
  border: 1px solid #ccc; 
  border-radius: 4px;
  font-size: 1rem; 
  width: 100%; 
}

/* Styles for focus state (when user clicks on input) */
input[type="text"]:focus,
input[type="email"]:focus {
  outline: none; /* Removes default outline */
  border-color: var(--main-bg-org); /* Orange border on focus */
}


input[type="file"]{
  margin-left:170px;
  opacity:0;
  position: absolute;
  z-index: -1;
}

.custom-file-input:hover {
  
  resize:20px;
  

}

.profile-image{

  max-width: 100%;
  height: auto;
  margin-top: 10px;
  border: 1px solid #ccc;
}

</style>