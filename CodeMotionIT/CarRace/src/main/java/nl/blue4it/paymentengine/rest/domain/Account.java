package nl.blue4it.paymentengine.rest.domain;

public class Account {
    public String iban = "NL61RABO0332454657";;
    public String name = "Turk";

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
