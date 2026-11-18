
package waypoint.model;

public class CurrencyMismatch extends IllegalArgumentException {

    public CurrencyMismatch(String message) {
        super(message);
    }
}
