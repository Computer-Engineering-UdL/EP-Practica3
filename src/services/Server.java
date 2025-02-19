package services;

import data.GeographicPoint;
import data.StationID;
import data.UserAccount;
import data.VehicleID;
import micromobility.JourneyService;
import micromobility.exceptions.InvalidPairingArgsException;
import micromobility.exceptions.PMVNotAvailException;
import micromobility.exceptions.PairingNotFoundException;

import java.math.BigDecimal;
import java.net.ConnectException;
import java.time.LocalDateTime;

/**
 * External services involved in the shared micromobility system
 */
public interface Server { // External service for the persistent storage
    // To be invoked by the use case controller 
    void checkPMVAvail(VehicleID vhID) throws PMVNotAvailException, ConnectException;

    void registerPairing(UserAccount user, VehicleID veh, StationID st, GeographicPoint loc, LocalDateTime date) throws InvalidPairingArgsException, ConnectException;

    void stopPairing(UserAccount user, VehicleID veh, StationID st, GeographicPoint loc, LocalDateTime date, float avSp, float dist, int dur, BigDecimal imp) throws InvalidPairingArgsException, ConnectException;

    // Internal operations 
    void setPairing(UserAccount user, VehicleID veh, StationID st, GeographicPoint loc, LocalDateTime date);

    void unPairRegisterService(JourneyService s) throws PairingNotFoundException;

    void registerLocation(VehicleID veh, StationID st);
}
