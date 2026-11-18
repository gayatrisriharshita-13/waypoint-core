
package waypoint.model;

import java.util.Objects;

public final class Spot {

    private final SpotId id;
    private VehicleId occupant;

    public Spot(SpotId id) {
        this.id = Objects.requireNonNull(id, "SpotId cannot be null");
        this.occupant = null;
    }

    public synchronized boolean tryAssign(VehicleId vehicle) {
        Objects.requireNonNull(vehicle, "VehicleId cannot be null");

        if (occupant != null) {
            return false;
        }

        occupant = vehicle;
        return true;
    }

    public synchronized void release(VehicleId vehicle) {
        Objects.requireNonNull(vehicle, "VehicleId cannot be null");

        if (Objects.equals(occupant, vehicle)) {
            occupant = null;
        }
    }

    public synchronized boolean isFree() {
        return occupant == null;
    }

    public SpotId id() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Spot other)) return false;

        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
