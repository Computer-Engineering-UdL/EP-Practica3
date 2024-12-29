package micromobility.mock;

import micromobility.JourneyService;
import micromobility.JourneyServiceFactory;
import data.UserAccount;
import data.VehicleID;

public class JourneyServiceFactoryMock implements JourneyServiceFactory {
    private JourneyServiceMock journeyServiceMock;
    private boolean createJourneyServiceCalled = false;

    public void setJourneyServiceMock(JourneyServiceMock mock) {
        this.journeyServiceMock = mock;
    }

    @Override
    public JourneyService createJourneyService(UserAccount user, VehicleID vehicle) {
        createJourneyServiceCalled = true;
        return journeyServiceMock;
    }

    public boolean isCreateJourneyServiceCalled() {
        return createJourneyServiceCalled;
    }
}
