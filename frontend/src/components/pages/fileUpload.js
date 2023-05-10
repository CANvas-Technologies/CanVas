import React from "react";
import { useNavigate } from "react-router-dom";
import { ChangeEvent, useState } from "react";
import axios from "axios";
import cors from "cors";

import Button from "@mui/material/Button";
import Divider from "@mui/material/Divider";

import TextField from "@mui/material/TextField";
import Input from "@mui/material/Input";
import { FormControl, FormLabel, CircularProgress } from "@mui/material";

import { API_URL } from "../../env";
import useUser from "../hooks/useUser";
import { authorizedPost } from "../util/auth";

function FileUploadSingle() {
  const [file, setFile] = useState();
  const [name, setName] = useState("");
  const [downloadUri, setDownloadUri] = React.useState();

  const [uploading, setUploading] = React.useState(false);

  const navigate = useNavigate();

  const { user, isLoading } = useUser();
  if (!user && !isLoading) navigate("/login");

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
      const response = await authorizedPost(
        user,
        API_URL + "upload/" + name,
        formData
      );
      setDownloadUri(response.data.fileDownloadUri);
      setUploading(false);
      navigate("/graphs");
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
