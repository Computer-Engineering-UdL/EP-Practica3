package services.smartfeatures;

import micromobility.exceptions.PMVPhisicalException;
import micromobility.exceptions.ProceduralException;

import java.net.ConnectException;


public interface ArduinoMicroController {
    // Software for microcontrollers
    void setBTconnection() throws ConnectException;

    void startDriving() throws PMVPhisicalException, ConnectException, ProceduralException;

    void stopDriving() throws PMVPhisicalException, ConnectException, ProceduralException;

    void undoBTconnection();
}
