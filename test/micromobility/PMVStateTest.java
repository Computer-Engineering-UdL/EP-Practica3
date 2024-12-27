package micromobility;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PMVStateTest {

    public static short NotAvailable;

    @Test
    void testValidStateTransitions() {
        assertDoesNotThrow(() -> PMVState.valueOf("Available"));
        assertDoesNotThrow(() -> PMVState.valueOf("UnderWay"));
        assertDoesNotThrow(() -> PMVState.valueOf("NotAvailable"));
    }

    @Test
    void testInvalidState() {
        assertThrows(IllegalArgumentException.class, () -> PMVState.valueOf("InvalidState"));
    }

    @Test
    void testStateEquality() {
        assertEquals(PMVState.Available, PMVState.valueOf("Available"));
    }
}
