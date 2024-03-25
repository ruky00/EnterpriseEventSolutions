export interface User{
    username: string;
    email: string;
    encodedPassword: string;
    description?: string;
    role: string;
    image: string;
}