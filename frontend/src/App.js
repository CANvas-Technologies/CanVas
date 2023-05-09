import React, { Component } from "react";
import Navbar from "./components/navbar";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import "./App.css";
import Home from "./components/pages/home";
import AboutCanvas from "./components/pages/aboutcanvas";
import UploadPage from "./components/pages/upload";
import GraphsPage from "./components/pages/graphs";
import DeletePage from "./components/pages/delete";
import SignUp from "./components/pages/account";
import CreateAccountPage from "./components/pages/CreateAccountPage";
import "bootstrap/dist/css/bootstrap.min.css";
import LoginPage from "./components/pages/LoginPage";
import AccountPage from "./components/pages/account";

function App() {
  return (
    <>
      <Router>
        <Navbar />
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/about-canvas" element={<AboutCanvas />} />
          <Route path="/upload" element={<UploadPage />} />
          <Route path="/graphs" element={<GraphsPage />} />
          <Route path="/delete" element={<DeletePage />} />
          <Route path="/sign-up" element={<CreateAccountPage />} />
          <Route path="/account" element={<AccountPage />} />
          <Route path="/login" element={<LoginPage />} />
        </Routes>
      </Router>
    </>
  );
}

export default App;
