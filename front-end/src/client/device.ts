import { client, clientWithToken } from "./client";

const deviceURL = 'device/'

export const registDevice = async (serialNumber:string) => {
    return await clientWithToken().post(deviceURL +  serialNumber + '/check')
}

