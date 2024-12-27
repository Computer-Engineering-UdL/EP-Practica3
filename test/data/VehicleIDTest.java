package data;

import org.junit.jupiter.api.Test;
import utils.NumberUtils;

import static org.junit.jupiter.api.Assertions.*;

public class VehicleIDTest {

    @Test
    void testVehicleIDCreation() {
        String id = NumberUtils.generateUUID();
        VehicleID vehicleID = new VehicleID(id);
        assertEquals(id, vehicleID.getId());
    }

    @Test
    void testVehicleIDEquality() {
        String id = NumberUtils.generateUUID();
        VehicleID vehicleID1 = new VehicleID(id);
        VehicleID vehicleID2 = new VehicleID(id);
        assertEquals(vehicleID1, vehicleID2);
        assertEquals(vehicleID1.hashCode(), vehicleID2.hashCode());
    }

    @Test
    void testVehicleIDInequality() {
        VehicleID vehicleID1 = new VehicleID(NumberUtils.generateUUID());
        VehicleID vehicleID2 = new VehicleID(NumberUtils.generateUUID());
        assertNotEquals(vehicleID1, vehicleID2);
        assertNotEquals(vehicleID1.hashCode(), vehicleID2.hashCode());
    }

    @Test
    void testVehicleIDToString() {
        String id = NumberUtils.generateUUID();
        VehicleID vehicleID = new VehicleID(id);
        assertEquals("VehicleID: {id = " + id + "}", vehicleID.toString());
    }

    @Test
    void testInvalidVehicleIDNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new VehicleID(null));
        assertEquals("VehicleID cannot be null or empty", exception.getMessage());
    }

    @Test
    void testInvalidVehicleIDEmpty() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new VehicleID(""));
        assertEquals("VehicleID cannot be null or empty", exception.getMessage());
    }

    @Test
    void testInvalidVehicleIDIncorrectPattern() {
        String invalidId = "invalid-uuid";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new VehicleID(invalidId));
        assertEquals("VehicleID must be a valid UUID (32 hexadecimal characters)", exception.getMessage());
    }
}
