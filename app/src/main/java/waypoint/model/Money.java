
package waypoint.model;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.Objects;

public final class Money implements Comparable<Money> {

    public static final Currency INR = Currency.getInstance("INR");
    public static final Currency USD = Currency.getInstance("USD");

    private final long paise;
    private final Currency currency;

    private Money(long paise, Currency currency) {
        this.currency = Objects.requireNonNull(currency, "Currency cannot be null");

        if (currency.getDefaultFractionDigits() != 2) {
            throw new IllegalArgumentException(
                "Only currencies with two decimal places are supported"
            );
        }

        this.paise = paise;
    }

    public static Money ofMinor(long paise, Currency currency) {
        return new Money(paise, currency);
    }

    public static Money rupees(long rupees) {
        return new Money(Math.multiplyExact(rupees, 100), INR);
    }

    public long getPaise() {
        return paise;
    }

    public Currency getCurrency() {
        return currency;
    }

    private void requireSameCurrency(Money other) {
        Objects.requireNonNull(other, "Money cannot be null");

        if (!currency.equals(other.currency)) {
            throw new CurrencyMismatch("Currencies do not match");
        }
    }

    public Money plus(Money other) {
        requireSameCurrency(other);
        return new Money(Math.addExact(paise, other.paise), currency);
    }

    public Money minus(Money other) {
        requireSameCurrency(other);
        return new Money(Math.subtractExact(paise, other.paise), currency);
    }

    public Money times(int multiplier) {
        return new Money(Math.multiplyExact(paise, multiplier), currency);
    }

    public boolean isNegative() {
        return paise < 0;
    }

    @Override
    public int compareTo(Money other) {
        requireSameCurrency(other);
        return Long.compare(paise, other.paise);
    }

    public List<Money> allocate(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException(
                "Number of shares must be positive"
            );
        }

        long base = paise / n;
        long remainder = paise % n;

        List<Money> shares = new java.util.ArrayList<>();

        for (int i = 0; i < n; i++) {
            long share = base;

            if (i < Math.abs(remainder)) {
                share += Long.signum(remainder);
            }

            shares.add(new Money(share, currency));
        }

        return List.copyOf(shares);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Money other)) return false;

        return paise == other.paise &&
               currency.equals(other.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paise, currency);
    }

    @Override
    public String toString() {
        return currency.getCurrencyCode() + " " +
               BigDecimal.valueOf(paise, 2).toPlainString();
    }
}
