import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import "../../App.css";
import Footer from "../footer";
import {
  List,
  ListItem,
  ListItemText,
  IconButton,
  Avatar,
} from "@mui/material";
import ListItemAvatar from "@mui/material/ListItemAvatar";
import DeleteIcon from "@mui/icons-material/Delete";
import SsidChartIcon from "@mui/icons-material/SsidChart";

import { API_URL } from "../../env";
import useUser from "../hooks/useUser";
import authorizedGet from "../util/auth";

export default function accountPage() {
  // get user state from useUser hook
  const { user, isLoading } = useUser();
  const navigate = useNavigate();

  const [traces, setTraces] = useState(null);

  useEffect(() => {
    if (!user) {
      if (!isLoading) navigate("/login");
      return;
    }

    const getTraces = async () => {
      const response = await authorizedGet(
        user,
        `${API_URL}graphing/getTraceNames/${user.email}}`
      );
      setTraces(response.data);
      console.log(response.data);
    };

    getTraces();
  }, [user]);

  const traceList = () => {
    if (traces) {
      return traces.map((trace) => {
        console.log("making trace item...");
        return (
          <ListItem
            secondaryAction={
              <IconButton edge="end" aria-label="delete">
                <DeleteIcon />
              </IconButton>
            }
          >
            <ListItemAvatar>
              <Avatar>
                <SsidChartIcon />
              </Avatar>
            </ListItemAvatar>
            <ListItemText primary={trace} />
          </ListItem>
        );
      });
    }
  };

  return (
    <>
      <h1 className="signup"> Hello Account </h1>
      <br />
      <p className="signup-heading">
        {isLoading
          ? "Loading..."
          : user
          ? `Hello ${user.email}`
          : "You are not logged in"}
      </p>

      <h1> Your Traces </h1>
      <List>{traceList()}</List>
      <Footer />
    </>
  );
}