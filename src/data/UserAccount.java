package data;

public final class UserAccount {
    private final String id;
    private static final String USER_ACCOUNT_PATTERN = "^[a-fA-F0-9]{32}$";

    public UserAccount(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("UserAccount cannot be null or empty");
        }
        if (!id.matches(USER_ACCOUNT_PATTERN)) {
            throw new IllegalArgumentException("UserAccount must be a valid UUID (32 hexadecimal characters)");
        }
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserAccount userAccount = (UserAccount) o;
        return id.equals(userAccount.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "UserAccount: {id = " + id + "}";
    }
}
