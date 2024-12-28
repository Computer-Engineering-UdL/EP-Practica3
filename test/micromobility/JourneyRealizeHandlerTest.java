package micromobility;

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

import static org.junit.jupiter.api.Assertions.*;

class JourneyRealizeHandlerTest {

    private ServerMock serverMock;
    private QRDecoderMock qrDecoderMock;
    private UnbondedBTSignalMock btSignalMock;
    private ArduinoMicroControllerMock arduinoMock;
    private JourneyRealizeHandler handler;

    @BeforeEach
    void setup() {
        serverMock = new ServerMock();
        qrDecoderMock = new QRDecoderMock();
        btSignalMock = new UnbondedBTSignalMock();
        arduinoMock = new ArduinoMicroControllerMock();
        handler = new JourneyRealizeHandler(serverMock, qrDecoderMock, btSignalMock, arduinoMock);
    }

    @Test
    void testScanQRSuccess() throws Exception {
        UserAccount user = new UserAccount("123e4567e89b12d3a456426655440000");
        BufferedImage qrImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        StationID stationID = new StationID("123e4567e89b12d3a456426655440000");
        GeographicPoint location = new GeographicPoint(40.0F, -3.0F);
        qrDecoderMock.setVehicleID(new VehicleID("123e4567e89b12d3a456426655440001"));
        handler.scanQR(user, qrImage, stationID, location);
        assertNotNull(handler);
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
        qrDecoderMock.setVehicleID(new VehicleID("123e4567e89b12d3a456426655440001"));
        handler.scanQR(user, qrImage, stationID, startLocation);
        handler.stopDriving(endLocation, stationID);
        assertNotNull(handler);
    }
}

