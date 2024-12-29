package micromobility;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PMVStateTest {

    @Test
    void testValidStateTransitions() {
        assertDoesNotThrow(() -> PMVState.valueOf("Available"));
        assertDoesNotThrow(() -> PMVState.valueOf("UnderWay"));
        assertDoesNotThrow(() -> PMVState.valueOf("NotAvailable"));
        assertDoesNotThrow(() -> PMVState.valueOf("TemporaryParking"));
    }

    @Test
    void testInvalidState() {
        assertThrows(IllegalArgumentException.class, () -> PMVState.valueOf("InvalidState"));
    }

    @Test
    void testStateEquality() {
        assertEquals(PMVState.Available, PMVState.valueOf("Available"));
        assertEquals(PMVState.UnderWay, PMVState.valueOf("UnderWay"));
        assertEquals(PMVState.NotAvailable, PMVState.valueOf("NotAvailable"));
        assertEquals(PMVState.TemporaryParking, PMVState.valueOf("TemporaryParking"));
    }
}
