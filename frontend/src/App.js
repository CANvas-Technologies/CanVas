import React, {Component} from 'react'
import Navbar from './components/navbar';
import {BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import './App.css';
import Home from './components/pages/home';
import AboutCanvas from './components/pages/aboutcanvas';
import StartGraphing from './components/pages/startgraphing';
import LinkSharing from './components/pages/linksharing';
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
        <Route path ='/start-graphing' element = {<StartGraphing />}/>
        <Route path ='/link-sharing' element = {<LinkSharing />}/>
        <Route path ='/sign-up' element = {<SignUp />}/>

     </Routes>
    </Router>
    </>
  );
}

export default App;
