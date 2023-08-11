import { client, clientWithToken } from "./client";

const analysisURL = 'analysis/'

export const getGraphData = (start:string, end:string) => {
	console.log("aaaaaa")
	console.log(start)
	console.log(end)
    return client().get(analysisURL +  start + '/' + end + '/graph')
}

