import { combineReducers, configureStore } from '@reduxjs/toolkit';
import { persistReducer } from 'redux-persist';
import storage from "redux-persist/lib/storage"
import memberReducer from './reducers/memberReducer';
import articleReducer from './reducers/articleReducer';
import commentReducer from './reducers/commentReducer';
import studyroomReducer from './reducers/studyroomReducer';
import StudymemberReducer from './reducers/StudymemberReducer';
import friendReducer from './reducers/friendReducer';
import persistStore from 'redux-persist/es/persistStore';

const persistConfig = {
  key: "root",
  storage,
  whitelist: ["memberReducer", "articleReducer", "commentReducer"]
}

const rootReducers = combineReducers({
  memberReducer,
  articleReducer,
  commentReducer,
  studyroomReducer,
  StudymemberReducer,
  friendReducer
})

const persistedReducer = persistReducer(persistConfig, rootReducers)

export const store = configureStore({
  reducer: {
    persistedReducer
  },
  middleware: getDefaultMiddleware => getDefaultMiddleware({ serializableCheck: false }),
})

export type RootState = ReturnType<typeof store.getState>
export type AppDispatch = typeof store.dispatch
export const persistor = persistStore(store)