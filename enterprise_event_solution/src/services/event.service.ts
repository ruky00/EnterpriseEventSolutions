import {Event} from "../models/Event"
const BASE_URL = "/api/users"

export class EventService{
    events: Event[] | undefined;

    constructor(){}

    public async getMyEvents(){
        try {
            const response = await fetch('https://localhost:8443/api/organizers/events', {
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
            return datos;
            } catch (err) {
                console.error('Error al obtener los eventos', err);
          // Puedes mostrar un mensaje de error al usuario o redirigir a una página de error
          return null;
            }
    
    }


    public async getEventById(id: number){
        try{
            const response = await fetch('https://localhost:8443/api/events/'+id, {
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
            return datos;
            } catch (err) {
                console.error('Error al obtener los eventos', err);
          // Puedes mostrar un mensaje de error al usuario o redirigir a una página de error
          return null;
            }
        }

    public async updateEvent(event: Event){
        try{
            const response = await fetch('https://localhost:8443/api/organizers/events/'+event.id, {
                method: 'PUT',
                credentials: 'include',
                headers: {
                    'Content-Type': 'application/json',
                    "Access-Control-Allow-Origin": "*",
                },
                body: JSON.stringify(event)
            });
            if (!response.ok) {
                throw new Error(`Error al actualizar el evento: ${response.status} - ${response.statusText}`);
            }
        }catch(error){ console.error('Error al actualizar el evento:', error);}
    }

    public async deleteEvent(id: number){
        try{
            const response = await fetch('https://localhost:8443/api/organizers/events/'+id, {
                method: 'DELETE',
                credentials: 'include',
                headers: {
                    'Content-Type': 'application/json',
                    "Access-Control-Allow-Origin": "*",
                },
            });
            if (!response.ok) {
                throw new Error(`Error al borrar el evento: ${response.status} - ${response.statusText}`);
              }
        }catch(error){ console.error('Error al eliminar el evento:', error)}
   }

   public async createEvent(event: Event){
        try{
            const response =  await fetch('https://localhost:8443/api/organizers/events/',{
                method: 'POST',
                credentials: 'include',
                headers: {
                    'Content-Type': 'application/json',
                    "Access-Control-Allow-Origin": "*",
                },
                body: JSON.stringify(event)
            });
            if (!response.ok) {
                throw new Error(`Error al crear el evento: ${response.status} - ${response.statusText}`);
              }
        }catch(error){ console.error('Error al crear el evento:', error)}

   }

}
