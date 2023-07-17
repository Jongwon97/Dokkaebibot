import client from "./client";

// register
export const register = ({ user_email, user_pw }:{user_email: string, user_pw: string}) => {
    client.post('http://localhost:8080/dokkaebi/api/members/register', { user_email, user_pw });
};

export const login = ({ user_email, user_pw }:{user_email: string, user_pw: string}) => {
    client.post('http://localhost:8080/dokkaebi/api/members/login', { user_email, user_pw });
};