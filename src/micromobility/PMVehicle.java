package micromobility;

import data.GeographicPoint;
import micromobility.exceptions.ProceduralException;

public class PMVehicle {
    private final String vehicleId;
    private PMVState state;
    private GeographicPoint location;

    public PMVehicle(String id, GeographicPoint initialLocation) {
        this.vehicleId = id;
        this.location = initialLocation;
        this.state = PMVState.Available;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public PMVState getState() {
        return state;
    }

    public GeographicPoint getLocation() {
        return location;
    }

    public void setNotAvailb() throws ProceduralException {
        if (state != PMVState.Available) {
            throw new ProceduralException("Vehicle must be " + PMVState.Available + "  to set it to " + PMVState.NotAvailable);
        }
        this.state = PMVState.NotAvailable;
    }

    public void setUnderWay() throws ProceduralException {
        if (state != PMVState.NotAvailable) {
            throw new ProceduralException("Vehicle must be  " + PMVState.NotAvailable + " to set it to " + PMVState.UnderWay);
        }
        this.state = PMVState.UnderWay;
    }

    public void setAvailb() throws ProceduralException {
        if (state != PMVState.UnderWay) {
            throw new ProceduralException("Vehicle must be " + PMVState.UnderWay + " to set it to " + PMVState.Available);
        }
        this.state = PMVState.Available;
    }

    public void setLocation(GeographicPoint gP) {
        this.location = gP;
    }
}
