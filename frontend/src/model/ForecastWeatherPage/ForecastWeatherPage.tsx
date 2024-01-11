import React, {useEffect, useState} from "react";
import TextField from "@mui/material/TextField";
import "./ForecastWeatherPage.css";
import Button from "@mui/material/Button";
import Alert from "@mui/material/Alert";
import AlertTitle from "@mui/material/AlertTitle";
import {GetForecastWeather, GetSavedTrips, SaveTrip, DeleteTrip} from "../../services/forecastWeatherService";
import Typography from "@mui/material/Typography";
import Box from "@mui/material/Box";
import Card from "@mui/material/Card";
import CardActions from "@mui/material/CardActions";
import CardContent from "@mui/material/CardContent";
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import {TabPanelProps} from "../../interfaces/TablePanelProps";
import {Trip} from "../../interfaces/Trip";
import DeleteIcon from '@mui/icons-material/Delete';
import {IconButton} from "@mui/material";
import SearchIcon from '@mui/icons-material/Search';

function TabPanel(props: TabPanelProps) {
    const {children, value, index, ...other} = props;

    return (
        <div
            role="tabpanel"
            hidden={value !== index}
            id={`vertical-tabpanel-${index}`}
            aria-labelledby={`vertical-tab-${index}`}
            {...other}
        >
            {value === index && (
                <Box sx={{p: 3}}>
                    <Typography>{children}</Typography>
                </Box>
            )}
        </div>
    );
}

function a11yProps(index: number) {
    return {
        id: `vertical-tab-${index}`,
        'aria-controls': `vertical-tabpanel-${index}`,
    };
}

const bull = (
    <Box
        component="span"
        sx={{display: "inline-block", mx: "2px", transform: "scale(0.8)"}}
    >
        •
    </Box>
);

const card = (
    <React.Fragment>
        <CardContent>
            <Typography sx={{fontSize: 14}} color="text.secondary" gutterBottom>
                Word of the Day
            </Typography>
            <Typography variant="h5" component="div">
                be{bull}nev{bull}o{bull}lent
            </Typography>
            <Typography sx={{mb: 1.5}} color="text.secondary">
                adjective
            </Typography>
            <Typography variant="body2">
                well meaning and kindly.
                <br/>
                {'"a benevolent smile"'}
            </Typography>
        </CardContent>
        <CardActions>
            <Button size="small">Learn More</Button>
        </CardActions>
    </React.Fragment>
);

export const ForecastWeatherPage = () => {
    const [startLoc, setStartLoc] = useState("");
    const [destLoc, setDestLoc] = useState<string[]>([]);
    const [days, setDays] = useState<number>(0);
    const [disabledBtn, setDisabledBtn] = useState(true);
    const [isError, setIsError] = useState(false);
    const [forecastData, setForecastData] = useState<any>([]);
    const [loadingData, setLoadingData] = useState(true);
    const [status, setStatus] = useState(undefined)
    const [savedTrips, setSavedTrips] = useState<any>([])
    const [tripsStatus, setTripsStatus] = useState(undefined)
    const [loadingTrips, setLoadingTrips] = useState(true)
    const [value, setValue] = React.useState(0);
    const [savingTrip, setSavingTrip] = useState(false)
    const [deletingTrip, setDeletingTrip] = useState(false)
    const [inputList, setInputList] = useState([{id: 0, value: ''}])
    const [extraDestinations, setExtraDestinations] = useState<[]>([])
    const [disabledAddBtn, setDisabledAddBtn] = useState(false)
    const handleChange = (event: React.SyntheticEvent, newValue: number) => {
        setValue(newValue);
    };

    // Regular expression for city or latitude and longitude validation
    const regex =
        /^[a-zA-Z\sżźćńółęąśŻŹĆĄŚĘŁÓŃ-]+$|^-?([0-9]|[1-8][0-9]|90)(\.\d+)?,-?((0|[1-9][0-9]?|1[0-7][0-9]|180)(\.\d+)?|180)$/;

    useEffect(() => {
        GetSavedTrips()
            .then((data: any) => {
                setSavedTrips(data)
                setTripsStatus(data.status)
            })
            .catch((err) => {
                console.log("Error while getting saved trips " + err);
            })
        setLoadingTrips(false)
    }, [savingTrip, deletingTrip])

    useEffect(() => {
        areBtnsDisabled();
    }, [days, destLoc, startLoc]);

    useEffect(() => {
        if (inputList.length >= 2) {
            setDisabledAddBtn(true)
        } else {
            setDisabledAddBtn(false)
        }
    }, [inputList])

    const handleStartLocChange = (event: any) => {
        const value = event.target.value;
        validate(value);
        setStartLoc(value);
    };

    const handleDaysChange = (event: any) => {
        let tmp = event.target.value;
        setDays(tmp)
    };

    const areBtnsDisabled = () => {
        if (startLoc !== "" && destLoc.length > 0 && days > 0) {
            setDisabledBtn(false)
        } else {
            setDisabledBtn(true)
            return
        }
    }

    const validate = (city: string) => {
        if (regex.test(city)) {
            setIsError(false); // Hide error message if input is valid
            return false;
        }
        setIsError(true); // Display error message for invalid input
        return true;
    };

    const handleSearchBtn = async (event: any) => {
        updateDestLoc()
        console.log(destLoc)
        await GetForecastWeather(startLoc, destLoc, days)
            .then((data: any) => {
                if (data.status === 500) {
                    setStatus(data.status)
                } else {
                    setStatus(undefined)
                }
                setForecastData(data);
            })
            .catch((err) => {
                console.log("Error while setting weather in currentWeatherPage " + err);
            });
        setLoadingData(false);
    };

    const updateDestLoc = () => {
        const updatedLocs = inputList.map((field) => field.value)
        setDestLoc(updatedLocs)
    }

    const handleSaveBtnChange = async () => {
        const trip: Trip = {
            startLoc: startLoc,
            destLocs: destLoc,
            days: days
        }
        await SaveTrip(trip)
        setSavingTrip(!savingTrip)
    }

    const handleDeleteBtn = async (trip: Trip) => {
        console.log(trip)
        await DeleteTrip(trip)
        setDeletingTrip(!deletingTrip)
    }

    const handleSearchSavedBtn = (trip: any) => {
        setStartLoc(trip.startLoc)
        setDestLoc(trip.destLoc)
        const updatedFields = [{id: 0, value: trip.destLoc}] //TODO jak gosia poprawi backend
        setInputList(updatedFields);
        setDays(trip.days)
    }

    const renderWeather = (forecastData: any, status: any) => {
        if (status === 500) {
            return <div className="status500">Brak wyników dla podanych miast!</div>
        }
        return (
            <div>
                {forecastData.map((array: any, arrayIndex: any) => (
                    <div key={arrayIndex} className="day">
                        <h1 className="dayNr">Day {arrayIndex + 1}</h1>
                        <div className="cards">
                            {array.map((data: any, dataIndex: any) => (
                                <Card variant="outlined" sx={{width: 200}}>
                                    <Box component="div">
                                        <CardContent>
                                            <Typography variant="h5">Date: {data.date}</Typography>
                                            <Typography variant="body2">
                                                Perceived Temperature: {data.feelsLikeTemperature}
                                            </Typography>
                                            <Typography variant="body2">
                                                Temperature: {data.temperature}
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
                                            <Typography variant="body2">
                                                Mud: {data.mud}
                                            </Typography>
                                        </CardContent>
                                    </Box>
                                </Card>
                            ))}
                        </div>
                    </div>
                ))}
            </div>)
    }

    const renderTrips = () => {

        return (
            <div className="savedTrips">
                <h1>Saved Trips</h1>
                <Box
                    sx={{flexGrow: 1, bgcolor: 'background.paper', display: 'flex', height: 224}}
                >
                    <Tabs
                        orientation="vertical"
                        variant="scrollable"
                        value={value}
                        onChange={handleChange}
                        aria-label="Vertical tabs example"
                        sx={{borderRight: 1, borderColor: 'divider'}}
                    >
                        {savedTrips.map((trip: any, tripIndex: any) => (
                            <Tab label={`Trip ${tripIndex + 1}`} {...a11yProps(tripIndex)} />
                        ))}
                    </Tabs>
                    {savedTrips.map((trip: any, tripIndex: number) => (
                        <TabPanel index={tripIndex} value={value}>
                            <p>
                                Starting location: {trip.startLoc}
                            </p>
                            <p>
                                Destination location: {trip.destLoc}
                                {/*TODO jak zostanie poprawiony backend */}
                                {/*Destination location: {trip.destLoc.join(" -> ")}*/}
                            </p>
                            <p>
                                Days: {trip.days}
                            </p>

                            <div className="savedTripsBtns">
                                <IconButton
                                    onClick={() => handleSearchSavedBtn(trip)}
                                >
                                    <SearchIcon/>
                                </IconButton>
                                <IconButton
                                    onClick={() => handleDeleteBtn(trip)}
                                >
                                    <DeleteIcon/>
                                </IconButton>
                            </div>
                        </TabPanel>
                    ))}
                </Box>
            </div>
        );
    }

    const handleAddAnotherDestBtn = () => {
        setInputList([...inputList, {id: inputList.length, value: ''}])
    }

    const handleDeleteInputBtn = (id: number) => {
        if (inputList.length > 1) {
            const updatedFields = inputList.filter((field) => field.id !== id);
            setInputList(updatedFields);
        }
    }

    const renderInputFields = () => {
        return (
            inputList.map((field) => (
                <div className="destField">
                    <TextField
                        key={field.id}
                        label="Destination"
                        variant="outlined"
                        onChange={(event) => handleFieldChange(field.id, event.target.value)}
                        value={field.value}
                    />
                    {field.id !== 0 && (
                        <IconButton onClick={() => handleDeleteInputBtn(field.id)}>
                            <DeleteIcon/>
                        </IconButton>
                    )}
                </div>
            ))
        )
    }

    const handleFieldChange = (id: number, value: string) => {
        validate(value);
        const updatedFields = inputList.map((field) => (
            field.id === id ? {...field, value} : field
        ))
        setInputList(updatedFields);
    }

    return (
        <div className="main">
            <div className="searchingForm">
                <div className="tripContainer">
                    {!loadingTrips && renderTrips()}
                </div>
                <div className="inputForm">
                    <p>Choose starting location</p>
                    <TextField
                        label="Start"
                        variant="outlined"
                        onChange={handleStartLocChange}
                        value={startLoc}
                    />
                    <p>Choose destination location</p>
                    <div className="addInputBtn">
                        <Button
                            variant="contained"
                            disabled={disabledAddBtn}
                            onClick={handleAddAnotherDestBtn}
                        >
                            Add another destination
                        </Button>
                    </div>
                    {renderInputFields()}

                    <p>Trip duration</p>
                    <TextField
                        id="standard-number"
                        className="essa"
                        label="Days"
                        variant="outlined"
                        type="number"
                        onChange={handleDaysChange}
                        value={days}
                    ></TextField>

                    <div className={`${isError ? "displayedAlert" : "disabledAlert"}`}>
                        <Alert severity="error">
                            <AlertTitle>Wrong input</AlertTitle>
                            Please enter a valid city name or valid latitude and longitude (from
                            -90 to 90 for latitude and -180 to 180 for longitude), separated by a
                            comma (e.g., 48.8567,2.3508)
                        </Alert>
                    </div>

                    <div className="buttons">
                        <div className="btn">
                            <Button
                                disabled={disabledBtn}
                                variant="contained"
                                onClick={handleSearchBtn}
                            >
                                Search
                            </Button>
                        </div>
                        <div className="btn">
                            <Button
                                disabled={disabledBtn}
                                variant="contained"
                                onClick={handleSaveBtnChange}
                            >
                                Save trip
                            </Button>
                        </div>
                    </div>
                </div>
            </div>

            {!loadingData && (renderWeather(forecastData, status))}
        </div>
    );
};

export default ForecastWeatherPage;