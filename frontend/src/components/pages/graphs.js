// import React from 'react';
// import { Change, useState } from 'react';
// import '../../App.css';
// import Footer from '../footer';

// export default function LinkSharing(){

//     return (
//         <>
//         <h1 className='graphs'> Graphs </h1>

//         <Footer />
//         </>

//         );

// }

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
import { Box, Slider, Button, Card } from "@mui/material";
import axios from "axios";

const data = [
  {
    name: "Page A",
    uv: 4000,
    pv: 2400,
    amt: 2400,
  },
  {
    name: "Page B",
    uv: 3000,
    pv: 1398,
    amt: 2210,
  },
  {
    name: "Page C",
    uv: 2000,
    pv: 9800,
    amt: 2290,
  },
  {
    name: "Page D",
    uv: 2780,
    pv: 3908,
    amt: 2000,
  },
  {
    name: "Page E",
    uv: 1890,
    pv: 4800,
    amt: 2181,
  },
  {
    name: "Page F",
    uv: 2390,
    pv: 3800,
    amt: 2500,
  },
  {
    name: "Page G",
    uv: 3490,
    pv: 4300,
    amt: 2100,
  },
];

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
  async function getData() {
    fetch(
      "http://localhost:8080/graphing/getSignalData/" +
        name.value +
        "/" +
        signal.value +
        "/" +
        bucketVal[0] +
        "/" +
        bucketVal[1]
    )
      .then((response) => response.json())
      .then(setData);
  }
  async function getBucket() {
    const response = await axios.get(
      "http://localhost:8080/graphing/getTrace/" +
        name.value +
        "/" +
        signal.value
    );
    setUpper(response.data);
    setClicked(1);
  }

  async function getSignalNames() {
    const response = await axios.get(
      "http://localhost:8080/graphing/getSignalNames/" + name.value
    );
    setSignalNames(response.data);
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
    const response = await axios.get(
      "http://localhost:8080/graphing/getTraceNames/" + email
    );
    setTraceNames(response.data);
  }

  const getGraph = async (e) => {
    e.preventDefault();
    await getData(name, signal.value);
    //displayGraph(newData);
  };

  console.log(traceNames);
  console.log(name);

  return (
    <div>
      <div style={{ marginLeft: 2 + "em", marginRight: 2 + "em" }}>
        <h1 className="upload"> Graphing </h1>
      </div>
      <br />
      <div style={{ marginLeft: 2 + "em", marginRight: 2 + "em" }}>
        <Card>
          <div style={{ marginLeft: 2 + "em", marginRight: 2 + "em" }}>
            <br />
            <form>
              <input
                value={email}
                onChange={(event) => setEmail(event.target.value)}
                type="text"
                placeholder="Email"
              />
              <br />
            </form>
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
          </div>
        </Card>
      </div>
      <center>
        <br />
        <br />
        <br />
        {newData ? (
          <LineChart
            width={2000}
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
    </div>
  );
}
