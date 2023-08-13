import { client, clientWithToken } from "./client";

const analysisURL = 'analysis/'

export const getGraphData = (start:string, end:string) => {
    return clientWithToken().get(analysisURL +  start + '/' + end + '/graph/check')
}

