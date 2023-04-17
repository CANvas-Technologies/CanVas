import { Change, useState  } from 'react'
import '../../App.css';
import Footer from '../footer';
import React from "react";
import FileUploadSingle from './fileUpload';
import axios from "axios";

export default function StartGraphing(){
    return (
        <>
        <h1 className='startgraphing'> Start Graphing </h1>
        <FileUploadSingle />
        <Footer />
        </>

        );

}




