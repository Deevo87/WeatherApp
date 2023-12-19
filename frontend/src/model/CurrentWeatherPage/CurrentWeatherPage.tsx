import React, { useState } from "react";
import TextField from "@mui/material/TextField";
import "./CurrentWeatherPage.css";
import Button from "@mui/material/Button";
import { styled } from "@mui/material/styles";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell, { tableCellClasses } from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import Alert from "@mui/material/Alert";
import AlertTitle from "@mui/material/AlertTitle";
import { GetCurrentWeather } from "../../services/currentWeatherService";
import { Weather } from "../../interfaces/Weather";

const StyledTableCell = styled(TableCell)(({ theme }) => ({
  [`&.${tableCellClasses.head}`]: {
    backgroundColor: theme.palette.common.black,
    color: theme.palette.common.white,
  },
  [`&.${tableCellClasses.body}`]: {
    fontSize: 14,
  },
}));

const StyledTableRow = styled(TableRow)(({ theme }) => ({
  "&:nth-of-type(odd)": {
    backgroundColor: theme.palette.action.hover,
  },
  "&:last-child td, &:last-child th": {
    border: 0,
  },
}));

const labels: [string, keyof Weather][] = [
  ["Weather for", "city"],
  ["Date", "date"],
  ["Perceived Temperature", "feelsLikeTemperature"],
  ["Temperature", "temperature"],
  ["Condition", "precipitation"],
  ["Wind", "windStrength"],
];

export const CurrentWeatherPage = () => {
  const [city, setCity] = useState("");
  const [disabledBtn, setDisabledBtn] = useState(true);
  const [weather, setWeather] = useState<any>([]);
  const [isLoaded, setIsLoaded] = useState(false);
  const [isError, setIsError] = useState(false);

  const handleCityChange = (event: any) => {
    const value = event.target.value;

    // Regular expression for city or latitude and longitude validation
    const regex =
      /^[a-zA-Z\sżźćńółęąśŻŹĆĄŚĘŁÓŃ-]+$|^-?([0-9]|[1-8][0-9]|90)(\.\d+)?,-?((0|[1-9][0-9]?|1[0-7][0-9]|180)(\.\d+)?|180)$/;

    if (regex.test(value)) {
      setCity(value);
      setIsError(false); // Hide error message if input is valid
      setDisabledBtn(false);
    } else {
      setIsError(true); // Display error message for invalid input
      setDisabledBtn(true);
    }
  };

  const convertObject = (
    inputObj: Weather,
    mappings: [string, keyof Weather][]
  ) => {
    const convertedArray = [];

    for (const [label, key] of mappings) {
      const value = inputObj[key];
      convertedArray.push([label, value]);
    }
    return convertedArray;
  };

  const handleBtnChange = async (event: any) => {
    await GetCurrentWeather(city)
      .then((data: Weather) => {
        let convertedData = convertObject(data, labels);
        setWeather(convertedData);
        setIsLoaded(true);
      })
      .catch((err) => {
        console.log("Error while setting weather in currentWeatherPage " + err);
      });
  };

  return (
    <div className="main">
      <div className="inputForm">
        <p>Choose city</p>
        <TextField
          id="outlined-basic"
          label="City"
          variant="outlined"
          onChange={handleCityChange}
        />
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
      <div className={`displayForm ${isLoaded ? "" : "disabledForm"}`}>
        <TableContainer className="tablecoint" component={Paper}>
          <Table aria-label="customized table">
            <TableBody>
              {weather.map((row: any) => (
                <StyledTableRow key={row[0]}>
                  <StyledTableCell component="th" scope="row">
                    {row[0]}
                  </StyledTableCell>
                  <StyledTableCell align="center">{row[1]}</StyledTableCell>
                </StyledTableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      </div>
    </div>
  );
};

export default CurrentWeatherPage;
