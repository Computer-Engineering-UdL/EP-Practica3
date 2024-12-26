package data;

import org.junit.jupiter.api.Test;
import utils.NumberUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StationIDTest {
    @Test
    void testStationIdCreation() {
        String id = NumberUtils.generateUUID();
        StationID stationID = new StationID(id);
        assertEquals(id, stationID.getId());
    }

    @Test
    void testStationIdEquality() {
        String id = NumberUtils.generateUUID();
        StationID stationID1 = new StationID(id);
        StationID stationID2 = new StationID(id);
        assertEquals(stationID1, stationID2);
        assertEquals(stationID1.hashCode(), stationID2.hashCode());
    }

    @Test
    void testStationIdInequality() {
        StationID stationID1 = new StationID(NumberUtils.generateUUID());
        StationID stationID2 = new StationID(NumberUtils.generateUUID());
        assertNotEquals(stationID1, stationID2);
        assertNotEquals(stationID1.hashCode(), stationID2.hashCode());
    }

    @Test
    void testStationIdToString() {
        String id = NumberUtils.generateUUID();
        StationID stationID = new StationID(id);
        assertEquals("StationID: {id = " + id + "}", stationID.toString());
    }

    @Test
    void testInvalidStationIdNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new StationID(null));
        assertEquals("StationID cannot be null or empty", exception.getMessage());
    }

    @Test
    void testInvalidStationIdEmpty() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new StationID(""));
        assertEquals("StationID cannot be null or empty", exception.getMessage());
    }

    @Test
    void testInvalidStationIdIncorrectPattern() {
        String invalidId = "invalid-uuid";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new StationID(invalidId));
        assertEquals("StationID must be a valid UUID (32 hexadecimal characters)", exception.getMessage());
    }
}
