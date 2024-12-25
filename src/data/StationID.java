package data;

public final class StationID {
    private final String id;
    private static final String STATION_ID_PATTERN = "^[a-fA-F0-9]{32}$";

    public StationID(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("StationID cannot be null or empty");
        }
        if (!id.matches(STATION_ID_PATTERN)) {
            throw new IllegalArgumentException("StationID must be a valid UUID (32 hexadecimal characters)");
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
        StationID stationID = (StationID) o;
        return id.equals(stationID.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "StationID{" + "id='" + id + '\'' + '}';
    }
}
