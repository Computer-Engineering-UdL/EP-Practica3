package services.mock;

import data.VehicleID;
import micromobility.exceptions.CorruptedImgException;
import services.smartfeatures.QRDecoder;

import java.awt.image.BufferedImage;

public class QRDecoderMock implements QRDecoder {
    private boolean throwCorruptedImgException;
    private VehicleID mockVehicleID;

    public void setThrowCorruptedImgException(boolean value) {
        this.throwCorruptedImgException = value;
    }

    public void setMockVehicleID(VehicleID vehicleID) {
        this.mockVehicleID = vehicleID;
    }

    @Override
    public VehicleID getVehicleID(BufferedImage image) throws CorruptedImgException {
        if (throwCorruptedImgException) {
            throw new CorruptedImgException("QR Code is corrupted");
        }
        return mockVehicleID;
    }

    public void setVehicleID(VehicleID vehicleID) {
        this.mockVehicleID = vehicleID;
    }
}
