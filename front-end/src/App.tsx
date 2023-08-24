import { Suspense } from 'react';
import { Route, Routes, Navigate } from 'react-router-dom';

import './App.css';
//theme
import 'primereact/resources/themes/lara-light-indigo/theme.css';
//core
import 'primereact/resources/primereact.min.css';
import 'primeicons/primeicons.css';

import AppLayout from './components/AppLayout';
import Loading from './components/Loading';
import PrivateRoutes from './modules/PrivateRoutes';
import {
  Home,
  ConnectBot,
  SignUp,
  LogIn,
  AboutService,
  Notice,
  NoticeCreate,
  Privacy,
  FAQ,
  QnA,
  Study,
  Community,
  Bridge,
  BridgeBridge,
  ArticleTotal,
  ArticleCreate,
  ArticleDetail,
  StudyroomTotal,
  StudyroomDetail,
  StudyroomEnterConfirm,
  MyPage,
  NotFound,
} from './modules/components';
import NoticeDetail from './pages/admin/NoticeDetail';
import { useSelector } from 'react-redux';
import { RootState } from './redux/store';

const App: React.FC = () => {
  const user = useSelector((state: RootState) => state.persistedReducer.memberReducer);
  const loggedIn = () => {
    return user.id !== 0 && localStorage.getItem('accessToken');
  };

  return (
    <AppLayout>
      <Suspense fallback={<Loading />}>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/members/register" element={<SignUp />} />
          <Route path="/members/login" element={<LogIn />} />

          <Route path="/about" element={<AboutService />} />
          <Route path="/notice" element={<Notice />} />
          <Route path="/privacy" element={<Privacy />} />
          <Route path="/faq" element={<FAQ />} />

          <Route path="/community" element={<Community />} />

          <Route path="/bridge" element={<Bridge />} />
          <Route path="/bridge/bridge" element={<BridgeBridge />} />

          <Route element={<PrivateRoutes />}>
            <Route path="/connect" element={<ConnectBot />} />
            <Route path="/members" element={<MyPage />} />

            <Route path="/study" element={<Study />} />

            <Route path="/community/article" element={<ArticleTotal />} />
            <Route path="/community/article/create" element={<ArticleCreate />} />
            <Route path="/community/article/:id" element={<ArticleDetail />} />

            <Route path="/community/studyroom" element={<StudyroomTotal />} />
            <Route path="/community/studyroom/:id" element={<StudyroomDetail />} />
            <Route path="/community/studyroom/check" element={<StudyroomEnterConfirm />} />

            <Route path="/notice/create" element={<NoticeCreate />} />
            <Route path="/notice/:id" element={<NoticeDetail />} />
            <Route path="/qna" element={<QnA />} />
          </Route>

          <Route path="*" element={<NotFound />} />
        </Routes>
      </Suspense>
    </AppLayout>
  );
};

export default App;
