package pl.edu.agh.to2.example.classifiers;

import org.junit.jupiter.api.Test;
import pl.edu.agh.to2.example.exceptions.RainClassifyingException;
import pl.edu.agh.to2.example.exceptions.TemperatureException;
import pl.edu.agh.to2.example.exceptions.WindClassifyingException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WindClassifierTest {
    @Test
    void testClassifyRain() throws RainClassifyingException, TemperatureException, WindClassifyingException {

        //given
        Double windCalm = 0.5;
        Double windModerateBreeze = 15.0;
        Double windHurricane = 150.0;

        //when
        String calmCategory = WindClassifier.classifyWind(windCalm);
        String moderateBreezeCategory = WindClassifier.classifyWind(windModerateBreeze);
        String HurricaneCategory = WindClassifier.classifyWind(windHurricane);

        //then
        assertEquals("Calm", calmCategory);
        assertEquals("Moderate Breeze", moderateBreezeCategory);
        assertEquals("Hurricane", HurricaneCategory);
    }
}
