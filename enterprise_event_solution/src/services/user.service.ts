import {User} from "../models/User"
import {Event} from "../models/Event"
const BASE_URL = "/api/users"


export class UserService{
    users: User[] | undefined;
    events: Event[] | undefined;
    
    constructor(){}

    getUserInEvent(){
        fetch("localhost://8443/"+BASE_URL)
            .then(data=> this.users = data as unknown as User[])
            .catch(error=>console.error("Me cago en todo no va", error));
            
    }


    public async getOrganizerById(){
        try{

            


        }catch(error){

        }

    }
}
