
package waypoint.model;

import java.time.Instant;
import java.util.Objects;

public final class Ticket {

    private final TicketId id;
    private final SpotId spotId;
    private final Instant entry;

    private Instant exit;
    private Money fare;

    private Ticket(TicketId id, SpotId spotId, Instant entry) {
        this.id = Objects.requireNonNull(id, "TicketId cannot be null");
        this.spotId = Objects.requireNonNull(spotId, "SpotId cannot be null");
        this.entry = Objects.requireNonNull(entry, "Entry cannot be null");
        this.exit = null;
        this.fare = null;
    }

    public static Ticket open(
        TicketId id,
        SpotId spotId,
        Instant entry
    ) {
        return new Ticket(id, spotId, entry);
    }

    public synchronized boolean isOpen() {
        return exit == null;
    }

    public synchronized Money close(Instant at, FarePolicy policy) {

        Objects.requireNonNull(at, "Exit time cannot be null");
        Objects.requireNonNull(policy, "FarePolicy cannot be null");

        if (!isOpen()) {
            throw new TicketAlreadyClosed("Ticket is already closed");
        }

        if (at.isBefore(entry)) {
            throw new IllegalArgumentException(
                "Exit cannot be before entry"
            );
        }

        Money calculatedFare = Objects.requireNonNull(
            policy.fareFor(entry, at, spotId),
            "Fare cannot be null"
        );

        if (calculatedFare.isNegative()) {
            throw new IllegalArgumentException(
                "Fare cannot be negative"
            );
        }

        // Update both fields only after all validations succeed.
        this.exit = at;
        this.fare = calculatedFare;

        return calculatedFare;
    }

    public TicketId id() {
        return id;
    }

    public SpotId spotId() {
        return spotId;
    }

    public Instant entry() {
        return entry;
    }

    public synchronized Instant exit() {
        return exit;
    }

    public synchronized Money fare() {
        return fare;
    }
}
