import React from 'react';
import { ChangeEvent, useState} from 'react';
import axios from 'axios';
import cors from 'cors'

function FileUploadSingle() {

  const [file, setFile] = useState();
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
      const API_URL = "http://localhost:8080/upload";
      const response = await axios.post(API_URL, formData);
      setDownloadUri(response.data.fileDownloadUri);
    } catch (err) {
      alert(err.message);
    }
  };


  return (
    <div>

      <input type="file" onChange={handleFileChange} />

      <div>{file && `${file.name} - ${file.type}`}</div>

      <button onClick={uploadFile}>Upload</button>

    </div>
  );
}

export default FileUploadSingle;
