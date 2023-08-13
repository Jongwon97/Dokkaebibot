import { client, clientWithToken } from "./client";

const deviceURL = 'device/'

export const registDevice = (serialNumber:string) => {
    return clientWithToken().post(deviceURL +  serialNumber + '/check')
}

