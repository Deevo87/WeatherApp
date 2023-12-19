import React from 'react';
import CurrentWeatherPage from './CurrentWeatherPage';
import {fireEvent, render} from "@testing-library/react";
import exp from "constants";

describe(CurrentWeatherPage, () => {
    it('should render city input', () => {
        const container = render(<CurrentWeatherPage />);

        const inputStart = container.getByLabelText("City") as HTMLInputElement;
        fireEvent.change(inputStart, {target: {value: 'Cracow'}});
        expect(inputStart.value).toBe('Cracow');
    });
})


