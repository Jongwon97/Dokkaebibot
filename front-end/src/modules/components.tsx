import React from 'react';

const Home = React.lazy(() => import('../pages/home/Home'));
const ConnectBot = React.lazy(() => import('../pages/home/comp/ConnectBot'));
const SignUp = React.lazy(() => import('../pages/members/SignUp'));
const LogIn = React.lazy(() => import('../pages/members/LogIn'));
const MyPage = React.lazy(() => import('../pages/members/MyPage'));

const AboutService = React.lazy(() => import('../pages/admin/AboutService'));
const Notice = React.lazy(() => import('../pages/admin/Notice'));
const NoticeCreate = React.lazy(() => import('../pages/admin/NoticeCreate'));
const Privacy = React.lazy(() => import('../pages/admin/Privacy'));
const FAQ = React.lazy(() => import('../pages/admin/FAQ'));
const QnA = React.lazy(() => import('../pages/admin/QnA'));

const Study = React.lazy(() => import('../pages/study/StudyTotal'));

const Community = React.lazy(() => import('../pages/community/Community'));
const ArticleTotal = React.lazy(() => import('../pages/community/ArticleTotal'));
const ArticleCreate = React.lazy(() => import('../pages/community/ArticleCreate'));
const ArticleDetail = React.lazy(() => import('../pages/community/ArticleDetail'));

const StudyroomTotal = React.lazy(() => import('../pages/community/StudyroomTotal'));
const StudyroomDetail = React.lazy(() => import('../pages/community/StudyroomDetail'));
const StudyroomEnterConfirm = React.lazy(
  () => import('../pages/community/comp/StudyroomEnterConfirm'),
);

const Bridge = React.lazy(() => import('../pages/Bridge'));
const BridgeBridge = React.lazy(() => import('../pages/BridgeBridge'));

const NotFound = React.lazy(() => import('../components/NotFound'));

export {
  Home,
  ConnectBot,
  SignUp,
  LogIn,
  MyPage,
  AboutService,
  Notice,
  NoticeCreate,
  Privacy,
  FAQ,
  QnA,
  Study,
  Community,
  ArticleTotal,
  ArticleCreate,
  ArticleDetail,
  StudyroomTotal,
  StudyroomDetail,
  StudyroomEnterConfirm,
  Bridge,
  BridgeBridge,
  NotFound,
};
