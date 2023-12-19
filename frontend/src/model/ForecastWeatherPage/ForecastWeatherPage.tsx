import React, { useState } from "react";
import TextField from "@mui/material/TextField";
import "./ForecastWeatherPage.css";
import Button from "@mui/material/Button";
import Alert from "@mui/material/Alert";
import AlertTitle from "@mui/material/AlertTitle";
import { GetForecastWeather } from "../../services/forecastWeatherService";
import Typography from "@mui/material/Typography";
import Box from "@mui/material/Box";
import Card from "@mui/material/Card";
import CardActions from "@mui/material/CardActions";
import CardContent from "@mui/material/CardContent";

interface TabPanelProps {
  children?: React.ReactNode;
  index: number;
  value: number;
}

const bull = (
  <Box
    component="span"
    sx={{ display: "inline-block", mx: "2px", transform: "scale(0.8)" }}
  >
    •
  </Box>
);

const card = (
  <React.Fragment>
    <CardContent>
      <Typography sx={{ fontSize: 14 }} color="text.secondary" gutterBottom>
        Word of the Day
      </Typography>
      <Typography variant="h5" component="div">
        be{bull}nev{bull}o{bull}lent
      </Typography>
      <Typography sx={{ mb: 1.5 }} color="text.secondary">
        adjective
      </Typography>
      <Typography variant="body2">
        well meaning and kindly.
        <br />
        {'"a benevolent smile"'}
      </Typography>
    </CardContent>
    <CardActions>
      <Button size="small">Learn More</Button>
    </CardActions>
  </React.Fragment>
);

function TabPanel(props: TabPanelProps) {
  const { children, value, index, ...other } = props;

  return (
    <div
      role="tabpanel"
      hidden={value !== index}
      id={`vertical-tabpanel-${index}`}
      aria-labelledby={`vertical-tab-${index}`}
      {...other}
    >
      {value === index && (
        <Box sx={{ p: 3 }}>
          <Typography>{children}</Typography>
        </Box>
      )}
    </div>
  );
}

function a11yProps(index: number) {
  return {
    id: `vertical-tab-${index}`,
    "aria-controls": `vertical-tabpanel-${index}`,
  };
}

export const ForecastWeatherPage = () => {
  const [startLoc, setStartLoc] = useState("");
  const [destLoc, setDestLoc] = useState("");
  const [days, setDays] = useState<number>(1);
  const [disabledBtn, setDisabledBtn] = useState(true);
  const [isError, setIsError] = useState(false);
  const [value, setValue] = useState(0);
  const [forecastData, setForecastData] = useState<any>([]);
  const [loadingData, setLoadingData] = useState(true);
  let forecast: any = [];
  // Regular expression for city or latitude and longitude validation
  const regex =
    /^[a-zA-Z\sżźćńółęąśŻŹĆĄŚĘŁÓŃ-]+$|^-?([0-9]|[1-8][0-9]|90)(\.\d+)?,-?((0|[1-9][0-9]?|1[0-7][0-9]|180)(\.\d+)?|180)$/;

  const handleStartLocChange = (event: any) => {
    const value = event.target.value;
    validate(value);
    setStartLoc(value);
  };

  const handleDestLocChange = (event: any) => {
    const value = event.target.value;
    validate(value);
    setDestLoc(value);
  };

  const handleDaysChange = (event: any) => {
    let tmp = event.target.value;
    if (parseInt(tmp) > 0) {
      setDays(tmp);
      setDisabledBtn(false);
    } else {
      setDisabledBtn(true);
    }
  };

  const validate = (city: string) => {
    if (regex.test(city)) {
      setIsError(false); // Hide error message if input is valid
      setDisabledBtn(false);
      return false;
    }
    setIsError(true); // Display error message for invalid input
    setDisabledBtn(true);
    return true;
  };

  const handleBtnChange = async (event: any) => {
    await GetForecastWeather(startLoc, destLoc, days)
      .then((data: any) => {
        forecast = data;
        setForecastData(data);
      })
      .catch((err) => {
        console.log("Error while setting weather in currentWeatherPage " + err);
      });
    setLoadingData(false);
    console.log(loadingData);
  };

  let essa = [];
  return (
    <div className="main">
      <div className="inputForm">
        <p>Choose starting location</p>
        <TextField
          id="outlined-basic"
          label="Start"
          variant="outlined"
          onChange={handleStartLocChange}
        />
        <p>Choose destination location</p>
        <TextField
          id="outlined-basic"
          label="Destination"
          variant="outlined"
          onChange={handleDestLocChange}
        />
        <p>Trip duration</p>
        <TextField
          id="standard-number"
          label="Days"
          variant="outlined"
          type="number"
          onChange={handleDaysChange}
        ></TextField>
      </div>
      <div className={`${isError ? "displayedAlert" : "disabledAlert"}`}>
        <Alert severity="error">
          <AlertTitle>Wrong input</AlertTitle>
          Please enter a valid city name or valid latitude and longitude (from
          -90 to 90 for latitude and -180 to 180 for longitude), separated by a
          comma (e.g., 48.8567,2.3508)
        </Alert>
      </div>
      <div className="searchBtn">
        <Button
          disabled={disabledBtn}
          variant="contained"
          onClick={handleBtnChange}
        >
          Search
        </Button>
      </div>
      <div className={`${loadingData ? "disableData" : "displayData"}`}>
        {forecastData.map((array: any, arrayIndex: any) => (
          <div key={arrayIndex} className="day">
            <h1 className="dayNr">Day {arrayIndex + 1}</h1>
            <div className="cards">
              {array.map((data: any, dataIndex: any) => (
                <Card variant="outlined" sx={{ width: 200 }}>
                  <Box component="div">
                    <CardContent>
                      <Typography variant="h5">Date: {data.date}</Typography>
                      <Typography variant="body2">
                        Preceived Temperature: {data.feelsLikeTemperature}
                      </Typography>
                      <Typography variant="body2">
                        Temeperature: {data.temperature}
                      </Typography>
                      <Typography variant="body2">
                        Wind: {data.windStrength}
                      </Typography>
                      <Typography variant="body2">
                        Snow: {data.snowStrength}
                      </Typography>
                      <Typography variant="body2">
                        Rain: {data.rainStrength}
                      </Typography>
                    </CardContent>
                  </Box>
                </Card>
              ))}
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default ForecastWeatherPage;
