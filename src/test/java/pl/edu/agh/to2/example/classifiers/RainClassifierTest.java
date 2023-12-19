package pl.edu.agh.to2.example.classifiers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.agh.to2.example.exceptions.RainClassifyingException;

import java.awt.image.RasterOp;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RainClassifierTest {

    @Test
    void testClassifyRain() throws RainClassifyingException {

        //given
        Double precipitationAmountLight = 1.1;
        Double precipitationAmountModerate = 5.5;
        Double precipitationAmountHeavy = 8.0;
        Double precipitationAmountVeryHeavy = 80.0;

        //when
        String lightCategory = RainClassifier.classifyRain(precipitationAmountLight);
        String moderateCategory = RainClassifier.classifyRain(precipitationAmountModerate);
        String heavyCategory = RainClassifier.classifyRain(precipitationAmountHeavy);
        String veryHeavyCategory = RainClassifier.classifyRain(precipitationAmountVeryHeavy);

        //then
        assertEquals("Light Rain", lightCategory);
        assertEquals("Moderate Rain", moderateCategory);
        assertEquals("Heavy Rain", heavyCategory);
        assertEquals("Very Heavy Rain", veryHeavyCategory);

    }

    @Test
    void testCompareRainIntensity(){
        //given
        String lightRain = "Light Rain";
        String moderateRain = "Moderate Rain";
        String heavyRain =  "Heavy Rain";
        String veryHeavyRain = "Very Heavy Rain";

        //when
        int resultLightModerate = RainClassifier.compareRainIntensity(lightRain, moderateRain);
        int resultHeavyModerate = RainClassifier.compareRainIntensity(heavyRain, moderateRain);
        int resultVeryHeavy = RainClassifier.compareRainIntensity(veryHeavyRain, veryHeavyRain);

        //then
        assertEquals(1, resultLightModerate);
        assertEquals(-1, resultHeavyModerate);
        assertEquals(0, resultVeryHeavy);

    }
}
