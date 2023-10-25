

import "bootstrap/dist/css/bootstrap.css"
import "bootstrap-icons/font/bootstrap-icons.css"
import Vue,{ createApp } from 'vue'
import App from './App.vue'
import routes from './router'

import 'bootstrap-vue/dist/bootstrap-vue.css'
createApp(App).use(routes).mount('#app')

import "bootstrap/dist/js/bootstrap.js"