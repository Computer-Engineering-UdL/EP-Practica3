package micromobility;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import data.GeographicPoint;
import data.UserAccount;
import data.VehicleID;

public class JourneyService {
    private final UserAccount userAccount;
    private final VehicleID vehicleID;
    private GeographicPoint startLocation;
    private GeographicPoint endLocation;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private float averageSpeed; // Speed [km/h]
    private float distance; // Distance [km]
    private int duration; // Duration [minutes]
    private BigDecimal serviceCost; // Cost of the service [€]

    public JourneyService(UserAccount userAccount, VehicleID vehicleID) {
        if (userAccount == null || vehicleID == null) {
            throw new IllegalArgumentException("UserAccount and VehicleID cannot be null");
        }
        this.userAccount = userAccount;
        this.vehicleID = vehicleID;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public VehicleID getVehicleID() {
        return vehicleID;
    }

    public GeographicPoint getStartLocation() {
        return startLocation;
    }

    public GeographicPoint getEndLocation() {
        return endLocation;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public float getAverageSpeed() {
        return averageSpeed;
    }

    public float getDistance() {
        return distance;
    }

    public int getDuration() {
        return duration;
    }

    public BigDecimal getServiceCost() {
        return serviceCost;
    }

    public void setServiceInit(GeographicPoint startLocation) {
        setServiceInit(startLocation, LocalDateTime.now());
    }

    public void setServiceInit(GeographicPoint startLocation, LocalDateTime startTime) {
        if (startLocation == null || startTime == null) {
            throw new IllegalArgumentException("Start location and start time cannot be null");
        }
        this.startLocation = startLocation;
        this.startTime = startTime;
    }

    public void setServiceFinish(GeographicPoint endLocation, LocalDateTime endTime, float averageSpeed, float distance, int duration) {
        if (endLocation == null || endTime == null || averageSpeed < 0 || distance < 0 || duration < 0) {
            throw new IllegalArgumentException("Invalid arguments for finishing the service");
        }
        this.endLocation = endLocation;
        this.endTime = endTime;
        this.averageSpeed = averageSpeed;
        this.distance = distance;
        this.duration = duration;
    }

    public void calculateServiceCost(BigDecimal ratePerKm, BigDecimal ratePerMinute) {
        if (ratePerKm == null || ratePerMinute == null || ratePerKm.compareTo(BigDecimal.ZERO) < 0 || ratePerMinute.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Rates cannot be null or negative");
        }
        BigDecimal costForDistance = ratePerKm.multiply(BigDecimal.valueOf(distance));
        BigDecimal costForDuration = ratePerMinute.multiply(BigDecimal.valueOf(duration));
        this.serviceCost = costForDistance.add(costForDuration);
    }
}
