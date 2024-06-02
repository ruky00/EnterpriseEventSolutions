<template>
<nav :class="{ 'navbar-transparent': isTransparent }" class=" navbar fixed-top navbar-expand-lg">
  <div class="container-fluid">
    <!--LOGO-->
    <a class="navbar-brand" href="#"><img src="https://enterpriseeventsolutions.s3.eu-west-2.amazonaws.com/localImages/Logo+naranja.png" alt="" height="90px"></a>
     <!--LOGO-->
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNavDropdown">
      <ul class="navbar-nav">
        <li class="nav-item">
          <router-link style="text-decoration: none; color: inherit;" to="/"><i class="bi bi-house-door"></i> Home</router-link>
        </li>
        <li class="nav-item">
         <a style="text-decoration: none; color: inherit;" @click="scroll"><i class="bi bi-person-raised-hand"></i> About us</a>
        </li>
        <li class="nav-item">
          <router-link style="text-decoration: none; color: inherit;" to="/login"><i class="bi bi-person-circle"></i> Log in</router-link>
        </li>
        <!--
        <li class="nav-item dropdown">
          <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
            Dropdown link
          </a>
          <ul class="dropdown-menu">
            <li><a class="dropdown-item" href="#">Action</a></li>
            <li><a class="dropdown-item" href="#">Another action</a></li>
            <li><a class="dropdown-item" href="#">Something else here</a></li>
          </ul>
        </li>
        -->
      </ul>
    </div>
  </div>
</nav>

</template>

<script lang="ts">
import { ref, onMounted } from 'vue';
export default({
    name: 'NavigationBar',
    props:{
        userType:{
            type:String,
            required: true
        }
    },
    setup() {
    const isTransparent = ref(true);

    onMounted(() => {
      const handleScroll = () => {
        isTransparent.value = window.scrollY === 0; // Transparente en scroll Y = 0
      };

      window.addEventListener('scroll', handleScroll);

      // Limpiar el detector de eventos al desmontar el componente
      return () => {
        window.removeEventListener('scroll', handleScroll);
      };
    });

    const scroll= ()=> {
      const element = document.getElementById('presentation');
      if (element) {
        element.scrollIntoView({ behavior: 'smooth' });
      }
    }

    return {
      isTransparent,
      scroll
    };
  },
})



</script>

<style scoped>

@import '../assets/styles.css';

.navbar-brand {
  margin-left: 20px;
  color: var(--main-bg-ultra);
}

.navbar {
  background: var(--main-bg-dark);
  transition: background-color 0.1s ease-in-out;
}

#navbarNavDropdown {
  justify-content: flex-end;
}

.navbar-nav :hover {
  color: var(--main-bg-org);
}

router-link:focus {
  color: var(--main-bg-org);
}

.navbar-nav router-link.show {
  color: var(--main-bg-org);
}

.nav-item {
  margin-left: 30px;
  color: var(--main-bg-ultra);
}

.bi {
  font-size: 1.4em;
}

.navbar-transparent {
  background-color: transparent !important; /* Garantiza la transparencia absoluta */
}
</style>