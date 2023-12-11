import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import "./App.css";
import HomePage from "./model/HomePage/HomePage";
import CurrentWeatherPage from "./model/CurrentWeatherPage/CurrentWeatherPage";
import ForecastWeatherPage from "./model/ForecastWeatherPage/ForecastWeatherPage";

function App() {
  return (
    <div>
      <h1>WeatherApp</h1>
      <Router>
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/currentWeather" element={<CurrentWeatherPage />} />
          <Route
            path="/forecastWeather"
            element={<ForecastWeatherPage />}
          ></Route>
        </Routes>
      </Router>
    </div>
  );
}

export default App;
