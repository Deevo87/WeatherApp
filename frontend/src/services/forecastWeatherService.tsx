import {Trip} from "../interfaces/Trip";
import {TripSaved} from "../interfaces/TripSaved";

export const GetForecastWeather = async (
    locations: string[],
    days: number
): Promise<Array<Array<any>>> => {
    let forecastWeather: any;

    return await fetch(`forecastWeather/${locations}/${days}`)
        .then((response) => response.json())
        .then((data: any) => {
            forecastWeather = data;
            console.log(forecastWeather)
            return forecastWeather;
        });
};

export const GetSavedTrips = async (): Promise<Array<any>> => {

    return await fetch(`/api/trips`)
        .then((response) => response.json())
        .then((data: any) => {
            return data
        })
}

export const SaveTrip = async (trip: Trip) => {
    const response = await fetch(`/api/trips`, {
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

export const DeleteTrip = async (trip: TripSaved) => {
   const response = await fetch(`/api/trips`, {
       method: 'DELETE',
       headers: {
           'Content-Type': 'application/json',
       },
       body: JSON.stringify(trip)
   })

    console.log(response)
    if (!response.ok) {
        throw new Error(`HTTP POST error while deleting trip! Status: ${response.status}`)
    }

}