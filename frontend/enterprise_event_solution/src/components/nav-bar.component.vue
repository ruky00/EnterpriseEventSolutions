<template>
  <nav :class="{ 'navbar-transparent': isTransparent && isCollapsed, 'navbar': true, 'fixed-top': true, 'navbar-expand-lg': true }">
    <div class="container-fluid">
      <!-- LOGO -->
      <a class="navbar-brand" href="#"><img src="https://enterpriseeventsolutions.s3.eu-west-2.amazonaws.com/localImages/Logo+naranja.png" alt="" height="90px"></a>
      <!-- LOGO -->
      <button class="navbar-toggler" type="button" @click="toggleCollapse" data-bs-toggle="collapse" data-bs-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
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
        </ul>
      </div>
    </div>
  </nav>
</template>

<script lang="ts">
import { ref, onMounted } from 'vue';

export default {
  name: 'NavigationBar',
  props: {
    userType: {
      type: String,
      required: true
    }
  },
  setup() {
    const isTransparent = ref(true);
    const isCollapsed = ref(true);

    onMounted(() => {
      const handleScroll = () => {
        isTransparent.value = window.scrollY === 0; 
      };

      window.addEventListener('scroll', handleScroll);

     
      return () => {
        window.removeEventListener('scroll', handleScroll);
      };
    });

    const scroll = () => {
      const element = document.getElementById('presentation');
      if (element) {
        element.scrollIntoView({ behavior: 'smooth' });
      }
    };

    const toggleCollapse = () => {
      isCollapsed.value = false;
    };

    return {
      isTransparent,
      isCollapsed,
      scroll,
      toggleCollapse
    };
  },
};
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
  background-color: transparent !important;
}

.navbar-toggler-icon {
  background-image: url("data:image/svg+xml;charset=utf8,%3Csvg viewBox='0 0 30 30' xmlns='http://www.w3.org/2000/svg'%3E%3Cpath stroke='rgba(255, 165, 0, 1)' stroke-width='2' stroke-linecap='round' stroke-miterlimit='10' d='M4 7h22M4 15h22M4 23h22'/%3E%3C/svg%3E");
}

.navbar-toggler{
  border-color: var(--main-bg-org) !important;
}

@media screen and (max-width: 992px) {
  .navbar-nav {
    padding-bottom: 20px; 
  }
}
</style>
