package test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import main.*;

public class TestCarDeserialize {
    @Test
    public void testSerializeCar() {
        Car car = new Car("honda", "crv", 2000);
        assertEquals(Main.deserializeCar("{\"name\":\"honda\",\"mark\":\"crv\",\"weight\":2000}").beep(), car.beep());
    }
}
