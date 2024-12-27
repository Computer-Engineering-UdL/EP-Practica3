package services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.mock.UnbondedBTSignalMock;

import static org.junit.jupiter.api.Assertions.*;

class UnbondedBTSignalMockTest {

    private UnbondedBTSignalMock btSignalMock;

    @BeforeEach
    void setUp() {
        btSignalMock = new UnbondedBTSignalMock();
    }

    @Test
    void testBTbroadcastCalled() {
        btSignalMock.BTbroadcast();
        assertTrue(btSignalMock.isBroadcastCalled(), "The BTbroadcast method should set broadcastCalled to true");
    }

    @Test
    void testResetBroadcastCalled() {
        btSignalMock.BTbroadcast();
        btSignalMock.resetBroadcastCalled();
        assertFalse(btSignalMock.isBroadcastCalled(), "The broadcastCalled flag should be reset to false");
    }
}
