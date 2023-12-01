import React, { useEffect, useState } from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import "./App.css";
import HomePage from "./model/HomePage/HomePage";
import CurrentWeatherPage from "./model/CurrentWeatherPage/CurrentWeatherPage";

function App() {
  const [message, setMessage] = useState("");

  useEffect(() => {
    fetch("/api")
      .then((response) => response.text())
      .then((mess) => {
        setMessage(mess);
      });
  }, []);

  return (
    <div>
      <h1>{message}</h1>
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
