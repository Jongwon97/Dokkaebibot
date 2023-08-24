import { createSlice, PayloadAction } from '@reduxjs/toolkit'
import { act } from 'react-dom/test-utils'

export interface Comment {
    id: number
    content: string
    createdAt: string
    writerId: number
    writerNickname: string
}

const initialState: Comment[] = []

const commentSlice = createSlice({
    name: 'comment',
    initialState: initialState,
    reducers: {
        setCommentList: (state, action: PayloadAction<Comment[]>) => {
            return [...action.payload]
        },
        addComment: (state, action:PayloadAction<Comment>) => {
            return [...state, action.payload]
        }
    }
})

export const { setCommentList, addComment } = commentSlice.actions
export default commentSlice.reducer;