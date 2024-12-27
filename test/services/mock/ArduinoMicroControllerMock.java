package services.mock;

import micromobility.exceptions.PMVPhisicalException;
import services.smartfeatures.ArduinoMicroController;

public class ArduinoMicroControllerMock implements ArduinoMicroController {
    private boolean throwPhysicalException;
    private boolean isDriving;

    public void setThrowPhysicalException(boolean value) {
        this.throwPhysicalException = value;
    }

    public boolean isDriving() {
        return isDriving;
    }

    @Override
    public void startDriving() throws PMVPhisicalException {
        if (throwPhysicalException) {
            throw new PMVPhisicalException("Simulated physical exception in startDriving");
        }
        isDriving = true;
    }

    @Override
    public void stopDriving() throws PMVPhisicalException {
        if (throwPhysicalException) {
            throw new PMVPhisicalException("Simulated physical exception in stopDriving");
        }
        isDriving = false;
    }

    @Override
    public void undoBTconnection() {
        System.out.println("Simulated Bluetooth Disconnection set.");

    }

    @Override
    public void setBTconnection() {
        System.out.println("Simulated Bluetooth connection set.");
    }
}
