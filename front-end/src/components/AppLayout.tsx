import React from "react";
import Header from "./Header";
import Footer from './Footer';
import styles from "../styles/components/AppLayout.module.scss"

const AppLayout = ( props:{ children: React.ReactNode }) => {
    return (
      <div>
        <Header />
        <div className={styles.container}>
          { props.children }
        </div>
        <Footer />
      </div>
    );
  };
  
  export default AppLayout;