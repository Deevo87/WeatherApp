import React, { useState } from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import "./App.css";
import HomePage from "./model/HomePage/HomePage";
import CurrentWeatherPage from "./model/CurrentWeatherPage/CurrentWeatherPage";

function App() {
  const [message, setMessage] = useState("");


  return (
    <div>
      <h1>WeatherApp</h1>
      <Router>
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/currentWeather" element={<CurrentWeatherPage />} />
        </Routes>
      </Router>
    </div>
  );
}

export default App;
