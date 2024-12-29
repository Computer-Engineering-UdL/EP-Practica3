package micromobility;

import micromobility.mock.JourneyServiceFactoryMock;
import micromobility.mock.JourneyServiceMock;
import data.GeographicPoint;
import data.StationID;
import data.UserAccount;
import data.VehicleID;
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
    private JourneyRealizeHandler handler;
    private JourneyServiceFactoryMock journeyServiceFactoryMock;

    @BeforeEach
    void setup() {
        serverMock = new ServerMock();
        qrDecoderMock = new QRDecoderMock();
        UnbondedBTSignalMock btSignalMock = new UnbondedBTSignalMock();
        ArduinoMicroControllerMock arduinoMock = new ArduinoMicroControllerMock();
        journeyServiceFactoryMock = new JourneyServiceFactoryMock();
        handler = new JourneyRealizeHandler(serverMock, qrDecoderMock, btSignalMock, arduinoMock, journeyServiceFactoryMock);
    }

    @Test
    void testScanQRSuccess() throws Exception {
        UserAccount user = new UserAccount("123e4567e89b12d3a456426655440000");
        BufferedImage qrImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        StationID stationID = new StationID("123e4567e89b12d3a456426655440000");
        GeographicPoint location = new GeographicPoint(40.0F, -3.0F);
        VehicleID expectedVehicleID = new VehicleID("123e4567e89b12d3a456426655440001");
        qrDecoderMock.setVehicleID(expectedVehicleID);

        JourneyServiceMock journeyMock = new JourneyServiceMock(user, expectedVehicleID);
        journeyServiceFactoryMock.setJourneyServiceMock(journeyMock);

        handler.scanQR(user, qrImage, stationID, location);

        // Assercions millorades
        assertNotNull(handler.getCurrentJourney(), "CurrentJourney hauria de ser inicialitzada");
        assertTrue(journeyServiceFactoryMock.isCreateJourneyServiceCalled(), "La fàbrica hauria de ser cridada");
        assertEquals(journeyMock, handler.getCurrentJourney(), "CurrentJourney hauria de ser el mock proporcionat");
        assertTrue(journeyMock.isSetServiceInitCalled(), "setServiceInit hauria de ser cridat");
        assertEquals(location, journeyMock.getMockStartLocation(), "La ubicació inicial hauria de coincidir");
    }

    @Test
    void testStartDrivingWithoutJourney() {
        assertThrows(ProceduralException.class, handler::startDriving);
    }

    @Test
    void testStopDriving() throws Exception {
        UserAccount user = new UserAccount("123e4567e89b12d3a456426655440000");
        BufferedImage qrImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        StationID stationID = new StationID("123e4567e89b12d3a456426655440000");
        GeographicPoint startLocation = new GeographicPoint(40.0F, -3.0F);
        GeographicPoint endLocation = new GeographicPoint(41.0F, -4.0F);
        VehicleID vehicleID = new VehicleID("123e4567e89b12d3a456426655440001");
        qrDecoderMock.setVehicleID(vehicleID);
        serverMock.registerLocation(vehicleID, stationID);

        JourneyServiceMock journeyMock = new JourneyServiceMock(user, vehicleID);
        journeyServiceFactoryMock.setJourneyServiceMock(journeyMock);

        handler.scanQR(user, qrImage, stationID, startLocation);
        handler.stopDriving(endLocation, stationID);

        // Assercions millorades
        assertNull(handler.getCurrentJourney(), "CurrentJourney hauria de ser nul després de finalitzar la jornada");
        assertTrue(journeyMock.isSetServiceFinishCalled(), "setServiceFinish hauria de ser cridat");
        assertTrue(journeyMock.isCalculateServiceCostCalled(), "calculateServiceCost hauria de ser cridat");
        assertEquals(new BigDecimal("10.00"), journeyMock.getMockServiceCost(), "El cost del servei hauria de coincidir amb el valor simul·lat");
    }
}
