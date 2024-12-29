package services;

import data.VehicleID;
import micromobility.exceptions.CorruptedImgException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.mock.QRDecoderMock;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

class QRDecoderTest {

    private QRDecoderMock qrDecoderMock;
    private BufferedImage dummyImage;

    @BeforeEach
    void setUp() {
        qrDecoderMock = new QRDecoderMock();
        dummyImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
    }

    @Test
    void testGetVehicleIDSuccess() throws CorruptedImgException {
        VehicleID expectedVehicleID = new VehicleID("123e4567e89b12d3a456426655440001");
        qrDecoderMock.setMockVehicleID(expectedVehicleID);

        VehicleID actualVehicleID = qrDecoderMock.getVehicleID(dummyImage);

        assertNotNull(actualVehicleID, "El VehicleID retornat no hauria de ser null");
        assertEquals(expectedVehicleID, actualVehicleID, "El VehicleID retornat hauria de coincidir amb el esperat");
    }

    @Test
    void testGetVehicleIDThrowsCorruptedImgException() {
        qrDecoderMock.setThrowCorruptedImgException(true);

        assertThrows(CorruptedImgException.class, () -> {
            qrDecoderMock.getVehicleID(dummyImage);
        }, "S'hauria de llançar una CorruptedImgException quan el QR és corromput");
    }

    @Test
    void testGetVehicleIDWithoutSettingVehicleID() throws CorruptedImgException {
        VehicleID actualVehicleID = qrDecoderMock.getVehicleID(dummyImage);
        assertNull(actualVehicleID, "Sense configurar, el VehicleID retornat hauria de ser nul");
    }
}
