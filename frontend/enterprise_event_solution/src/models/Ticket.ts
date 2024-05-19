import { User } from "./User";
import { Event } from "./Event";

export interface Ticket{
    id:number;
    price:number;
    user:User;
    event:Event;
    qrCode: string;
    creationTime:Date
}