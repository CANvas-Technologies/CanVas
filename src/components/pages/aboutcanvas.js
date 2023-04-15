import React from 'react';
import '../../App.css';
import Footer from '../footer';

export default function AboutCanvas(){
    return (
        <div>
            <h1 className='aboutcanvas'> ABOUT CANVAS </h1>
            <br/>

            <h2> What is CAN bus? </h2>
            <br/>
            <p> The Controller Area Network (CAN bus) is a message-based protocol designed
                to allow the Electronic Control Units (ECUs) found in today’s automobiles,
                as well as other devices, to communicate with each other in a reliable,
                priority-driven fashion. Messages or “frames” are received by all devices
                in the network, which does not require a host computer. </p>
            <br/>

            <h2> Advantages of CAN bus </h2>
            <br/>
            <p> The CAN bus standard is widely accepted and is used in practically all
                vehicles and many machines. This is mainly due to below key benefits: </p>
            <br/>
            <p>&emsp;&#x2022;Simple and low cost: ECUs communicate via a single CAN system instead of via
                direct complex analog signal lines - reducing errors, weight, wiring, and
                costs. CAN chipsets are readily available and affordable. </p>
            <br/>
            <p>&emsp;&#x2022;Fully centralized: the CAN bus provides one point of entry to communicate with all network
                ECUs - enabling central diagnostics, data logging, and configuration.</p>
            <br/>
            <p>&emsp;&#x2022;Extremely robust: the system is robust towards electric disturbances and electromagnetic
                interference - ideal for safety-critical applications (e.g. vehicles)</p>
            <br/>
            <p>&emsp;&#x2022;Efficient: CAN frames are prioritized by ID numbers. The top priority data gets immediate
                bus access, without causing interruption of other frames.</p>
            <br/>
            <p>&emsp;&#x2022;Reduced vehicle weight: by the elimination of kilometers of heavily insulated electrical
                wires and their weight from the vehicle.</p>
            <br/>
            <p>&emsp;&#x2022;Easy deployment: a proven standard with a rich support ecosystem.</p>
            <br/>
            <p>&emsp;&#x2022;Resistant to EMI: this makes CAN ideal for critical applications in vehicles.</p>
            <br/>
            <p>CAN has excellent control and fault detection capabilities. Detecting an error is easily done, and thus
                transmitted data gets to where it needs to go. </p>
            <br/>
            <p>It is an ideal protocol when distributed control of a complex system is required. It reduces heavy wiring
                and thus costs and weight. The cost of the chips is low, and implementing CAN is relatively easy because
                of the clean design of the protocol.</p>
            <br/>
            <p>Another advantage to using CAN is that the first two layers: the physical layer and the data link layer,
                are implemented in inexpensive microchips, available in several configurations.</p>
            <br/>

            <h2> Popular CAN bus Applications </h2>
            <br/>
            <p>Today, applications for CAN are dominated by the automotive and motor vehicle world, but they are not
                limited to that. CAN is found across virtually every industry. You can find the CAN protocol being used
                in:</p>
            <br/>
            <p>&emsp;&#x2022;Every kind of vehicle: motorcycles, automobiles, trucks etc.</p>
            <br/>
            <p>&emsp;&#x2022;Heavy-duty fleet telematics</p>
            <br/>
            <p>&emsp;&#x2022;Airplanes</p>
            <br/>
            <p>&emsp;&#x2022;Elevators</p>
            <br/>
            <p>&emsp;&#x2022;Ships</p>
            <br/>
            <p>&emsp;&#x2022;Manufacturing plants</p>
            <br/>
            <p>&emsp;&#x2022;Medical Equipment</p>
            <br/>
            <p>&emsp;&#x2022;Predictive maintenance systems</p>
            <br/>
            <p>&emsp;&#x2022;Washing machines, dryers, and other househole appliances</p>
            <br/>

            <h2> A Brief History of CAN bus </h2>
            <br/>
            <p> When you flip a switch in your house to turn on the lights, electricity
                flows through the switch to the lights. As a result, the switches and wiring
                need to be heavy and insulated enough to handle the maximum expected current.
                The walls of your house are filled with this heavy, insulated wiring. </p>
            <br/>
            <p> Cars and trucks used to be wired exactly the same way: ever since Henry
                Ford got the idea to add lights and an electric horn to his cars in 1915,
                electricity flowed from the battery through switches to the lights and
                other devices. By the 1960s there were thousands of heavy wires running
                throughout every vehicle. Every bit of extra weight reduces a vehicle’s
                fuel efficiency. </p>
            <br/>
            <p> Following the oil embargoes of the 1970s, there was increasing pressure on
                automobile manufacturers to improve their fuel efficiency. So they
                started looking for ways to reduce the weight of the cars they were
                making. </p>
            <br/>
            <p> By the early 1980s, cars had more and more ECUs (electronic control units) inside
                them, and companies like Robert Bosch company of Germany were looking
                for a type of bus communication system that could be used as a
                communication system between multiple ECUs and vehicle systems.
                They searched the market but couldn’t find exactly what they needed,
                so they began developing the “Controller Area Network” in partnership
                with automobile manufacturer Mercedes-Benz and semiconductor maker
                Intel®, and several universities in Germany. </p>
            <br/>
            <p> In 1986, Bosch introduced the CAN standard at the SAE Congress in Detroit.
                One year later Intel Corporation began shipments of the first CAN
                controller chips, and the automotive world was changed forever.
                Looking back, the weight savings that resulted from the development
                of CAN were almost a lucky by-product, but very real nonetheless. </p>
            <br/>
            <Footer />
        </div>


    );

}