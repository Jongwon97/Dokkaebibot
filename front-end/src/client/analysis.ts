import { client, clientWithToken } from "./client";

export interface Ranker {
    nickname: string,
    iconNumber: number,
    timeSum: string
}

const analysisURL = 'analysis/'

export const getGraphData = (start:string, end:string) => {
    return clientWithToken().get(analysisURL +  start + '/' + end + '/graph/check')
}

export const getRanker = () => {
    return client().get(analysisURL + 'ranker')
}