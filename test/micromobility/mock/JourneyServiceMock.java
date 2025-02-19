package micromobility.mock;

import micromobility.JourneyService;
import data.GeographicPoint;
import data.UserAccount;
import data.VehicleID;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class JourneyServiceMock extends JourneyService {
    private boolean setServiceInitCalled = false;
    private boolean setServiceFinishCalled = false;
    private boolean calculateServiceCostCalled = false;

    private GeographicPoint mockStartLocation;
    private LocalDateTime mockStartTime;
    private BigDecimal mockServiceCost;

    public JourneyServiceMock(UserAccount user, VehicleID vehicle) {
        super(user, vehicle);
    }

    @Override
    public void setServiceInit(GeographicPoint startLocation, LocalDateTime startTime) {
        setServiceInitCalled = true;
        this.mockStartLocation = startLocation;
        this.mockStartTime = startTime;
        super.setServiceInit(startLocation, startTime); // Crida a la superclasse
    }

    @Override
    public void setServiceFinish(GeographicPoint endLocation, LocalDateTime endTime, float averageSpeed, float distance, int duration) {
        setServiceFinishCalled = true;
    }

    @Override
    public void calculateServiceCost(BigDecimal ratePerKm, BigDecimal ratePerMinute) {
        calculateServiceCostCalled = true;
        // Simular un cost fix per a la prova
        this.mockServiceCost = new BigDecimal("10.00");
    }

    public boolean isSetServiceInitCalled() {
        return setServiceInitCalled;
    }

    public boolean isSetServiceFinishCalled() {
        return setServiceFinishCalled;
    }

    public boolean isCalculateServiceCostCalled() {
        return calculateServiceCostCalled;
    }

    public GeographicPoint getMockStartLocation() {
        return mockStartLocation;
    }

    public LocalDateTime getMockStartTime() {
        return mockStartTime;
    }

    //PLANNED TO USE BUT FINALLY DID NOT
/*
    public GeographicPoint getMockEndLocation() {
        return mockEndLocation;
    }

    public LocalDateTime getMockEndTime() {
        return mockEndTime;
    }

    public float getMockAverageSpeed() {
        return mockAverageSpeed;
    }

    public float getMockDistance() {
        return mockDistance;
    }

    public int getMockDuration() {
        return mockDuration;
    }
*/
    public BigDecimal getMockServiceCost() {
        return mockServiceCost;
    }
}
