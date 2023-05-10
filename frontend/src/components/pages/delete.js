import React from "react";
import { ChangeEvent, useState } from "react";
import axios from "axios";
import cors from "cors";

import Button from "@mui/material/Button";
import Divider from "@mui/material/Divider";

import TextField from "@mui/material/TextField";
import Input from "@mui/material/Input";
import { FormControl, FormLabel } from "@mui/material";

function DeletePage() {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [downloadUri, setDownloadUri] = React.useState();

  async function doDelete() {
    const uuid = await axios.get(API_URL + "api/get_trace_uuid/" + name.value);
    const response = await axios.get(
      API_URL + "api/delete_trace/" + uuid.value
    );
  }

  return (
    <center>
      <br />
      <h1 className="upload"> Trace Deletion </h1>
      <br />
      <FormControl>
        {/* <FormLabel>Trace Deletion</FormLabel> */}
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
        <Button onClick={doDelete} variant="contained" color="error">
          Delete
        </Button>
      </FormControl>
    </center>
  );
}

export default DeletePage;
