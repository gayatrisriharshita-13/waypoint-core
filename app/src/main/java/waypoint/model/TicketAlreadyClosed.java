
package waypoint.model;

public class TicketAlreadyClosed extends IllegalStateException {

    public TicketAlreadyClosed(String message) {
        super(message);
    }
}
