import {User} from "../models/User"
import {Event} from "../models/Event"



export class UserService{
    users: User[] | undefined;
    events: Event[] | undefined;
    
    constructor(){}

    getUserInEvent(){
        fetch("localhost://8443/")
            .then(data=> this.users = data as unknown as User[])
            .catch(error=>console.error("Me cago en todo no va", error));
            
    }


    public async getOrganizers(){
        try{
            const response = await fetch('/api/clients/organizers', {
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
            return datos
            } catch (err) {
                console.error('Error al obtener los eventos', err);
          // Puedes mostrar un mensaje de error al usuario o redirigir a una página de error
            return null;
            }
    }
    public async getOrganizersById(id:number){
        try{
            const response = await fetch('/api/organizers/'+id, {
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
            return datos
            } catch (err) {
                console.error('Error al obtener los eventos', err);
          // Puedes mostrar un mensaje de error al usuario o redirigir a una página de error
            return null;
            }
    }
    public async updateUser(user:User){
        try{
            const response =  await fetch('/api/users/me/',{
                method: 'PUT',
                credentials: 'include',
                headers: {  
                    'Content-Type': 'application/json',
                    "Access-Control-Allow-Origin": "*",
                },
                body:JSON.stringify(user)
            });
            const data = await response.json();
            if (!response.ok) {
                throw new Error(`Error al crear el evento: ${response.status} - ${response.statusText}`);
              }
            return data;
        }catch(error){ console.error('Error al crear el evento:', error)}
    }
    public async updateUserImage(image:FormData){
        try{
            const response =  await fetch('/api/users/me/',{
                method: 'PUT',
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
