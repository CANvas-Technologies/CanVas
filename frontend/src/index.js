import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import App from "./App";

import { initializeApp } from "firebase/app";

// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
const firebaseConfig = {
  apiKey: "AIzaSyARIr2mLNOODaNK9kQE5VpL87wh0rPgWs0",
  authDomain: "canvas-e5ab9.firebaseapp.com",
  projectId: "canvas-e5ab9",
  storageBucket: "canvas-e5ab9.appspot.com",
  messagingSenderId: "907569682459",
  appId: "1:907569682459:web:82c9a1575ff57c3fc440be",
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);
