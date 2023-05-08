import { Change, useState } from "react";
import "../../App.css";
import Footer from "../footer";
import React from "react";
import FileUploadSingle from "./fileUpload";
import axios from "axios";

export default function UploadPage() {
  return (
    <center>
      <br />
      <h1 className="upload"> Upload Files </h1>
      <br />
      {/* <p className="upload-heading">
            Input trace name here:
        </p> */}
      <FileUploadSingle />
      <Footer />
    </center>
  );
}
