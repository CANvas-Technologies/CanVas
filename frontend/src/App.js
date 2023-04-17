import React, {Component} from 'react'
import Navbar from './components/navbar';
import {BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import './App.css';
import Home from './components/pages/home';
import AboutCanvas from './components/pages/aboutcanvas';
import UploadPage from './components/pages/upload';
import GraphsPage from './components/pages/graphs';
import SignUp from './components/pages/signup';
import 'bootstrap/dist/css/bootstrap.min.css';


function App() {
  return (
    <>
    <Router>
     <Navbar />
     <Routes>
        <Route path ='/' element = {<Home />}/>
        <Route path ='/about-canvas' element = {<AboutCanvas />}/>
        <Route path ='/upload' element = {<UploadPage />}/>
        <Route path ='/graphs' element = {<GraphsPage />}/>
        <Route path ='/sign-up' element = {<SignUp />}/>
     </Routes>
    </Router>
    </>
  );
}

export default App;
