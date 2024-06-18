<template>
  <div class="error-page">
    <h1 class="error-code">{{ errorCode }}</h1>
    <p class="error-message">Acceso no autorizado</p>
    <button @click="goBack" class="back-button">Volver atrás</button>
  </div>
</template>

<script>
export default {
  name: 'error-page',
  props: ['errorCode'], // Propiedad para recibir el código de error
  methods: {
    goBack() {
      if (this.errorCode === 403) {
        window.history.length > 2 ? this.$router.go(-2) : this.$router.push('/');
      } else {
        window.history.length > 1 ? this.$router.go(-1) : this.$router.push('/');
      }
    }
  }
}
</script>
  
  <style scoped>
  .error-page {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    height: 100vh;
    background-color: #f0f0f0; 
  }
  
  .error-code {
    font-size: 8rem; 
    color: var(--main-bg-org); 
  }
  
  .error-message {
    font-size: 2rem; 
    margin-top: 1rem;
  }
  
  .back-button {
    margin-top: 2rem; 
    padding: 1rem 2rem;
    font-size: 1.5rem;
    background-color: #007bff; 
    color: #ffffff;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    transition: background-color 0.3s ease;
  }
  
  .back-button:hover {
    background-color: #0056b3;
  }
  </style>
  