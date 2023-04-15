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
    const [wasClicked, setClicked ] = useState(null);
    const [newData, setData] = useState(null);
    async function getData(traceName){
        fetch(
            "http://localhost:8080/graphing/getSignalData/" + traceName + "/CAN1.OBD2.ParameterID_Service01"
        ).then((response) => response.json())
            .then(setData);

    }
    function displayGraph(newData){
        return(<LineChart
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
    const submit = (e) => {
        e.preventDefault();

        alert(`${name},${wasClicked}`)
        setName("")

        setClicked(0)
    };



    useEffect(() => {
        if(name){
            getData("testing");
            displayGraph(newData)
        }
    }, [name, newData]);
        console.log(name)
        console.log(newData)
        return (
            <div>
            <form onSubmit={submit}>
                <input
                    defaultValue={name}
                    onClick={(event) =>
                        setName(event.target.value)}
                    type="text"
                    placeholder="trace name..."
                />
                <button>ADD</button>
            </form>
            </div>
        );
}
