export interface User {
  id: number;
  name: string;
  username: string;
  email: string;
}


export interface AuthUser {
    id: string;
    email: string;
    role: string;
}