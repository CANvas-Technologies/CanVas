import React, {useRef} from 'react';
import { useState, useEffect } from 'react';
import '../../App.css';
import Footer from '../footer';
import { Button } from '../button';
import {LineChart,
        Line,
        XAxis,
        YAxis,
        CartesianGrid,
        Tooltip,
        Legend
       } from 'recharts';
import { render } from "@testing-library/react";
import {Box, Slider} from "@mui/material";
import axios from "axios";
import './startgraphing.css';


const data = [
    { name: "Page A", uv: 4000, pv: 2400, amt: 2400 },
    { name: "Page B", uv: 3000, pv: 1398, amt: 2210 },
    { name: "Page C", uv: 2000, pv: 9800, amt: 2290 },
    { name: "Page D", uv: 2780, pv: 3908, amt: 2000 },
    { name: "Page E", uv: 1890, pv: 4800, amt: 2181 },
    { name: "Page F", uv: 2390, pv: 3800, amt: 2500 },
    { name: "Page G", uv: 3490, pv: 4300, amt: 2100 },
];

export default function StartGraphing(){

    const [name, setName] = useState("");
    const [signal, setSignal] = useState("");
    const [wasClicked, setClicked ] = useState(null);
    const [newData, setData] = useState(null);
    const [bucketVal, setBuckets] = useState([0,0]);
    const [upperBucket, setUpper] = useState(100);

    async function getData(traceName, signalName){
        fetch(
            "http://localhost:8080/graphing/getSignalData/" + traceName + "/" + signalName + "/" + bucketVal[0] + "/" + bucketVal[1]
        ).then((response) => response.json())
            .then(setData);
    }

    async function getBucket(traceName,signalName){
        const response = await axios.get('http://localhost:8080/graphing/getTrace/' + traceName + "/" + signalName);
        setUpper(response.data)
        setClicked(1)
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

    const retrieveBucket = async(e) => {
        e.preventDefault();
        await getBucket(name, signal);
        //displayGraph(newData);
    }

    const handleSlider = (event, newValue) => {
        setBuckets(newValue);
    };

    const getGraph = async(e) => {
        e.preventDefault();
        await getData(name,signal);
        //displayGraph(newData);
    }

    console.log(bucketVal[0])
    console.log(bucketVal[1])

    return (
        <div className='upload-container'>
        <h1 className='startgraphing'> START GRAPHING </h1>
        <br/>
            <section className="upload">
                <p className="upload-heading">
                    Input trace name and signal name here:
                </p>
                <br/>
                <div classname='input-areas'>
                    <form>
                        <input
                            value = {name}
                            onChange={(event) => setName(event.target.value)}
                            type='text'
                            placeholder='trace name...'
                        />
                        <br/>
                        <br/>
                        <input
                            value = {signal}
                            onChange={(event) => setSignal(event.target.value)}
                            type="text"
                            placeholder='signal name...'
                        />
                        <br/>
                        <br/>
                        <button onClick={retrieveBucket}>Retrieve Bucket Bounds </button>
                    </form>
                        <Box sx={{ width: 300 }}>
                        <Slider
                            getAriaLabel={() => 'Bucket bounds'}
                            value={bucketVal}
                            onChange={handleSlider}
                            valueLabelDisplay="auto"
                            getAriaValueText={valuetext}
                            min = {0}
                            max={upperBucket}
                        />
                        </Box>
                        {wasClicked ?
                        <button onClick={getGraph}>Display Graph

                        </button>
                        : <p>Please retrieve bucket bounds</p>
                        }
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
            </section>
            <Footer />
        </div>


        );



}