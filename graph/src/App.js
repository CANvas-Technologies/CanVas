import './App.css';
import React, {useRef} from "react";
import { useState, useEffect } from "react";
import {
    LineChart,
    Line,
    XAxis,
    YAxis,
    CartesianGrid,
    Tooltip,
    Legend
} from "recharts";
import {render} from "@testing-library/react";
import {Box, Slider} from "@mui/material";
import axios from "axios";

const data = [
    {
        name: "Page A",
        uv: 4000,
        pv: 2400,
        amt: 2400
    },
    {
        name: "Page B",
        uv: 3000,
        pv: 1398,
        amt: 2210
    },
    {
        name: "Page C",
        uv: 2000,
        pv: 9800,
        amt: 2290
    },
    {
        name: "Page D",
        uv: 2780,
        pv: 3908,
        amt: 2000
    },
    {
        name: "Page E",
        uv: 1890,
        pv: 4800,
        amt: 2181
    },
    {
        name: "Page F",
        uv: 2390,
        pv: 3800,
        amt: 2500
    },
    {
        name: "Page G",
        uv: 3490,
        pv: 4300,
        amt: 2100
    }
];




export default function App() {
    const [name, setName] = useState("");
    const [signal, setSignal] = useState("");
    const [wasClicked, setClicked ] = useState(null);
    const [newData, setData] = useState(null);
    const [bucketVal, setBuckets] = useState([0,0]);

    const [upperBucket, setUpper] = useState(100);
    async function getData(traceName, signalName){
        fetch(
            "http://localhost:8080/graphing/getSignalData/" + traceName + "/" + signalName
        ).then((response) => response.json())
            .then(setData);

    }
    async function getTrace(traceName){
        const response = await axios.get('http://localhost:8080/graphing/getTrace' + traceName);
        setUpper(response.data)
    }
    function valuetext(bucketVal) {
        return `${bucketVal}°C`;
    }
    function displayGraph(newData){
       render
        (<LineChart
            width={2000}
            height={500}
            data={newData}
            margin={{
                top: 5,
                right: 30,
                left: 20,
                bottom: 5
            }}
        >
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="timestamp" />
            <YAxis type = "number" domain = {[0,100]} />
            <Tooltip />
            <Legend />
            <Line
                type="monotone"
                dataKey="data point"
                stroke="#8884d8"
                activeDot={{ r: 8 }}
            />

        </LineChart>);
    }

    const retrieveTrace = async() => {
        await getTrace(name);

        //displayGraph(newData);
    }

    const handleSlider = (event, newValue) => {
        setBuckets(newValue);
    };


    const submit = (e) => {
        e.preventDefault();

        alert(`${name},${wasClicked}`)
        setName("")

        setClicked(0)
    };
    const getGraph = async() => {
        await getData(name,signal);
        //displayGraph(newData);
    }



        console.log(upperBucket)

        return (
            <div>
            <form onSubmit={submit}>
                <input
                    value={name}
                    onChange={(event) =>
                        setName(event.target.value)

                    }
                    type="text"
                    placeholder="trace name..."
                />

                <button onClick={retrieveTrace}>Submit Trace Name

                </button>
            </form>
            <form>
                <input
                    value={signal}
                    onChange={(event) =>
                        setSignal(event.target.value)

                    }
                    type="text"
                    placeholder="signal name..."
                />

                <button onClick={getGraph}>Submit Signal Name

                </button>
            </form>
                <Box sx={{ width: 300 }}>
                    <Slider
                        getAriaLabel={() => 'Bucket bounds'}
                        value={bucketVal}
                        onChange={handleSlider}
                        valueLabelDisplay="auto"
                        getAriaValueText={valuetext}
                    />
                </Box>
                {newData ?
                <LineChart
                    width={2000}
                    height={500}
                    data={newData}
                    margin={{
                        top: 5,
                        right: 30,
                        left: 20,
                        bottom: 5
                    }}
                >
                    <CartesianGrid strokeDasharray="3 3" />
                    <XAxis dataKey="timestamp" />
                    <YAxis type = "number" domain = {[0,100]} />
                    <Tooltip />
                    <Legend />
                    <Line
                        type="monotone"
                        dataKey="data point"
                        stroke="#8884d8"
                        activeDot={{ r: 8 }}
                    />

                </LineChart>
                    : <p> Enter a trace name to see the graph</p>
                }
            </div>
        );
}
