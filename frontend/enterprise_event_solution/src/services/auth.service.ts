import {User} from '../models/User'
import {EventService} from '../services/event.service'

const BASE_URL = "/api/auth"

export class authService{

    public constructor() {
        
    }
    
   public async login(user: User) {
    try {
        const response = await fetch(BASE_URL + '/login', {
            method: 'POST',
            credentials: 'include',
            headers: {
                'Content-Type': 'application/json',
                "Access-Control-Allow-Origin": "*",
            },
            body: JSON.stringify({
                username: user.email,
                password: user.encodedPassword,
            }),
        });

        if (!response.ok) {
            throw new Error(`Error en la solicitud: ${response.status} - ${response.statusText}`);
        }

        const data = await response.json();

        // AQUÍ PUEDES VER QUE SE PIDEN TUS EVENTOS Y FUNCIONA (SALE VACÍO PORQUE NO HAY NINGUNO)
        const eventData = await EventService.prototype.getMyEvents();

        return data;
    } catch (error) {
        throw new Error();
    }
}

public async logout() {
    try {
        const response = await fetch(BASE_URL + '/logout', {
            method: 'POST',
            credentials: 'include',
            headers: {
                'Content-Type': 'application/json',
                "Access-Control-Allow-Origin": "*",
            },
           
        });

        if (!response.ok) {
            throw new Error(`Error en la solicitud: ${response.status} - ${response.statusText}`);
        }
        const data = await response.json();
        
    } catch (error) {
        throw new Error();
    }
}
   
    
    public  async getUserInfoFromServer(): Promise<User | null> {
       
        try {
          const respuesta = await fetch('/api/users/me', {
            method: 'GET',
            headers: {
              'Content-Type': 'application/json'
          },
            credentials: 'include'
          });
    
          if (!respuesta.ok) {
            throw new Error(`Error al obtener la información del usuario: ${respuesta.status} - ${respuesta.statusText}`);
          }
    
          const datos = await respuesta.json();

          
          return datos; 
        } catch (error) {
          
          console.error('Error al obtener la información del usuario:', error);
          
          return null;
        }
      }


    public async register(user: User){
        try {
            const response = await fetch('/api/users/', {
                method: 'POST', 
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    username: user.username,
                    email: user.email,
                    encodedPassword: user.encodedPassword
                }),
                credentials: 'include'
            });
    
            if (!response.ok) {
                throw new Error('Inténtelo de nuevo');
            }
    
            const data = await response.json();
            return data;
        } catch (error) {
            throw new Error('El usuario ya existe en el sistema');
        }
    }
}