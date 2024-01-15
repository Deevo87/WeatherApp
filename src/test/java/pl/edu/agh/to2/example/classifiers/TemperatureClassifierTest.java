package pl.edu.agh.to2.example.classifiers;

import org.junit.jupiter.api.Test;
import pl.edu.agh.to2.example.exceptions.RainClassifyingException;
import pl.edu.agh.to2.example.exceptions.TemperatureException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TemperatureClassifierTest {

    @Test
    void testClassifyRain() throws RainClassifyingException, TemperatureException {

        //given
        Double temperatureExtremelyCold = -35.0;
        Double temperatureHot = 25.0;
        Double temperatureLastFloor = 60.0;

        //when
        String extremelyColdCategory = TemperatureClassifier.classifyTemperature(temperatureExtremelyCold);
        String hotCategory = TemperatureClassifier.classifyTemperature(temperatureHot);
        String lastFloorCategory = TemperatureClassifier.classifyTemperature(temperatureLastFloor);

        //then
        assertEquals("Extremely Cold", extremelyColdCategory);
        assertEquals("Hot", hotCategory);
        assertEquals("Hell's last floor", lastFloorCategory);
    }
}
