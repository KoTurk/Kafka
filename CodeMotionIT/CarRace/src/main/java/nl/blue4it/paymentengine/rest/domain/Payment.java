package nl.blue4it.paymentengine.rest.domain;

public class Payment {
    public String toIBAN = "NL63ABNA332454654";
    public String fromIBAN = "NL61RABO0332454657";
    private Double amount = 10.0;

    public String getToIBAN() {
        return toIBAN;
    }

    public void setToIBAN(String toIBAN) {
        this.toIBAN = toIBAN;
    }

    public String getFromIBAN() {
        return fromIBAN;
    }

    public void setFromIBAN(String fromIBAN) {
        this.fromIBAN = fromIBAN;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
