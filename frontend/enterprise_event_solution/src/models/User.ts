export interface User{
    id: number;
    username: string;
    email: string;
    encodedPassword: string;
    description?: string;
    role: string;
    image: string;
    logo?: string;
}