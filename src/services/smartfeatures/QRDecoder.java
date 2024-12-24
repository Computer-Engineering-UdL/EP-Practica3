package services.smartfeatures;

import data.VehicleID;
import micromobility.exceptions.CorruptedImgException;

import java.awt.image.BufferedImage;

public interface QRDecoder {
    // Decodes QR codes from an image 
    VehicleID getVehicleID(BufferedImage QRImg) throws CorruptedImgException;
}
