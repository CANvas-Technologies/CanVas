import React from "react";
import "../App.css";
import { Button } from "./button";
import "./herosection.css";

function HeroSection() {
  return (
    <div className="hero-container">
      <h1>VISUALIZE CANBUS TRACES</h1>
      <div className="hero-btns">
        <Button
          className="btns"
          buttonStyle="btn--primary"
          buttonSize="btn--large"
        >
          GET STARTED
        </Button>
      </div>
    </div>
  );
}

export default HeroSection;
