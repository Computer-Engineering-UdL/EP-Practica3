package services.mock;

import data.GeographicPoint;
import data.StationID;
import data.UserAccount;
import data.VehicleID;
import micromobility.JourneyService;
import micromobility.exceptions.InvalidPairingArgsException;
import micromobility.exceptions.PMVNotAvailException;
import micromobility.exceptions.PairingNotFoundException;
import services.Server;

import java.math.BigDecimal;
import java.net.ConnectException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ServerMock implements Server {
    private boolean throwConnectionException;
    private boolean throwPMVNotAvailableException;
    private boolean throwInvalidPairingArgsException;
    private boolean throwPairingNotFoundException;

    private final Map<VehicleID, Boolean> vehicleAvailability = new HashMap<>();
    private final Map<UserAccount, VehicleID> activePairings = new HashMap<>();

    public void setThrowConnectionException(boolean value) {
        this.throwConnectionException = value;
    }

    public void setThrowPMVNotAvailableException(boolean value) {
        this.throwPMVNotAvailableException = value;
    }

    public void setThrowInvalidPairingArgsException(boolean value) {
        this.throwInvalidPairingArgsException = value;
    }

    public void setThrowPairingNotFoundException(boolean value) {
        this.throwPairingNotFoundException = value;
    }

    @Override
    public void checkPMVAvail(VehicleID vhID) throws PMVNotAvailException, ConnectException {
        if (throwConnectionException) {
            throw new ConnectException("Simulated connection error in checkPMVAvail");
        }
        if (throwPMVNotAvailableException || !vehicleAvailability.getOrDefault(vhID, true)) {
            throw new PMVNotAvailException("Vehicle is not available");
        }
    }

    @Override
    public void registerPairing(UserAccount user, VehicleID veh, StationID st, GeographicPoint loc, LocalDateTime date)
            throws InvalidPairingArgsException, ConnectException {
        if (throwConnectionException) {
            throw new ConnectException("Simulated connection error in registerPairing");
        }
        if (throwInvalidPairingArgsException || activePairings.containsKey(user)) {
            throw new InvalidPairingArgsException("Pairing already exists or invalid arguments");
        }
        activePairings.put(user, veh);
    }

    @Override
    public void stopPairing(UserAccount user, VehicleID veh, StationID st, GeographicPoint loc, LocalDateTime date,
                            float avSp, float dist, int dur, BigDecimal imp)
            throws InvalidPairingArgsException, ConnectException {
        if (throwConnectionException) {
            throw new ConnectException("Simulated connection error in stopPairing");
        }
        if (throwInvalidPairingArgsException || !activePairings.containsKey(user)) {
            throw new InvalidPairingArgsException("No active pairing for this user");
        }
        activePairings.remove(user);
    }

    @Override
    public void setPairing(UserAccount user, VehicleID veh, StationID st, GeographicPoint loc, LocalDateTime date) {
        activePairings.put(user, veh);
    }

    @Override
    public void unPairRegisterService(JourneyService s) throws PairingNotFoundException {
        if (throwPairingNotFoundException) {
            throw new PairingNotFoundException("Pairing not found");
        }
    }

    @Override
    public void registerLocation(VehicleID veh, StationID st) {
        vehicleAvailability.put(veh, true);
    }
}
