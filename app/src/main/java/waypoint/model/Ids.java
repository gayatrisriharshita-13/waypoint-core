
package waypoint.model;

import java.util.Objects;

record TicketId(String value) {
    TicketId {
        value = validate(value);
    }

    private static String validate(String value) {
        Objects.requireNonNull(value, "TicketId cannot be null");

        if (value.isBlank()) {
            throw new IllegalArgumentException("TicketId cannot be blank");
        }

        return value.trim();
    }
}

record SpotId(String value) {
    SpotId {
        value = validate(value);
    }

    private static String validate(String value) {
        Objects.requireNonNull(value, "SpotId cannot be null");

        if (value.isBlank()) {
            throw new IllegalArgumentException("SpotId cannot be blank");
        }

        return value.trim();
    }
}

record VehicleId(String value) {
    VehicleId {
        Objects.requireNonNull(value, "VehicleId cannot be null");

        if (value.isBlank()) {
            throw new IllegalArgumentException("VehicleId cannot be blank");
        }

        value = value.trim();
    }
}
