package micromobility;

import data.UserAccount;
import data.VehicleID;

public interface JourneyServiceFactory {
    JourneyService createJourneyService(UserAccount user, VehicleID vehicle);
}
