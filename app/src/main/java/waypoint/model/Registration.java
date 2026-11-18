
package waypoint.model;

import java.util.Objects;
import java.util.Locale;

public record Registration(String value) {

    public Registration {
        value = Objects.requireNonNull(
            value, "Registration cannot be null"
        ).trim().toUpperCase(Locale.ROOT);

        if (!value.matches("[A-Z]{2}\\d{2}[A-Z]{1,2}\\d{4}")) {
            throw new IllegalArgumentException(
                "Invalid vehicle registration number: " + value
            );
        }
    }
}
