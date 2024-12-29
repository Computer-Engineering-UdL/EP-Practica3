package micromobility;

import data.GeographicPoint;
import data.StationID;
import data.UserAccount;
import data.VehicleID;
import micromobility.exceptions.CorruptedImgException;
import micromobility.exceptions.InvalidPairingArgsException;
import micromobility.exceptions.PMVNotAvailException;
import micromobility.exceptions.PMVPhisicalException;
import micromobility.exceptions.PairingNotFoundException;
import micromobility.exceptions.ProceduralException;
import services.Server;
import services.smartfeatures.ArduinoMicroController;
import services.smartfeatures.QRDecoder;
import services.smartfeatures.UnbondedBTSignal;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.net.ConnectException;
import java.time.LocalDateTime;

public class JourneyRealizeHandler {
    private final Server server;
    private final QRDecoder qrDecoder;
    private final UnbondedBTSignal btSignal;
    private final ArduinoMicroController arduinoController;
    private final JourneyServiceFactory journeyServiceFactory;
    private static final BigDecimal RATE_PER_KM = new BigDecimal("0.5");
    private static final BigDecimal RATE_PER_MINUTE = new BigDecimal("0.2");

    private JourneyService currentJourney;

    public JourneyRealizeHandler(Server server, QRDecoder qrDecoder, UnbondedBTSignal btSignal,
                                 ArduinoMicroController arduinoController, JourneyServiceFactory factory) {
        this.server = server;
        this.qrDecoder = qrDecoder;
        this.btSignal = btSignal;
        this.arduinoController = arduinoController;
        this.journeyServiceFactory = factory;
    }

    public JourneyRealizeHandler(Server server, QRDecoder qrDecoder, UnbondedBTSignal btSignal,
                                 ArduinoMicroController arduinoController) {
        this(server, qrDecoder, btSignal, arduinoController, new DefaultJourneyServiceFactory());
    }

    public void scanQR(UserAccount user, BufferedImage qrImage, StationID stationID, GeographicPoint location)
            throws ConnectException, InvalidPairingArgsException, CorruptedImgException, PMVNotAvailException, ProceduralException {
        if (currentJourney != null) {
            throw new ProceduralException("Cannot scan QR while a journey is active");
        }
        VehicleID vehicleID = qrDecoder.getVehicleID(qrImage);
        server.checkPMVAvail(vehicleID);
        server.registerPairing(user, vehicleID, stationID, location, LocalDateTime.now());
        currentJourney = journeyServiceFactory.createJourneyService(user, vehicleID); // Utilitzar la fàbrica
        currentJourney.setServiceInit(location, LocalDateTime.now());
        server.setPairing(user, vehicleID, stationID, location, LocalDateTime.now());
    }

    public void unPairVehicle(GeographicPoint endLocation, StationID stationID)
            throws ConnectException, InvalidPairingArgsException, PairingNotFoundException, ProceduralException {
        if (currentJourney == null) {
            throw new ProceduralException("No active journey to unpair");
        }
        LocalDateTime endTime = LocalDateTime.now();
        int duration = (int) java.time.Duration.between(currentJourney.getStartTime(), endTime).toMinutes();
        float distance = calculateDistance(currentJourney.getStartLocation(), endLocation);
        float averageSpeed = distance / (duration / 60f);
        currentJourney.setServiceFinish(endLocation, endTime, averageSpeed, distance, duration);
        currentJourney.calculateServiceCost(RATE_PER_KM, RATE_PER_MINUTE);
        server.stopPairing(
                currentJourney.getUserAccount(),
                currentJourney.getVehicleID(),
                stationID,
                endLocation,
                endTime,
                averageSpeed,
                distance,
                duration,
                currentJourney.getServiceCost()
        );
        server.unPairRegisterService(currentJourney);
        currentJourney = null;
    }

    public void broadcastStationID(StationID stID) throws ConnectException {
        server.registerLocation(null, stID);
        btSignal.BTbroadcast();
    }

    public void startDriving() throws ConnectException, ProceduralException, PMVPhisicalException {
        if (currentJourney == null) {
            throw new ProceduralException("No active journey to start driving");
        }
        arduinoController.startDriving();
    }

    public void stopDriving(GeographicPoint currentLocation, StationID stationID)
            throws ConnectException, ProceduralException, PMVPhisicalException, PairingNotFoundException, InvalidPairingArgsException {
        if (currentJourney == null) {
            throw new ProceduralException("No active journey to stop driving");
        }
        arduinoController.stopDriving();
        unPairVehicle(currentLocation, stationID);
    }

    private float calculateDistance(GeographicPoint start, GeographicPoint end) {
        return (float) Point2D.distance(start.getLatitude(), start.getLongitude(), end.getLatitude(), end.getLongitude());
    }

    public JourneyService getCurrentJourney() {
        return currentJourney;
    }
}
