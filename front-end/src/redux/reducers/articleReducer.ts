import { createSlice, PayloadAction } from '@reduxjs/toolkit'

export interface Article {
    id: number
    title: string
    content: string
    createdAt: string
    writerId: number
    writerNickname: string
    numLike: number
    numScrap: number
    liked: boolean
    scrapped: boolean,
}
const initialState: Article = {
    id: 0,
    title: "",
    content: "",
    createdAt: "",
    writerId: 0,
    writerNickname: "",
    numLike: 0,
    numScrap: 0,
    liked: false,
    scrapped: false,
}

const articleSlice = createSlice({
    name: 'article',
    initialState: initialState,
    reducers: {
        setArticleData: (state, action: PayloadAction<Article>) => {
            return {
                ...action.payload,
            }
        },
        toggleArticleLike: (state, action: PayloadAction<void>) => {
            return {
                ...state,
                numLike: state.numLike + (state.liked ? -1:1),
                liked: !state.liked,
            }
        },
        toggleArticleScrap: (state, action: PayloadAction<void>) => {
            return {
                ...state,
                numScrap: state.numScrap + (state.scrapped ? -1 : 1),
                scrapped: !state.scrapped
            }
        },
    }
})

export const { setArticleData, toggleArticleLike, toggleArticleScrap } = articleSlice.actions
export default articleSlice.reducer;