package services;

import data.GeographicPoint;
import data.StationID;
import data.UserAccount;
import data.VehicleID;
import micromobility.JourneyService;
import micromobility.exceptions.InvalidPairingArgsException;
import micromobility.exceptions.PMVNotAvailException;
import micromobility.exceptions.PairingNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.mock.ServerMock;
import utils.NumberUtils;

import java.math.BigDecimal;
import java.net.ConnectException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ServerTest {
    private ServerMock serverMock;
    private UserAccount user;
    private VehicleID vehicle;
    private StationID station;
    private GeographicPoint location;

    @BeforeEach
    void setUp() {
        serverMock = new ServerMock();
        user = new UserAccount(NumberUtils.generateUUID());
        vehicle = new VehicleID(NumberUtils.generateUUID());
        station = new StationID(NumberUtils.generateUUID());
        location = new GeographicPoint(NumberUtils.generateRandomLatitude(), NumberUtils.generateRandomLongitude());
    }

    @Test
    void testCheckPMVAvail_Success() {
        assertDoesNotThrow(() -> serverMock.checkPMVAvail(vehicle));
    }

    @Test
    void testCheckPMVAvail_ThrowsPMVNotAvailException() {
        serverMock.setThrowPMVNotAvailableException(true);
        assertThrows(PMVNotAvailException.class, () -> serverMock.checkPMVAvail(vehicle));
    }

    @Test
    void testCheckPMVAvail_ThrowsConnectException() {
        serverMock.setThrowConnectionException(true);
        assertThrows(ConnectException.class, () -> serverMock.checkPMVAvail(vehicle));
    }

    @Test
    void testRegisterPairing_Success() {
        assertDoesNotThrow(() -> serverMock.registerPairing(user, vehicle, station, location, LocalDateTime.now()));
    }

    @Test
    void testRegisterPairing_ThrowsInvalidPairingArgsException() {
        serverMock.setThrowInvalidPairingArgsException(true);
        assertThrows(InvalidPairingArgsException.class, () -> serverMock.registerPairing(user, vehicle, station, location, LocalDateTime.now()));
    }

    @Test
    void testRegisterPairing_ThrowsConnectException() {
        serverMock.setThrowConnectionException(true);
        assertThrows(ConnectException.class, () -> serverMock.registerPairing(user, vehicle, station, location, LocalDateTime.now()));
    }

    @Test
    void testStopPairing_Success() {
        assertDoesNotThrow(() -> {
            serverMock.registerPairing(user, vehicle, station, location, LocalDateTime.now());
            serverMock.stopPairing(user, vehicle, station, location, LocalDateTime.now(), 15.0f, 3.0f, 10, BigDecimal.TEN);
        });
    }


    @Test
    void testStopPairing_ThrowsInvalidPairingArgsException() {
        serverMock.setThrowInvalidPairingArgsException(true);
        assertThrows(InvalidPairingArgsException.class, () -> {
            serverMock.stopPairing(user, vehicle, station, location, LocalDateTime.now(), 15.0f, 3.0f, 10, BigDecimal.TEN);
        });
    }

    @Test
    void testStopPairing_ThrowsConnectException() {
        serverMock.setThrowConnectionException(true);
        assertThrows(ConnectException.class, () -> {
            serverMock.stopPairing(user, vehicle, station, location, LocalDateTime.now(), 15.0f, 3.0f, 10, BigDecimal.TEN);
        });
    }

    @Test
    void testUnPairRegisterService_Success() {
        JourneyService journeyService = new JourneyService(user, vehicle);
        assertDoesNotThrow(() -> serverMock.unPairRegisterService(journeyService));
    }

    @Test
    void testUnPairRegisterService_ThrowsPairingNotFoundException() {
        serverMock.setThrowPairingNotFoundException(true);
        JourneyService journeyService = new JourneyService(user, vehicle);
        assertThrows(PairingNotFoundException.class, () -> serverMock.unPairRegisterService(journeyService));
    }

    @Test
    void testRegisterLocation_Success() {
        assertDoesNotThrow(() -> serverMock.registerLocation(vehicle, station));
    }
}
