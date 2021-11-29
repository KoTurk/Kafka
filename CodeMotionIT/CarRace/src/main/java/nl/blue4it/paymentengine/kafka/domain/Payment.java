package nl.blue4it.paymentengine.kafka.domain;

public class Payment {
    public String getCode;
    private Double amount;

    public Double getAmount() {
        this.amount = 11.0;
        return amount;
    }

    public String getGetCode() {
        return getCode;
    }
}
