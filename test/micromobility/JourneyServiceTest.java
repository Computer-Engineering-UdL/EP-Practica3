package micromobility;

import data.GeographicPoint;
import data.UserAccount;
import data.VehicleID;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JourneyServiceTest {

    @Test
    void testSetServiceInitValid() {
        // UUID válido para UserAccount y VehicleID
        UserAccount user = new UserAccount("123e4567e89b12d3a456426655440000");
        VehicleID vehicle = new VehicleID("223e4567e89b12d3a456426655440111");
        JourneyService journey = new JourneyService(user, vehicle);

        GeographicPoint startLocation = new GeographicPoint(40.0F, -3.0F);
        LocalDateTime startTime = LocalDateTime.now();

        journey.setServiceInit(startLocation, startTime);

        assertEquals(startLocation, journey.getStartLocation());
        assertEquals(startTime, journey.getStartTime());
    }

    @Test
    void testSetServiceFinishValid() {
        UserAccount user = new UserAccount("123e4567e89b12d3a456426655440000");
        VehicleID vehicle = new VehicleID("223e4567e89b12d3a456426655440111");
        JourneyService journey = new JourneyService(user, vehicle);

        GeographicPoint endLocation = new GeographicPoint(41.0F, -4.0F);
        LocalDateTime endTime = LocalDateTime.now();

        journey.setServiceFinish(endLocation, endTime, 20.0f, 10.0f, 30);

        assertEquals(endLocation, journey.getEndLocation());
        assertEquals(endTime, journey.getEndTime());
        assertEquals(20.0f, journey.getAverageSpeed());
    }

    @Test
    void testCalculateServiceCost() {
        UserAccount user = new UserAccount("123e4567e89b12d3a456426655440000");
        VehicleID vehicle = new VehicleID("223e4567e89b12d3a456426655440111");
        JourneyService journey = new JourneyService(user, vehicle);

        GeographicPoint endLocation = new GeographicPoint(41.0F, -4.0F);
        LocalDateTime endTime = LocalDateTime.now();
        journey.setServiceFinish(endLocation, endTime, 20.0f, 10.0f, 30);

        journey.calculateServiceCost(new BigDecimal("0.5"), new BigDecimal("0.2"));

        assertEquals(new BigDecimal("11.00"), journey.getServiceCost());
    }

    @Test
    void testSetServiceInitInvalid() {
        UserAccount user = new UserAccount("123e4567e89b12d3a456426655440000");
        VehicleID vehicle = new VehicleID("223e4567e89b12d3a456426655440111");
        JourneyService journey = new JourneyService(user, vehicle);

        assertThrows(IllegalArgumentException.class, () -> {
            journey.setServiceInit(null, LocalDateTime.now());
        }, "Hauria de llançar IllegalArgumentException si startLocation és nul");

        assertThrows(IllegalArgumentException.class, () -> {
            journey.setServiceInit(new GeographicPoint(40.0F, -3.0F), null);
        }, "Hauria de llançar IllegalArgumentException si startTime és nul");
    }

    @Test
    void testSetServiceFinishInvalid() {
        UserAccount user = new UserAccount("123e4567e89b12d3a456426655440000");
        VehicleID vehicle = new VehicleID("223e4567e89b12d3a456426655440111");
        JourneyService journey = new JourneyService(user, vehicle);

        assertThrows(IllegalArgumentException.class, () -> {
            journey.setServiceFinish(null, LocalDateTime.now(), 20.0f, 10.0f, 30);
        }, "Hauria de llançar IllegalArgumentException si endLocation és nul");

        assertThrows(IllegalArgumentException.class, () -> {
            journey.setServiceFinish(new GeographicPoint(41.0F, -4.0F), null, 20.0f, 10.0f, 30);
        }, "Hauria de llançar IllegalArgumentException si endTime és nul");

        assertThrows(IllegalArgumentException.class, () -> {
            journey.setServiceFinish(new GeographicPoint(41.0F, -4.0F), LocalDateTime.now(), -1.0f, 10.0f, 30);
        }, "Hauria de llançar IllegalArgumentException si averageSpeed és negatiu");

        assertThrows(IllegalArgumentException.class, () -> {
            journey.setServiceFinish(new GeographicPoint(41.0F, -4.0F), LocalDateTime.now(), 20.0f, -5.0f, 30);
        }, "Hauria de llançar IllegalArgumentException si distance és negativa");

        assertThrows(IllegalArgumentException.class, () -> {
            journey.setServiceFinish(new GeographicPoint(41.0F, -4.0F), LocalDateTime.now(), 20.0f, 10.0f, -10);
        }, "Hauria de llançar IllegalArgumentException si duration és negativa");
    }

    @Test
    void testCalculateServiceCostInvalid() {
        UserAccount user = new UserAccount("123e4567e89b12d3a456426655440000");
        VehicleID vehicle = new VehicleID("223e4567e89b12d3a456426655440111");
        JourneyService journey = new JourneyService(user, vehicle);

        assertThrows(IllegalArgumentException.class, () -> {
            journey.calculateServiceCost(null, new BigDecimal("0.2"));
        }, "Hauria de llançar IllegalArgumentException si ratePerKm és nul");

        assertThrows(IllegalArgumentException.class, () -> {
            journey.calculateServiceCost(new BigDecimal("0.5"), null);
        }, "Hauria de llançar IllegalArgumentException si ratePerMinute és nul");

        assertThrows(IllegalArgumentException.class, () -> {
            journey.calculateServiceCost(new BigDecimal("-0.5"), new BigDecimal("0.2"));
        }, "Hauria de llançar IllegalArgumentException si ratePerKm és negatiu");

        assertThrows(IllegalArgumentException.class, () -> {
            journey.calculateServiceCost(new BigDecimal("0.5"), new BigDecimal("-0.2"));
        }, "Hauria de llançar IllegalArgumentException si ratePerMinute és negatiu");
    }
}
