package micromobility;

import data.GeographicPoint;
import data.UserAccount;
import data.VehicleID;
import micromobility.JourneyService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JourneyServiceTest {

    @Test
    void testSetServiceInitValid() {
        UserAccount user = new UserAccount("user123");
        VehicleID vehicle = new VehicleID("vehicle123");
        JourneyService journey = new JourneyService(user, vehicle);
        GeographicPoint startLocation = new GeographicPoint(40.0F, (float) -3.0);
        LocalDateTime startTime = LocalDateTime.now();
        journey.setServiceInit(startLocation, startTime);
        assertEquals(startLocation, journey.getStartLocation());
        assertEquals(startTime, journey.getStartTime());
    }

    @Test
    void testSetServiceFinishValid() {
        UserAccount user = new UserAccount("user123");
        VehicleID vehicle = new VehicleID("vehicle123");
        JourneyService journey = new JourneyService(user, vehicle);
        GeographicPoint endLocation = new GeographicPoint(41.0F, (float) -4.0);
        LocalDateTime endTime = LocalDateTime.now();
        journey.setServiceFinish(endLocation, endTime, 20.0f, 10.0f, 30);
        assertEquals(endLocation, journey.getEndLocation());
        assertEquals(endTime, journey.getEndTime());
        assertEquals(20.0f, journey.getAverageSpeed());
    }

    @Test
    void testCalculateServiceCost() {
        UserAccount user = new UserAccount("user123");
        VehicleID vehicle = new VehicleID("vehicle123");
        JourneyService journey = new JourneyService(user, vehicle);
        journey.setServiceFinish(null, null, 0, 10.0f, 30);
        journey.calculateServiceCost(new BigDecimal("0.5"), new BigDecimal("0.2"));
        assertEquals(new BigDecimal("7.0"), journey.getServiceCost());
    }
}