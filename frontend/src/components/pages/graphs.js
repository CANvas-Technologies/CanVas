import "../../App.css";
import React, { useRef } from "react";
import { useState, useEffect } from "react";
import Dropdown from "react-dropdown";
import "react-dropdown/style.css";
import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  Legend,
  Label,
} from "recharts";
import { render } from "react";
import {
  Box,
  Slider,
  Button,
  Card,
  Divider,
  LinearProgress,
} from "@mui/material";
import useUser from "../hooks/useUser";
import { API_URL } from "../../env";
import { authorizedGet } from "../util/auth";

export default function Graph() {
  const [name, setName] = useState("");
  const [traceNames, setTraceNames] = useState("");
  const [email, setEmail] = useState("");
  const [signal, setSignal] = useState("");
  const [wasClicked, setClicked] = useState(null);
  const [newData, setData] = useState(null);
  const [bucketVal, setBuckets] = useState([0, 0]);
  const [signalNames, setSignalNames] = useState(null);
  const [upperBucket, setUpper] = useState(100);
  const defaultOption = "Select";

  const { user, isLoading } = useUser();

  // increment loading by 1 when loading, decrement when done.
  const [loading, setLoading] = useState(0);

  function startLoading() {
    setLoading(loading + 1);
  }

  function endLoading() {
    setLoading(0);
  }

  async function getData() {
    startLoading();
    const response = await authorizedGet(
      user,
      API_URL +
        "graphing/getSignalData/" +
        name.value +
        "/" +
        signal.value +
        "/" +
        bucketVal[0] +
        "/" +
        bucketVal[1]
    );
    setData(response.data);
    endLoading();
  }

  async function getBucket() {
    startLoading();
    const response = await authorizedGet(
      user,
      API_URL + "graphing/getTrace/" + name.value + "/" + signal.value
    );
    setUpper(response.data);
    setClicked(1);
    endLoading();
  }

  async function getSignalNames() {
    startLoading();
    const response = await authorizedGet(
      user,
      API_URL + "graphing/getSignalNames/" + name.value
    );
    setSignalNames(response.data);
    endLoading();
  }

  function valuetext(bucketVal) {
    return `${bucketVal}°C`;
  }

  function displayGraph(newData) {
    render(
      <LineChart
        width={1500}
        height={500}
        data={newData}
        margin={{
          top: 5,
          right: 30,
          left: 20,
          bottom: 5,
        }}
      >
        <CartesianGrid strokeDasharray="3 3" />
        <XAxis dataKey="timestamp">
          <Label
            value="timestamp (seconds)"
            offset={0}
            position="insideBottom"
          />
        </XAxis>
        <YAxis type="number" domain={["dataMin", "dataMax"]} />
        <Tooltip />
        <Legend />
        <Line
          type="monotone"
          dataKey="signal value:"
          stroke="#8884d8"
          activeDot={{ r: 8 }}
        />
      </LineChart>
    );
  }

  const retrieveBucket = async (e) => {
    e.preventDefault();
    await getBucket(name, signal.value);

    //displayGraph(newData);
  };

  const handleSlider = (event, newValue) => {
    setBuckets(newValue);
  };

  const handleSignalNames = async (e) => {
    e.preventDefault();
    await getSignalNames(name);
  };

  async function handleGetTraces() {
    startLoading();
    const response = await authorizedGet(
      user,
      API_URL + "graphing/getTraceNames"
    );
    setTraceNames(response.data);
    endLoading();
  }

  const getGraph = async (e) => {
    e.preventDefault();
    await getData(name, signal.value);
    //displayGraph(newData);
  };

  console.log(traceNames);
  console.log(name);

  return (
    <>
      {loading != 0 ? <LinearProgress /> : null}
      <br />
      <Divider>
        {/* <center> */}
        <h1 className="upload"> Graphing </h1>
        {/* </center> */}
        <br />
        <Divider>
          <Card>
            <Divider>
              <br />
              <Button variant="outlined" onClick={handleGetTraces}>
                Get Traces
              </Button>
              <br />
              <br />
              {traceNames ? (
                <Dropdown
                  options={traceNames}
                  onChange={setName}
                  value={defaultOption}
                  placeholder="Select a trace"
                />
              ) : (
                <p></p>
              )}
              <br />
              {name ? (
                <Button variant="outlined" onClick={handleSignalNames}>
                  Retrieve Signal Names
                </Button>
              ) : (
                <p></p>
              )}
              <br />
              <br />
              {signalNames ? (
                <Dropdown
                  options={signalNames}
                  onChange={setSignal}
                  value={defaultOption}
                  placeholder="Select a signal"
                />
              ) : (
                <p></p>
              )}
              <br />
              {signalNames ? (
                <>
                  <Button variant="outlined" onClick={retrieveBucket}>
                    Retrieve Signal Data
                  </Button>
                  <br />
                  <br />
                </>
              ) : (
                <p></p>
              )}

              {signal && wasClicked ? (
                <Box sx={{ width: 300 }}>
                  <Slider
                    getAriaLabel={() => "Bucket bounds"}
                    value={bucketVal}
                    onChange={handleSlider}
                    valueLabelDisplay="auto"
                    getAriaValueText={valuetext}
                    min={0}
                    max={upperBucket}
                  />
                  <p>Select seconds of data to display</p>
                </Box>
              ) : (
                <p></p>
              )}
              {wasClicked ? (
                <Button variant="outlined" onClick={getGraph}>
                  Display Graph
                </Button>
              ) : (
                <p></p>
              )}
              <br />
              <br />
            </Divider>
          </Card>
        </Divider>
        <center>
          <br />
          <br />
          <br />
          {newData ? (
            <LineChart
              width={1200}
              height={500}
              data={newData}
              margin={{
                top: 20,
                right: 30,
                left: 5,
                bottom: 5,
              }}
            >
              <CartesianGrid strokeDasharray="3 3" />
              <XAxis dataKey="timestamp">
                <Label
                  value="timestamp (seconds)"
                  offset={50}
                  position="insideBottom"
                />
              </XAxis>
              <YAxis type="number" domain={[0, 100]} />
              <Tooltip />
              <Legend />
              <Line
                type="monotone"
                dataKey="data point"
                stroke="#8884d8"
                activeDot={{ r: 8 }}
              />
            </LineChart>
          ) : (
            <p> Select a signal to see the graph</p>
          )}
        </center>
      </Divider>
    </>
  );
}
