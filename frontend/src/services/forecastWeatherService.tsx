import {Trip} from "../interfaces/Trip";

export const GetForecastWeather = async (
    startLoc: string,
    destLoc: string,
    days: number
): Promise<Array<Array<any>>> => {
    let forecastWeather: any;

    return await fetch(`forecastWeather/${startLoc}/${destLoc}/${days}`)
        .then((response) => response.json())
        .then((data: any) => {
            forecastWeather = data;
            console.log(forecastWeather)
            return forecastWeather;
        });
};

export const GetSavedTrips = async (): Promise<Array<any>> => {

    return await fetch(`forecastWeather/`)
        .then((response) => response.json())
        .then((data: any) => {
            return data
        })
}

export const SaveTrip = async (trip: Trip) => {
    const response = await fetch(`forecastWeather/saveTrip`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(trip)
    })

    if (!response.ok) {
        throw new Error(`HTTP POST error while saving trip! Status: ${response.status}`)
    }
}