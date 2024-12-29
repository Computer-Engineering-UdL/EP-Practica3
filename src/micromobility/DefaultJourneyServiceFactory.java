package micromobility;

import data.UserAccount;
import data.VehicleID;

public class DefaultJourneyServiceFactory implements JourneyServiceFactory {
    @Override
    public JourneyService createJourneyService(UserAccount user, VehicleID vehicle) {
        return new JourneyService(user, vehicle);
    }
}
