import React from 'react';
import CurrentWeatherPage, {ForecastWeatherPage} from './ForecastWeatherPage';
import {fireEvent, render} from "@testing-library/react";

describe(ForecastWeatherPage, () => {
    it('should render city input', () => {
        const container = render(<ForecastWeatherPage />);

        const inputStart = container.getByLabelText("Start") as HTMLInputElement;
        fireEvent.change(inputStart, {target: {value: 'Cracow'}});
        expect(inputStart.value).toBe('Cracow');

        const inputEnd = container.getByLabelText("Destination") as HTMLInputElement;
        fireEvent.change(inputEnd, {target: {value: 'Warsaw'}});
        expect(inputEnd.value).toBe('Warsaw');
    });
})


