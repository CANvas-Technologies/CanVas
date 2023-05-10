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
  Card,
  Divider,
  LinearProgress,
} from "@mui/material";
import ListItemAvatar from "@mui/material/ListItemAvatar";
import DeleteIcon from "@mui/icons-material/Delete";
import SsidChartIcon from "@mui/icons-material/SsidChart";
import LogoutIcon from "@mui/icons-material/Logout";

import { API_URL } from "../../env";
import useUser from "../hooks/useUser";
import { authorizedGet, authorizedPost } from "../util/auth";

import logOut from "../logout";

export default function accountPage() {
  // get user state from useUser hook
  const { user, isLoading } = useUser();
  const navigate = useNavigate();

  const [traces, setTraces] = useState(null);

  useEffect(() => {
    if (isLoading) return;

    if (!user) {
      navigate("/login");
      return;
    }

    const getTraces = async () => {
      const response = await authorizedGet(
        user,
        `${API_URL}graphing/getTraceNames`
      );
      setTraces(response.data);
      console.log(response.data);
    };

    getTraces();
  }, [user]);

  async function doDelete(trace) {
    const uuid = authorizedGet(
      user,
      API_URL + "api/get_trace_uuid/" + name.value
    );
    const response = authorizedGet(
      user,
      API_URL + "api/delete_trace/" + uuid.value
    );
  }

  const traceList = () => {
    if (traces) {
      return traces.map((trace) => {
        console.log("making trace item...");
        return (
          <ListItem
            secondaryAction={
              <IconButton
                edge="end"
                aria-label="delete"
                onClick={() => doDelete(trace.repeat(1))}
              >
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
      {isLoading ? (
        <LinearProgress />
      ) : (
        <center>
          <br />
          <h1 className="signup"> Your CANvas Account</h1>
          <br />
          <p className="signup-heading">
            {isLoading
              ? "Loading..."
              : user
              ? `Hello ${user.email}!`
              : "You are not logged in"}
            <IconButton
              edge="end"
              aria-label="delete"
              onClick={() => {
                logOut();
                navigate("/");
              }}
            >
              <LogoutIcon />
            </IconButton>
          </p>
          <br />
          <center>
            <Card sx={{ width: "80%" }}>
              <h1> Your Traces </h1>
              <List>{traceList()}</List>
            </Card>
          </center>
        </center>
      )}
      ;
      <Footer />;
    </>
  );
}
