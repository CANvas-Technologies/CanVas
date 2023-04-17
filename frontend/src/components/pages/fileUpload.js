import React from 'react';
import { ChangeEvent, useState} from 'react';
import axios from 'axios';
import cors from 'cors'

import Button from '@mui/material/Button';
import Divider from '@mui/material/Divider';

import TextField from '@mui/material/TextField';
import Input from '@mui/material/Input';
import { FormControl, FormLabel } from '@mui/material';

function FileUploadSingle() {

  const [file, setFile] = useState();
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [downloadUri, setDownloadUri] = React.useState();

  const handleFileChange = (e: ChangeEvent<HTMLInputElement>) => {
    if (e.target.files) {
      setFile(e.target.files[0]);
    }
  };

  const uploadFile = async () => {
    try {
      const formData = new FormData();
      formData.append("file", file);
      const API_URL = "http://localhost:8080/upload/" + name + "/" + email;
      const response = await axios.post(API_URL, formData);
      setDownloadUri(response.data.fileDownloadUri);
    } catch (err) {
      alert(err.message);
    }
  };


  return (

      <Divider classname='input-areas'>
          <FormControl>
              <FormLabel>Enter Name:</FormLabel>
              <TextField
                  value = {name}
                  onChange = {(event) => setName(event.target.value)}
                  label = 'Trace name'
              />
              <TextField
                  value = {email}
                  onChange = {(event) => setEmail(event.target.value)}
                  label = 'User email'
              />

              <Input type="file" onChange={handleFileChange}/>
              <Button onClick={uploadFile}>Upload</Button>
          </FormControl>
      </Divider>
  );
}

export default FileUploadSingle;
