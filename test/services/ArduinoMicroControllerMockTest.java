package services;

import micromobility.exceptions.PMVPhisicalException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.mock.ArduinoMicroControllerMock;

import static org.junit.jupiter.api.Assertions.*;

class ArduinoMicroControllerMockTest {

    private ArduinoMicroControllerMock arduinoMock;

    @BeforeEach
    void setUp() {
        arduinoMock = new ArduinoMicroControllerMock();
    }

    @Test
    void testStartDrivingSuccess() throws PMVPhisicalException {
        arduinoMock.setThrowPhysicalException(false);
        arduinoMock.startDriving();
        assertTrue(arduinoMock.isDriving(), "Vehicle should be marked as driving");
    }

    @Test
    void testStartDrivingThrowsException() {
        arduinoMock.setThrowPhysicalException(true);
        assertThrows(PMVPhisicalException.class, arduinoMock::startDriving, "Exception should be thrown when starting driving");
    }

    @Test
    void testStopDrivingSuccess() throws PMVPhisicalException {
        arduinoMock.setThrowPhysicalException(false);
        arduinoMock.startDriving();
        arduinoMock.stopDriving();
        assertFalse(arduinoMock.isDriving(), "Vehicle should be marked as not driving");
    }

    @Test
    void testStopDrivingThrowsException() {
        arduinoMock.setThrowPhysicalException(true);
        assertThrows(PMVPhisicalException.class, arduinoMock::stopDriving, "Exception should be thrown when stopping driving");
    }

    @Test
    void testBTConnection() {
        arduinoMock.setBTconnection();
        arduinoMock.undoBTconnection();
        assertDoesNotThrow(arduinoMock::setBTconnection);
        assertDoesNotThrow(arduinoMock::undoBTconnection);
    }
}
