import { client } from "./client";

const membersURL = 'members/'

export const postMember = ({ email, nickname, password }:
    {email: string, nickname: string, password: string}) => {
    return client.post(membersURL + 'register', { email, nickname, password });
};

export const memberLogin = ({ email, password }:
    {email: string, password: string}) => {
    return client.post(membersURL + 'login', { email, password });
};
