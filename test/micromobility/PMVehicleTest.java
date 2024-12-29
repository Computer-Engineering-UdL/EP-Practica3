package micromobility;

import data.GeographicPoint;
import data.VehicleID;
import micromobility.exceptions.ProceduralException;
import org.junit.jupiter.api.Test;
import utils.NumberUtils;

import static org.junit.jupiter.api.Assertions.*;

class PMVehicleTest {
    @Test
    void testSetNotAvailableValid() throws ProceduralException {
        GeographicPoint location = new GeographicPoint(NumberUtils.generateRandomLatitude(), NumberUtils.generateRandomLongitude());
        VehicleID vehicleID = new VehicleID(NumberUtils.generateUUID());
        PMVehicle vehicle = new PMVehicle(vehicleID, location);
        vehicle.setNotAvailb();
        assertEquals(PMVState.NotAvailable, vehicle.getState());
    }

    @Test
    void testSetNotAvailableInvalid() {
        GeographicPoint location = new GeographicPoint(NumberUtils.generateRandomLatitude(), NumberUtils.generateRandomLongitude());
        VehicleID vehicleID = new VehicleID(NumberUtils.generateUUID());
        PMVehicle vehicle = new PMVehicle(vehicleID, location);
        assertThrows(ProceduralException.class, vehicle::setUnderWay, "No es pot passar a UnderWay si el vehicle no està a NotAvailable");
    }

    @Test
    void testSetUnderWayValid() throws ProceduralException {
        GeographicPoint location = new GeographicPoint(NumberUtils.generateRandomLatitude(), NumberUtils.generateRandomLongitude());
        VehicleID vehicleID = new VehicleID(NumberUtils.generateUUID());
        PMVehicle vehicle = new PMVehicle(vehicleID, location);
        vehicle.setNotAvailb();
        vehicle.setUnderWay();
        assertEquals(PMVState.UnderWay, vehicle.getState());
    }

    @Test
    void testSetUnderWayInvalid() throws ProceduralException {
        GeographicPoint location = new GeographicPoint(NumberUtils.generateRandomLatitude(), NumberUtils.generateRandomLongitude());
        VehicleID vehicleID = new VehicleID(NumberUtils.generateUUID());
        PMVehicle vehicle = new PMVehicle(vehicleID, location);
        assertThrows(ProceduralException.class, vehicle::setUnderWay, "No es pot passar a UnderWay si el vehicle no està a NotAvailable");
    }

    @Test
    void testSetAvailableValid() throws ProceduralException {
        GeographicPoint location = new GeographicPoint(NumberUtils.generateRandomLatitude(), NumberUtils.generateRandomLongitude());
        VehicleID vehicleID = new VehicleID(NumberUtils.generateUUID());
        PMVehicle vehicle = new PMVehicle(vehicleID, location);
        vehicle.setNotAvailb();
        vehicle.setUnderWay();
        vehicle.setAvailb();
        assertEquals(PMVState.Available, vehicle.getState());
    }

    @Test
    void testSetAvailableInvalid() throws ProceduralException {
        GeographicPoint location = new GeographicPoint(NumberUtils.generateRandomLatitude(), NumberUtils.generateRandomLongitude());
        VehicleID vehicleID = new VehicleID(NumberUtils.generateUUID());
        PMVehicle vehicle = new PMVehicle(vehicleID, location);
        assertThrows(ProceduralException.class, vehicle::setAvailb, "No es pot passar a Available si el vehicle no està a UnderWay");
    }

    @Test
    void testSetLocation() {
        GeographicPoint initialLocation = new GeographicPoint(NumberUtils.generateRandomLatitude(), NumberUtils.generateRandomLongitude());
        GeographicPoint newLocation = new GeographicPoint(NumberUtils.generateRandomLatitude(), NumberUtils.generateRandomLongitude());
        VehicleID vehicleID = new VehicleID(NumberUtils.generateUUID());
        PMVehicle vehicle = new PMVehicle(vehicleID, initialLocation);
        vehicle.setLocation(newLocation);
        assertEquals(newLocation, vehicle.getLocation(), "La ubicació del vehicle hauria de ser actualitzada correctament");
    }
}
