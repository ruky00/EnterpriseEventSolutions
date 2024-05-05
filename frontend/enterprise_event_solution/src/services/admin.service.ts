import { User } from "@/models/User";
import {Event} from "../models/Event"
const BASE_URL = "/api/admin"
export class AdminService{

    constructor(){}

    public async getUsersMonth(){
        try {
            const response = await fetch(BASE_URL+'/users/graphics/users', {
                method: 'GET',
                credentials: 'include',
                headers: {
                    'Content-Type': 'application/json',
                    "Access-Control-Allow-Origin": "*",
                },
            });
            if (!response.ok) {
                throw new Error(`Error al obtener la información del usuario: ${response.status} - ${response.statusText}`);
              }
            const datos = await response.json();  
            console.log(datos);
            return datos;
            } catch (err) {
                console.error('Error al obtener los eventos', err);
          // Puedes mostrar un mensaje de error al usuario o redirigir a una página de error
          return null;
            }
    
    }
    public async getUsersRole(){
        try {
            const response = await fetch(BASE_URL+'/users/roles', {
                method: 'GET',
                credentials: 'include',
                headers: {
                    'Content-Type': 'application/json',
                    "Access-Control-Allow-Origin": "*",
                },
            });
            if (!response.ok) {
                throw new Error(`Error al obtener la información del usuario: ${response.status} - ${response.statusText}`);
              }
            const datos = await response.json();  
            console.log(datos);
            return datos;
            } catch (err) {
                console.error('Error al obtener la info del usuario', err);
          // Puedes mostrar un mensaje de error al usuario o redirigir a una página de error
          return null;
            }
    
    }

    public async getEventsMonth(){
        try {
            const response = await fetch(BASE_URL+'/users/graphics/events', {
                method: 'GET',
                credentials: 'include',
                headers: {
                    'Content-Type': 'application/json',
                    "Access-Control-Allow-Origin": "*",
                },
            });
            if (!response.ok) {
                throw new Error(`Error al obtener la información del usuario: ${response.status} - ${response.statusText}`);
              }
            const datos = await response.json();  
            console.log(datos);
            return datos;
            } catch (err) {
                console.error('Error al obtener la info del usuario', err);
          // Puedes mostrar un mensaje de error al usuario o redirigir a una página de error
          return null;
            }
    
    }

    public async postOrganizer(user: User){
        try {
            const response = await fetch(BASE_URL+'/organizers/', {
                method: 'POST',
                credentials: 'include',
                headers: {
                    'Content-Type': 'application/json',
                    "Access-Control-Allow-Origin": "*",
                },
                body: JSON.stringify(user)
            });
            if (!response.ok) {
                throw new Error(`Error al obtener la información del usuario: ${response.status} - ${response.statusText}`);
              }
            const datos = await response.json();  
            console.log(datos);
            return datos
            } catch (err) {
                console.error('Error al obtener la info del usuario', err);
          // Puedes mostrar un mensaje de error al usuario o redirigir a una página de error
          return null;
            }
    
    }
    public async postOrgImage(image:FormData,id:number){
        try{
            const response =  await fetch(BASE_URL+'/organizers/'+id+'/images',{
                method: 'POST',
                credentials: 'include',
                headers: {  
                    "Access-Control-Allow-Origin": "*",

                },
                body:image
            });
            const data = await response.json();
            if (!response.ok) {
                throw new Error(`Error al crear el evento: ${response.status} - ${response.statusText}`);
              }
            return data;
        }catch(error){ console.error('Error al crear el evento:', error)}
    }
    public async postOrgLogo(image:FormData,id:number){
        try{
            const response =  await fetch(BASE_URL+'/organizers/'+id+'/images/logo',{
                method: 'POST',
                credentials: 'include',
                headers: {  
                    "Access-Control-Allow-Origin": "*",

                },
                body:image
            });
            const data = await response.json();
            if (!response.ok) {
                throw new Error(`Error al crear el evento: ${response.status} - ${response.statusText}`);
              }
            return data;
        }catch(error){ console.error('Error al crear el evento:', error)}
    }





}