package com.smrc.javadesignpatterns.architectural.abstractdocument;

import com.smrc.javadesignpatterns.architectural.abstractdocument.domain.Car;
import com.smrc.javadesignpatterns.architectural.abstractdocument.domain.Part;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

public class DomainTest {

    private static final String TEST_PART_TYPE = "test-part-type";
    private static final String TEST_PART_MODEL = "test-part-model";
    private static final long TEST_PART_PRICE = 0L;

    private static final String TEST_CAR_MODEL = "test-car-model";
    private static final long TEST_CAR_PRICE = 1L;

    @Test
    void shouldConstructPart() {
        Map<String, Object> partProperties = Map.of(
                Property.TYPE.toString(), TEST_PART_TYPE,
                Property.MODEL.toString(), TEST_PART_MODEL,
                Property.PRICE.toString(), (Object) TEST_PART_PRICE
        );
        Part part = new Part(partProperties);
        System.out.println(part.getType().orElseThrow());
        System.out.println(part.getPrice().orElseThrow());
        System.out.println(part.getModel().orElseThrow());
    }

    @Test
    void shouldConstructCar() {
//        var carProperties = Map.of(
//                Property.MODEL.toString(), TEST_CAR_MODEL,
//                Property.PRICE.toString(), TEST_CAR_PRICE,
//                Property.PARTS.toString(), List.of(Map.of(), Map.of())
//        );
//        var car = new Car(carProperties);
//        Assertions.assertEquals(TEST_CAR_MODEL, car.getModel().orElseThrow());
//        Assertions.assertEquals(TEST_CAR_PRICE, car.getPrice().orElseThrow());
//        Assertions.assertEquals(2, car.getParts().count());

        var wheelProperties = Map.of(
                Property.TYPE.toString(), "wheel",
                Property.MODEL.toString(), "15C",
                Property.PRICE.toString(), 100L);

        var doorProperties = Map.of(
                Property.TYPE.toString(), "door",
                Property.MODEL.toString(), "Lambo",
                Property.PRICE.toString(), 300L);

        var carProperties = Map.of(
                Property.MODEL.toString(), "300SL",
                Property.PRICE.toString(), 10000L,
                Property.PARTS.toString(), List.of(wheelProperties, doorProperties));

        var car = new Car(carProperties);

        System.out.println("car.getModel().orElseThrow() = " + car.getModel().orElseThrow());
        System.out.println("car.getPrice().orElseThrow() = " + car.getPrice().orElseThrow());

        car.getParts().forEach(p -> {
            System.out.println("p.getType().orElse(null) = " + p.getType().orElse(null));
            System.out.println("p.getModel().orElse(null) = " + p.getModel().orElse(null));
            System.out.println("p.getPrice().orElse(null) = " + p.getPrice().orElse(null));
            }
        );
    }
}
