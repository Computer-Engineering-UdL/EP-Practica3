package services.mock;

import services.smartfeatures.UnbondedBTSignal;

public class UnbondedBTSignalMock implements UnbondedBTSignal {
    private boolean broadcastCalled = false;

    @Override
    public void BTbroadcast() {
        broadcastCalled = true;
    }

    public boolean isBroadcastCalled() {
        return broadcastCalled;
    }

    public void resetBroadcastCalled() {
        broadcastCalled = false;
    }
}
