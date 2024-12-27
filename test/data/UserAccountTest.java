package data;

import org.junit.jupiter.api.Test;
import utils.NumberUtils;

import static org.junit.jupiter.api.Assertions.*;

public class UserAccountTest {

    @Test
    void testUserAccountCreation() {
        String id = NumberUtils.generateUUID();
        UserAccount userAccount = new UserAccount(id);
        assertEquals(id, userAccount.getId());
    }

    @Test
    void testUserAccountEquality() {
        String id = NumberUtils.generateUUID();
        UserAccount userAccount1 = new UserAccount(id);
        UserAccount userAccount2 = new UserAccount(id);
        assertEquals(userAccount1, userAccount2);
        assertEquals(userAccount1.hashCode(), userAccount2.hashCode());
    }

    @Test
    void testUserAccountInequality() {
        UserAccount userAccount1 = new UserAccount(NumberUtils.generateUUID());
        UserAccount userAccount2 = new UserAccount(NumberUtils.generateUUID());
        assertNotEquals(userAccount1, userAccount2);
        assertNotEquals(userAccount1.hashCode(), userAccount2.hashCode());
    }

    @Test
    void testUserAccountToString() {
        String id = NumberUtils.generateUUID();
        UserAccount userAccount = new UserAccount(id);
        assertEquals("UserAccount: {id = " + id + "}", userAccount.toString());
    }

    @Test
    void testInvalidUserAccountNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new UserAccount(null));
        assertEquals("UserAccount cannot be null or empty", exception.getMessage());
    }

    @Test
    void testInvalidUserAccountEmpty() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new UserAccount(""));
        assertEquals("UserAccount cannot be null or empty", exception.getMessage());
    }

    @Test
    void testInvalidUserAccountIncorrectPattern() {
        String invalidId = "invalid-uuid";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new UserAccount(invalidId));
        assertEquals("UserAccount must be a valid UUID (32 hexadecimal characters)", exception.getMessage());
    }
}
