package data;

public final class VehicleID {
    private final String id;
    private static final String VEHICLE_ID_PATTERN = "^[a-fA-F0-9]{32}$";

    public VehicleID(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("VehicleID cannot be null or empty");
        }
        if (!id.matches(VEHICLE_ID_PATTERN)) {
            throw new IllegalArgumentException("VehicleID must be a valid UUID (32 hexadecimal characters)");
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
        VehicleID vehicleID = (VehicleID) o;
        return id.equals(vehicleID.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "VehicleID{" + "id='" + id + '\'' + '}';
    }
}
