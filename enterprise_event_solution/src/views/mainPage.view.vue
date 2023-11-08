<template>
  <NavigationBar
   user-type="none"
  />
  <div class="container-fluid main-content">
    <div class="row main-content0" id="main-text">
      <h1>Your space to organize or attend your Company events</h1>
    </div>
    <div class="row main-content1" id="get-started-button"><router-link to="/login"><button class="btn btn-primary" type="button">Get started</button></router-link></div>
  </div>
  <div class="container-fluid presentation">
    <div class="row presentation">
      <div class="col-4"><img src="../assets/carlosFernandez.png" alt="Carlos Fernandez Lopez"></div>
      <div class="col-8"><p><b>Who created this services...?</b>, as a DFP, I've launched <b>Enterprise Event Solutions</b>, a one-click platform where companies effortlessly manage employee attendance for their crucial meetings and large-scale events. 
    <b>Say goodbye to worries about access and attendance</b>. It's all just a click away!</p></div>
    </div>
  </div>
  <div class="container-fluid description">
        <div class="row">
          <div class="col-8"><p><b>Welcome to Enterprise Event Solutions!</b> We're your go-to platform for seamless event management. 
<b>Companies</b> can create a company profile, effortlessly upload their events, and manage attendance effortlessly. On the flip side, Users can explore these events, secure their spot, and purchase 
tickets hassle-free. <b>Experience the simplicity of event coordination with us!</b>"</p></div>
          <div class="col-4"><img src="../assets/evento1.png" alt="Evento presentacion"></div>
        </div>
  </div>
  <div class="container-fluid footer">
    <div class="row">
      <div class="col-6"><p><b>Created By</b><br>Carlos Fernandez Lopez c.fernandezl.2019@alumnos.urjc.es</p></div>
      <div class="col-6">
        <ul class="nav">
          <li>
            <router-link style="text-decoration: none; color: inherit;" to="/">Home</router-link>
          </li>
          <li>
            <router-link style="text-decoration: none; color: inherit;" to="/">About us</router-link>
          </li>
          <li>
            <router-link style="text-decoration: none; color: inherit;" to="/">Get started</router-link>
          </li>
          <li>
            <router-link style="text-decoration: none; color: inherit;" to="/login">Log in</router-link>
          </li>
        </ul>
      </div>
    </div>
  </div>


</template>

<script lang="ts">

import NavigationBar from '../components/nav-bar.component.vue'

export default ({
  name:'MainPage',
  components:{
    NavigationBar
  }
})

</script>

<style>

.main-content{
  min-height: 450px;
  background: var(--main-bg-dark);
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
}

#main-text{
  margin-top: 50px;
  text-align: center;
  padding-left: 400px;
  padding-right: 400px;
}

h1{
  text-align: center;
  font-family:'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;
  font-size: 70px;
  font-style: italic;
  font-weight: 400;
  line-height: normal;
  color: var(--main-bg-ultra);
}

#get-started-button{
 justify-content: center;
}

.btn{    
  width: 300px;
  height: 40px;
  border-color: var(--main-bg-org-hover);
  border-radius: 24px;
  background: var(--main-bg-org);
}

.btn:hover{
  background: var(--main-bg-org-hover);
  border-color: var(--main-bg-org-hover);
}

.presentation{
  background-color: #fff;
  min-height: 600px;
}

.row .presentation{
  display: flex;
  flex-direction: column;
}

.col-8{
 display: flex;
 min-height: 500px;
 align-items: center;
 margin-top: -50px;
}


.col-8 p{
  color: black; 
  font-size: 30px;
  font-family: 'Franklin Gothic', 'Arial Narrow', Arial, sans-serif;
}

b{
  font-size: 33px;
}

.col-4{ 
  min-width: 340px;
  margin-top: -130px;
}

img{
  border-radius: 20px;
  
}

.description{
  min-height: 600px;
  background: var(--main-bg-org);
  display: flex;
}

.description .row{
  display: flex;
  justify-content: center;
}

.description .row .col-4 img{
  width: 450px;
  height: 240px;
}

.footer{
  min-height: 200px;
  background: var(--main-bg-dark);
  display: flex;
}

.footer .row{
  width: inherit;
  display: flex;
  align-items: center;
}

.col-6 p{
  color: var(--main-bg-ultra);
}

.nav{
  list-style: none;
  color: var(--main-bg-ultra);
  display: block;
}

.nav :hover{
  color: var(--main-bg-org);
}

li{
  margin-top: 10px;
}



@media screen and (max-width: 1050px) {
  .col-4 {
    margin-top: 10px;
    margin-bottom: 10px;
  }
  .col-8{
    margin-top: -50px;
    width: auto;
    height: auto;
  }
  .col-8 p{
    font-size:20px;
  }
  b{
    font-size: 27px;
  }
}

</style>
