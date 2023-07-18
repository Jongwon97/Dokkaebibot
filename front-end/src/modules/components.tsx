import React from "react";

const Home = React.lazy(() => import("../pages/home/Home"));
const SignUp = React.lazy(() => import("../pages/members/SignUp"));
const LogIn = React.lazy(() => import("../pages/members/LogIn"));
const Study = React.lazy(() => import("../pages/study/TotalStudy"));

export {
  Home,
  SignUp,
  LogIn,
  Study,
};