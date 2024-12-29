package micromobility;

import micromobility.mock.JourneyServiceFactoryMock;
import micromobility.mock.JourneyServiceMock;
import data.GeographicPoint;
import data.StationID;
import data.UserAccount;
import data.VehicleID;
import micromobility.exceptions.PMVNotAvailException;
import micromobility.exceptions.ProceduralException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.mock.ArduinoMicroControllerMock;
import services.mock.QRDecoderMock;
import services.mock.ServerMock;
import services.mock.UnbondedBTSignalMock;

import java.awt.image.BufferedImage;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class JourneyRealizeHandlerTest {

    private QRDecoderMock qrDecoderMock;
    private ServerMock serverMock;
    private UnbondedBTSignalMock btSignalMock;
    private ArduinoMicroControllerMock arduinoMock;
    private JourneyServiceFactoryMock journeyServiceFactoryMock;
    private JourneyRealizeHandler handler;

    @BeforeEach
    void setup() {
        serverMock = new ServerMock();
        qrDecoderMock = new QRDecoderMock();
        btSignalMock = new UnbondedBTSignalMock();
        arduinoMock = new ArduinoMicroControllerMock();
        journeyServiceFactoryMock = new JourneyServiceFactoryMock();
        handler = new JourneyRealizeHandler(serverMock, qrDecoderMock, btSignalMock, arduinoMock, journeyServiceFactoryMock);
        serverMock.reset();
    }

    @Test
    void testScanQRSuccess() throws Exception {
        UserAccount user = new UserAccount("123e4567e89b12d3a456426655440000");
        BufferedImage qrImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        StationID stationID = new StationID("123e4567e89b12d3a456426655440000");
        GeographicPoint location = new GeographicPoint(40.0F, -3.0F);
        VehicleID expectedVehicleID = new VehicleID("123e4567e89b12d3a456426655440001");

        qrDecoderMock.setMockVehicleID(expectedVehicleID);

        JourneyServiceMock journeyMock = new JourneyServiceMock(user, expectedVehicleID);
        journeyServiceFactoryMock.setJourneyServiceMock(journeyMock);

        handler.scanQR(user, qrImage, stationID, location);

        assertTrue(journeyServiceFactoryMock.isCreateJourneyServiceCalled(), "La fábrica hauria de ser cridada");
        assertEquals(user, journeyServiceFactoryMock.getLastUser(), "El user hauria de ser el proporcionat");
        assertEquals(expectedVehicleID, journeyServiceFactoryMock.getLastVehicle(), "El vehicleID hauria de ser el proporcionat");

        assertTrue(journeyMock.isSetServiceInitCalled(), "setServiceInit hauria de ser cridat");
        assertEquals(location, journeyMock.getMockStartLocation(), "La ubicació inicial hauria de coincidir");
        assertNotNull(handler.getCurrentJourney(), "CurrentJourney hauria de ser inicialitzada");
        assertEquals(journeyMock, handler.getCurrentJourney(), "CurrentJourney hauria de ser el mock proporcionat");

        assertFalse(btSignalMock.isBroadcastCalled(), "No s'hauria d'emetre una senyal BT durant scanQR");
    }

    @Test
    void testStartDrivingWithoutJourney() {
        ProceduralException exception = assertThrows(ProceduralException.class, () -> {
            handler.startDriving();
        });
        assertEquals("No active journey to start driving", exception.getMessage());
    }

    @Test
    void testStopDriving() throws Exception {
        UserAccount user = new UserAccount("123e4567e89b12d3a456426655440000");
        BufferedImage qrImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        StationID stationID = new StationID("123e4567e89b12d3a456426655440000");
        GeographicPoint startLocation = new GeographicPoint(40.0F, -3.0F);
        GeographicPoint endLocation = new GeographicPoint(41.0F, -4.0F);
        VehicleID vehicleID = new VehicleID("123e4567e89b12d3a456426655440001");

        qrDecoderMock.setMockVehicleID(vehicleID);

        JourneyServiceMock journeyMock = new JourneyServiceMock(user, vehicleID);
        journeyServiceFactoryMock.setJourneyServiceMock(journeyMock);

        handler.scanQR(user, qrImage, stationID, startLocation);

        handler.stopDriving(endLocation, stationID);

        assertTrue(journeyMock.isSetServiceFinishCalled(), "setServiceFinish hauria de ser cridat");
        assertTrue(journeyMock.isCalculateServiceCostCalled(), "calculateServiceCost hauria de ser cridat");
        assertEquals(new BigDecimal("10.00"), journeyMock.getMockServiceCost(), "El cost del servei hauria de coincidir amb el valor simul·lat");

        assertNull(handler.getCurrentJourney(), "CurrentJourney hauria de ser nul després de finalitzar la jornada");

        assertTrue(serverMock.activePairings.isEmpty(), "El mapa de emparellaments hauria de estar buit");
    }

    @Test
    void testStartDrivingSuccess() throws Exception {
        UserAccount user = new UserAccount("123e4567e89b12d3a456426655440000");
        BufferedImage qrImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        StationID stationID = new StationID("123e4567e89b12d3a456426655440000");
        GeographicPoint startLocation = new GeographicPoint(40.0F, -3.0F);
        VehicleID vehicleID = new VehicleID("123e4567e89b12d3a456426655440001");

        qrDecoderMock.setMockVehicleID(vehicleID);

        JourneyServiceMock journeyMock = new JourneyServiceMock(user, vehicleID);
        journeyServiceFactoryMock.setJourneyServiceMock(journeyMock);

        handler.scanQR(user, qrImage, stationID, startLocation);

        handler.startDriving();

        assertTrue(arduinoMock.isDriving(), "El controlador Arduino hauria de marcar el vehicle com a conduint");
    }

    @Test
    void testBroadcastStationID_EmitsBTSignal() throws Exception {
        StationID stationID = new StationID("123e4567e89b12d3a456426655440000");

        handler.broadcastStationID(stationID);

        assertTrue(serverMock.vehicleAvailability.containsKey(null), "El vehículo hauria de ser registrat en la ubicació");
        assertTrue(btSignalMock.isBroadcastCalled(), "El método BTbroadcast hauria de ser cridat");
    }

    @Test
    void testScanQR_PMVNotAvailable() throws Exception {
        UserAccount user = new UserAccount("123e4567e89b12d3a456426655440000");
        BufferedImage qrImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        StationID stationID = new StationID("123e4567e89b12d3a456426655440000");
        GeographicPoint location = new GeographicPoint(40.0F, -3.0F);
        VehicleID vehicleID = new VehicleID("123e4567e89b12d3a456426655440001");

        qrDecoderMock.setMockVehicleID(vehicleID);

        serverMock.setThrowPMVNotAvailableException(true);

        PMVNotAvailException exception = assertThrows(PMVNotAvailException.class, () -> {
            handler.scanQR(user, qrImage, stationID, location);
        });
        assertEquals("Vehicle is not available", exception.getMessage());

        assertNull(handler.getCurrentJourney(), "CurrentJourney no hauria de ser inicialitzada");
        assertFalse(journeyServiceFactoryMock.isCreateJourneyServiceCalled(), "La fàbrica no hauria de ser cridada");
    }

    @Test
    void testSetServiceInitCalled() throws Exception {
        UserAccount user = new UserAccount("123e4567e89b12d3a456426655440000");
        BufferedImage qrImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        StationID stationID = new StationID("123e4567e89b12d3a456426655440000");
        GeographicPoint startLocation = new GeographicPoint(40.0F, -3.0F);
        VehicleID vehicleID = new VehicleID("123e4567e89b12d3a456426655440001");

        qrDecoderMock.setMockVehicleID(vehicleID);

        JourneyServiceMock journeyMock = new JourneyServiceMock(user, vehicleID);
        journeyServiceFactoryMock.setJourneyServiceMock(journeyMock);

        handler.scanQR(user, qrImage, stationID, startLocation);

        assertTrue(journeyMock.isSetServiceInitCalled(), "setServiceInit hauria de ser cridat");
        assertEquals(startLocation, journeyMock.getMockStartLocation(), "La ubicació inicial hauria de coincidir");
        assertNotNull(journeyMock.getMockStartTime(), "La hora de inicio no hauria de ser nul·la");
    }

    @Test
    void testCalculateServiceCostCalled() throws Exception {
        UserAccount user = new UserAccount("123e4567e89b12d3a456426655440000");
        BufferedImage qrImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        StationID stationID = new StationID("123e4567e89b12d3a456426655440000");
        GeographicPoint startLocation = new GeographicPoint(40.0F, -3.0F);
        GeographicPoint endLocation = new GeographicPoint(41.0F, -4.0F);
        VehicleID vehicleID = new VehicleID("123e4567e89b12d3a456426655440001");

        qrDecoderMock.setMockVehicleID(vehicleID);

        JourneyServiceMock journeyMock = new JourneyServiceMock(user, vehicleID);
        journeyServiceFactoryMock.setJourneyServiceMock(journeyMock);

        handler.scanQR(user, qrImage, stationID, startLocation);
        handler.stopDriving(endLocation, stationID);

        BigDecimal ratePerKm = new BigDecimal("1.50");
        BigDecimal ratePerMinute = new BigDecimal("0.20");

        journeyMock.calculateServiceCost(ratePerKm, ratePerMinute);

        assertTrue(journeyMock.isCalculateServiceCostCalled(), "calculateServiceCost hauria de ser cridat");
        assertEquals(new BigDecimal("10.00"), journeyMock.getMockServiceCost(), "El cost del servei hauria de coincidir amb el valor simul·lat");
    }

}
