import { Weather } from "../interfaces/Weather";

export const GetCurrentWeather = async (city: string): Promise<Weather> => {
  let weather: Weather;

  return await fetch(`api/currentWeather/${city}`)
    .then((response) => response.json())
    .then((data: Weather) => {
      weather = data;
      console.log(weather);
      weather.city = city;
      return weather;
    });
};
