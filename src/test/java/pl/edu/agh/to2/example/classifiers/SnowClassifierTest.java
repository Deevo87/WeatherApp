package pl.edu.agh.to2.example.classifiers;

import org.junit.jupiter.api.Test;
import pl.edu.agh.to2.example.exceptions.RainClassifyingException;
import pl.edu.agh.to2.example.exceptions.SnowClassifyingException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SnowClassifierTest {

    @Test
    void testClassifyRain() throws RainClassifyingException, SnowClassifyingException {

        //given
        double precipitationAmountVeryLight = 0.5;
        double precipitationAmountLight = 4.0;
        double precipitationAmountModerate = 8.0;
        double precipitationAmountHeavy = 12.0;
        double precipitationAmountVeryHeavy = 50.0;

        //when
        String veryLightCategory = SnowClassifier.classifySnow(precipitationAmountVeryLight);
        String lightCategory = SnowClassifier.classifySnow(precipitationAmountLight);
        String moderateCategory = SnowClassifier.classifySnow(precipitationAmountModerate);
        String heavyCategory = SnowClassifier.classifySnow(precipitationAmountHeavy);
        String veryHeavyCategory = SnowClassifier.classifySnow(precipitationAmountVeryHeavy);

        //then
        assertEquals("Very Light Snow", veryLightCategory);
        assertEquals("Light Snow", lightCategory);
        assertEquals("Moderate Snow", moderateCategory);
        assertEquals("Heavy Snow", heavyCategory);
        assertEquals("Very Heavy Snow", veryHeavyCategory);

    }

    @Test
    void testCompareRainIntensity(){
        //given
        String veryLightSnow = "Very Light Snow";
        String lightSnow = "Light Snow";
        String moderateSnow = "Moderate Snow";
        String heavySnow =  "Heavy Snow";
        String veryHeavySnow= "Very Heavy Snow";

        //when
        int resultVeryHeavyVeryLight = SnowClassifier.compareSnowIntensity(veryHeavySnow, veryLightSnow);
        int resultLightModerate = SnowClassifier.compareSnowIntensity(lightSnow, moderateSnow);
        int resultHeavyModerate = SnowClassifier.compareSnowIntensity(heavySnow, moderateSnow);
        int resultVeryHeavy = SnowClassifier.compareSnowIntensity(veryHeavySnow, veryHeavySnow);

        //then
        assertEquals(-1, resultVeryHeavyVeryLight);
        assertEquals(1, resultLightModerate);
        assertEquals(-1, resultHeavyModerate);
        assertEquals(0, resultVeryHeavy);

    }
}
