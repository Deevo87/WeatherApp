import { ForecastWeather } from "../interfaces/ForecastWeather";

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
      console.log(forecastWeather);
      return forecastWeather;
    });
};
