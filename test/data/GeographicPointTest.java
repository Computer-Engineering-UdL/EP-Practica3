package data;

import org.junit.jupiter.api.Test;
import utils.NumberUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class GeographicPointTest {
    @Test
    void testGeographicPointCreation() {
        float latitude = NumberUtils.generateRandomLatitude();
        float longitude = NumberUtils.generateRandomLongitude();
        GeographicPoint point = new GeographicPoint(latitude, longitude);
        assertEquals(latitude, point.getLatitude());
        assertEquals(longitude, point.getLongitude());
    }

    @Test
    void testGeographicPointEquality() {
        float latitude = NumberUtils.generateRandomLatitude();
        float longitude = NumberUtils.generateRandomLongitude();
        GeographicPoint point1 = new GeographicPoint(latitude, longitude);
        GeographicPoint point2 = new GeographicPoint(latitude, longitude);
        assertEquals(point1, point2);
        assertEquals(point1.hashCode(), point2.hashCode());
    }

    @Test
    void testGeographicPointInequality() {
        float latitude1 = NumberUtils.generateRandomLatitude();
        float longitude1 = NumberUtils.generateRandomLongitude();

        float latitude2 = NumberUtils.generateRandomLatitude();
        float longitude2 = NumberUtils.generateRandomLongitude();

        GeographicPoint point1 = new GeographicPoint(latitude1, longitude1);
        GeographicPoint point2 = new GeographicPoint(latitude2, longitude2);

        // In case coordinates are the same, the test is called again
        if (point1.equals(point2)) {
            testGeographicPointInequality();
            return;
        }

        assertNotEquals(point1, point2);
        assertNotEquals(point1.hashCode(), point2.hashCode());
    }

    @Test
    void testGeographicPointZeroCoordinates() {
        GeographicPoint point = new GeographicPoint(0.0f, 0.0f);
        assertEquals(0.0f, point.getLatitude());
        // TODO: This is just a workflow test
        assertNotEquals(0.0f, point.getLongitude());
    }

    @Test
    void testGeographicPointToString() {
        GeographicPoint point = new GeographicPoint(NumberUtils.generateRandomLatitude(), NumberUtils.generateRandomLongitude());
        assertEquals("Geographic point: {latitude = " + point.getLatitude() +
                " longitude = " + point.getLongitude() + "}", point.toString());
    }
}
