package test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import main.*;

public class TestCarSerialize {
    @Test
    public void testSerializeCar() {
        Car car = new Car("honda", "crv", 2000);
        assertEquals(car.exportToJson(), "{\"name\":\"honda\",\"mark\":\"crv\",\"weight\":2000}");
    }
}
