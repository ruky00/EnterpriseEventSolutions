<template>

<h1>Crear Organizaciones</h1>
<div class="form-header" v-if="user">
   <form @submit.prevent="registerOrg">    
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
              <button type="button" class="foto"><label for="profileImage" >Añadir Imagen de perfil Organizacion</label></button>
              <input type="file" id="profileImage" @change="handleFileChange1" required>
            </div>
            <div class="form-group">
                <img :src="vistaPreviaImagenPerfil" alt="Imagen de perfil" class="imagen-perfil-container"/>
              
            </div>
          </div>
       
            <div class="row custom-file-input"> 
              <div class="form-group">
             <button type="button" class="foto"><label for="logoImage">Añadir Logo de la Organizacion</label></button>
              <input type="file" id="logoImage" @change="handleFileChange2" class="custom-file-input" required >
            </div>
            <div class="form-group">
                <img :src="vistaPreviaImagenLogo" alt="Logotipo" class="imagen-perfil-container" />
            </div>
        </div>
          </div>
        </div>
            
         
        </div>
        <button type="submit" v-if="!seleccionado" class="btn btn-primary">Registrar Organización</button>
        <button v-else class="btn btn-primary" type="button" disabled>
          <span class="spinner-grow spinner-grow-sm" role="status" aria-hidden="true"></span>
          Cargando...
        </button>
    </form>
</div>
</template>

<script lang="ts">
  import { ref } from 'vue';
  import { User } from '../../models/User';
import { AdminService } from '@/services/admin.service';
import router from '@/router';
export default{
    name:'create-org',
    setup(){
        const user = ref({} as User)
        const newPassword = "qwert1234";
        const newImage = new FormData();
        const newLogo =  new FormData();
        const vistaPreviaImagenPerfil = ref('');
        const vistaPreviaImagenLogo = ref('');
        const seleccionado = ref(false);
        const registerOrg = async ()=>{
           seleccionado.value=true;
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
            seleccionado.value=false;
            router.back();
        }

        

        const handleFileChange1 = (event: Event) => {
        const files = (event.target as HTMLInputElement).files; 
            if (files && files.length > 0) {
               
                vistaPreviaImagenPerfil.value=URL.createObjectURL(files[files.length-1]);
                newImage.append('image',files[files.length-1] ); 
               
            }
            };
        const handleFileChange2 = (event: Event) => {
        const files2 = (event.target as HTMLInputElement).files; 

            if (files2 && files2.length > 0) {
                
                vistaPreviaImagenLogo.value=URL.createObjectURL(files2[files2.length-1]);
                newLogo.append('image',files2[files2.length-1] ); 
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
            vistaPreviaImagenLogo,
            seleccionado

            
        }
    }
}


</script>

<style scoped>

.form-header{
    margin-top: 2%;
    margin-left: 1%;
    border-radius: 15px;
    background: white; 
    box-shadow: #b1b1b1 0px 10px 15px -1px,  #b1b1b1 0px -10px 10px 0px; 
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

label:hover{
  cursor: pointer;
  
}

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
  border-radius: 9px; 
  font-size: 1rem;
  cursor: pointer;
  transition: background-color 0.4s ease-in-out;
  margin-bottom: 1%;
}

.imagen-perfil-container {
  display: block; 
  max-width:50% ; 
  max-height: 200px;
  margin: 0 auto; 
  border: 1px solid #ddd; 
  padding: 5px; 
}


.btn-primary:hover {
  background-color: var(--main-bg-dark);
}

.foto{
  border-radius: 5px;
  background-color: transparent;
  transition: ease 0.5s
}

.foto:hover{
  background-color: var(--main-bg-org);
}

input[type="text"],
input[type="email"] {
  padding: 0.5rem;
  border: 1px solid #ccc; 
  border-radius: 4px;
  font-size: 1rem; 
  width: 100%; 
}


input[type="text"]:focus,
input[type="email"]:focus {
  outline: none; 
  border-color: var(--main-bg-org); 
}


input[type="file"]{
  margin-left:170px;
  opacity:0;
  position: absolute;
  z-index: 1;
}
img{
  border-radius: 15px;
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