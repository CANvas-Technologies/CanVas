import React from "react";
import { ChangeEvent, useState } from "react";
import axios from "axios";
import cors from "cors";

import Button from "@mui/material/Button";
import Divider from "@mui/material/Divider";

import TextField from "@mui/material/TextField";
import Input from "@mui/material/Input";
import { FormControl, FormLabel, CircularProgress } from "@mui/material";

function FileUploadSingle() {
  const [file, setFile] = useState();
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [downloadUri, setDownloadUri] = React.useState();

  const [uploading, setUploading] = React.useState(false);

  const handleFileChange = (e) => {
    if (e.target.files) {
      setFile(e.target.files[0]);
    }
  };

  const uploadFile = async () => {
    try {
      setUploading(true);
      const formData = new FormData();
      formData.append("file", file);
      const API_URL = API_URL + "upload/" + name + "/" + email;
      const response = await axios.post(API_URL, formData);
      setDownloadUri(response.data.fileDownloadUri);
      setUploading(false);
    } catch (err) {
      alert(err.message);
    }
  };

  return (
    // if uploading, show progress bar
    // else show upload button
    <>
      {uploading ? (
        <>
          <h2>Uploading...</h2>
          <center>
            <CircularProgress />
          </center>
        </>
      ) : (
        <>
          <FormControl>
            <FormLabel>Enter Name:</FormLabel>
            <TextField
              value={name}
              onChange={(event) => setName(event.target.value)}
              label="Trace name"
            />
            <br />
            <TextField
              value={email}
              onChange={(event) => setEmail(event.target.value)}
              label="User email"
            />
            <br />
            <Input type="file" onChange={handleFileChange} />
            <br />
            <Button onClick={uploadFile} size="large" variant="contained">
              Upload
            </Button>
            <br />
          </FormControl>
        </>
      )}
    </>
  );
}

export default FileUploadSingle;
