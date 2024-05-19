export interface Event{
    id?:number;
    name?: string;
    description: string;
    price: number;
    max_capacity: number;
    current_capacity: number;
    date: Date;
    privateEvent:boolean;
    organization:{
        id:number,
        name:string
    }
}

