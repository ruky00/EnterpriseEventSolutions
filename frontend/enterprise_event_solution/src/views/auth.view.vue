<template>
  <article>
    <div class="container" :class="{'sign-up-active' : signUp}">
      <div class="arrow-container">
        <router-link to="/">
          <div width="24" height="24" fill="currentColor" class="bi bi-arrow-left"></div>
        </router-link>
      </div>
      <div class="overlay-container">
        <div class="overlay">
          <div class="overlay-left">
            <h2>Welcome Back!</h2>
            <p>Please login with your personal info</p>
            <button class="invert" id="signIn" @click="signUp = !signUp">Sign In</button>
          </div>
          <div class="overlay-right">
            <h2>Hello, Friend!</h2>
            <p>Please enter your personal details</p>
            <button class="invert" id="signUp" @click="signUp = !signUp">Sign Up</button>
          </div>
        </div>
      </div>
      <form class="sign-up" @submit.prevent="registerUser">
        <h2>Create User</h2>
        <div>Use your email for registration</div>
        <input v-model="user.username" type="text" placeholder="Name" />
        <input v-model="user.email" type="email" placeholder="Email" :class="{ 'error-border': registerError !== '' }" />
        <div v-if="registerError !== ''" class="error-mensaje">{{ registerError }}</div>
        <input v-model="user.encodedPassword" type="password" placeholder="Password" />
        <button v-if="seleccionado" class="btn btn-primary" type="button" disabled>
          <span class="spinner-grow spinner-grow-sm" role="status" aria-hidden="true"></span>
          Enviando confirmación...
        </button>
        <button v-else type="submit">Sign Up</button>
      </form>
      <form class="sign-in" @submit.prevent="loginUser">
        <h2>Sign In</h2>
        <div>Use your account</div>
        <input v-model="user.email" type="email" placeholder="Email" :class="{ 'error-border': loginError !== '' }" required/>
        <input v-model="user.encodedPassword" type="password" placeholder="Password" :class="{ 'error-border': loginError !== '' }" required/>
        <div v-if="loginError !== ''" class="error-mensaje">{{ loginError }}</div>
        <button>Sign In</button>
      </form>
      <div v-if="showBanner" class="banner">
        A confirmation email has been sent to your email address. Please check your inbox.
      </div>
    </div>
  </article>
</template>

<script lang="ts">
import { User } from "../models/User";
import { authService } from "../services/auth.service";
import { onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';
import { useStore } from 'vuex';

export default {
  name: "AuthView",
  setup() {
    const user = ref({} as User);
    const signUp = ref(false);
    const loginError = ref("");
    const registerError = ref(""); // Nueva variable para el error de registro
    const showBanner = ref(false); // Nueva variable para mostrar el banner
    const router = useRouter();
    const store = useStore();
    let userType = String;
    const seleccionado = ref(false)

    const loginUser = async () => {
      try {
        await store.dispatch('login', user.value);
        loginError.value = "";
        userType = store.state.userRoles;
        router.push('/user/' + userType.toString().toLowerCase());
      } catch (error) {
        console.log(error);
        if (error instanceof Error) {
          loginError.value = "Usuario o contraseña incorrectos";
        } else {
          loginError.value = "Fallo en otro sitio";
        }
      }
    };

    // Type guard para verificar si el error tiene una propiedad response
    const isAxiosError = (error: unknown): error is { response: { data: { message: string } } } => {
      return typeof error === 'object' && error !== null && 'response' in error;
    };

    const registerUser = async () => {
      try {
        seleccionado.value=true;
        await authService.prototype.register(user.value);
        registerError.value = ""; // Limpia cualquier error previo
        showBanner.value = true; // Muestra el banner
        setTimeout(() => { showBanner.value = false; }, 5000); // Oculta el banner después de 5 segundos
      } catch (error: unknown) {
        if (isAxiosError(error) && error.response && error.response.data && error.response.data.message) {
          registerError.value = "Correo en uso";
        } else if (error instanceof Error) {
          registerError.value = error.message;
        } else {
          registerError.value = "Error al registrar";
        }
      }
      seleccionado.value=false;
    };
    const checkAuthenticated =  () => {
      try {
        // Obtener información de autenticación del store o servicio adecuado


        if (store.state.isAuthenticated) {
          userType = store.state.userRoles;
          // Si el usuario ya está autenticado, redirigir a la página de inicio
          router.push('/user/' + userType.toString().toLowerCase());
        }
      } catch (error) {
        console.error("Error al comprobar la autenticación:", error);
      }
    };

    onMounted(() => {
      checkAuthenticated()
    });

    return {
      user,
      signUp,
      loginError,
      registerError, // Devuelve la nueva variable
      showBanner, // Devuelve la nueva variable
      loginUser,
      registerUser,
      store,
      seleccionado
    };
  },
};
</script>

<style scoped>
@import "../assets/styles.css";

.arrow-container {
  position: absolute;
  top: 10px;
  left: 10px;
  z-index: 999; /* Asegúrate de que la flecha esté encima del contenido */
}

article {
  padding-bottom: 20%;
  padding-top: 10%;
  background: var(--main-bg-dark);

}

.container {
  margin-top: 100px;
  position: relative;
  width: 768px;
  height: 480px;
  border-radius: 10px;
  overflow: hidden;
  box-shadow: 0 15px 30px rgba(0, 0, 0, .2), 0 10px 10px rgba(0, 0, 0, .2);
  background: linear-gradient(to bottom, #efefef, #ccc);
}

.banner {
  position: fixed; /* Cambiado a fixed para posicionarlo en la parte superior de la pantalla */
  top: 0; /* Posición en la parte superior */
  left: 50%;
  transform: translateX(-50%);
  background-color: var(--main-bg-org);
  color: var(--main-bg-ultra);
  padding: 10px 20px;
  border-radius: 5px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
  font-size: 1rem;
  font-weight: bold;
  z-index: 1000;
}

.error-mensaje {
  color: #ff7300;
  margin-top: 10px;
}

input.error-border {
  border-color: #ff0000;
}

.arrow-container {
  position: absolute;
  top: 10px;
  left: 10px;
  z-index: 999; /* Asegúrate de que la flecha esté encima del contenido */
}

article {
  padding-bottom: 20%;
  padding-top: 10%;
  margin-top: 0px;
  background: var(--main-bg-dark);
  /* o el valor que prefieras, como 'contain' */
  /* ajusta la altura según tus necesidades */
}

.container {
  margin-top: 100px;
  position: relative;
  width: 768px;
  height: 480px;
  border-radius: 10px;
  overflow: hidden;
  box-shadow: 0 15px 30px rgba(0, 0, 0, .2), 0 10px 10px rgba(0, 0, 0, .2);
  background: linear-gradient(to bottom, #efefef, #ccc);
}

.container .overlay-container {
  position: absolute;
  top: 0;
  left: 50%;
  width: 50%;
  height: 100%;
  overflow: hidden;
  transition: transform 0.5s ease-in-out;
  z-index: 100;
}

.container .overlay {
  position: relative;
  left: -100%;
  height: 100%;
  width: 200%;
  background: linear-gradient(to bottom right, var(--main-bg-org), var(--main-bg-light));
  color: #fff;
  transform: translateX(0);
  transition: transform 0.5s ease-in-out;
}

.container .overlay-left {
  position: absolute;
  top: 40px;
  display: flex;
  align-items: center;
  justify-content: space-around;
  flex-direction: column;
  padding: 70px 40px;
  width: calc(60% - 80px);
  height: calc(100% - 140px);
  text-align: center;
  transform: translateX(-20%);
  transition: transform 0.5s ease-in-out;
}

.container .overlay-right {
  margin-top: 40px;
  position: absolute;
  top: 0;
  display: flex;
  align-items: center;
  justify-content: space-around;
  flex-direction: column;
  padding: 70px 40px;
  width: calc(60% - 80px);
  height: calc(100% - 140px);
  text-align: center;
  transform: translateX(0);
  transition: transform 0.5s ease-in-out;
  right: 0;
}

h2 {
  margin: 0;
}

p {
  margin: 20px 0 30px;
}

a {
  color: #222;
  text-decoration: none;
  margin: 15px 0;
  font-size: 1rem;
}

button {
  border-radius: 20px;
  border: 1px solid var(--main-bg-dark);
  background-color: var(--main-bg-org);
  color: var(--main-bg-ultra);
  font-size: 1rem;
  font-weight: bold;
  padding: 10px 40px;
  letter-spacing: 1px;
  text-transform: uppercase;
  cursor: pointer;
  transition: transform 0.6s ease-;
}


button:active {
  transform: scale(0.9);
}

button:hover{
  background-color: var(--main-bg-org-hover);
}

button:focus {
  outline: none;
}

button.invert {
  background-color: transparent;
  border-color: #fff;
}

form {
  position: absolute;
  top: 0;
  display: flex;
  align-items: center;
  justify-content: space-around;
  flex-direction: column;
  padding: 90px -26px;
  width: calc(66% - 120px);
  height: calc(130% - 180px);
  text-align: center;
  transition: all 0.5s ease-in-out;
}

form div {
  font-size: 1rem;
}

form input {
  background-color: #eee;
  border: 1px solid #ddd; /* Cambiado para resaltar el borde */
  padding: 8px 15px;
  margin: 6px 0;
  width: calc(100% - 30px);
  border-radius: 15px;
  box-shadow: inset 0 1px 2px rgba(0, 0, 0, .4), 0 -1px 1px #fff, 0 1px 0 #fff;
  overflow: hidden;
  transition: border-color 0.3s ease-in-out; /* Transición para resaltar el cambio de color del borde */
}

form input:focus {
  outline: none;
  background-color: #fff;
  border-color: var(--main-bg-org-hover);
}

form input.error-border {
  border-color: #ff0000;
}

form .error-mensaje {
  color: #ff7300;
  margin-top: 10px;
}

.sign-in {
  left: 0;
  z-index: 2;
}

.sign-up {
  left: 0;
  z-index: 1;
  opacity: 0;
}

.sign-up-active .sign-in {
  transform: translateX(100%);
  opacity: 0;
}

.sign-up-active .sign-up {
  transform: translateX(100%);
  opacity: 1;
  z-index: 5;
  animation: show 0.5s;
}

.sign-up-active .overlay-container {
  transform: translateX(-100%);
}

.sign-up-active .overlay {
  transform: translateX(50%);
}

.sign-up-active .overlay-left {
  transform: translateX(0);
}

.sign-up-active .overlay-right {
  transform: translateX(20%);
}

@keyframes show {
  0% {
    opacity: 0;
    z-index: 1;
  }
  49% {
    opacity: 0;
    z-index: 1;
  }
  50% {
    opacity: 1;
    z-index: 10;
  }
}



</style>
