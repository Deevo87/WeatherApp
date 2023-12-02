import { Weather } from "../interfaces/Weather";

export const GetCurrentWeather = async (city: string): Promise<Weather> => {
  let loading = false;
  let weather: Weather;

  console.log(city + " " + "passing to backend");
  loading = true;
  return await fetch(`api/currentWeather/${city}`)
    .then((response) => response.json())
    .then((data: Weather) => {
      weather = data;
      weather.city = city;
      loading = false;
      return weather;
    });
};
