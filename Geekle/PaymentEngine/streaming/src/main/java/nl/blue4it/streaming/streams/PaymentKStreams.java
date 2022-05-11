package nl.blue4it.streaming.streams;

import example.avro.Fraud;
import example.avro.Payment;
import io.micrometer.core.instrument.Metrics;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.GlobalKTable;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;

public class PaymentKStreams {

    public static KStream<String, Payment> getPaymentKStream(StreamsBuilder builder) {
        // 4.1 Create stream payments
        KStream<String, Payment> paymentKStream = builder.stream("payments");

        // 4.8 create table balances
        KTable<String, Payment> balances = builder.table("balance");

            // 4.2 peek
            paymentKStream.peek((key,payment) -> {
                System.out.println("Checking balance");
                Metrics.counter("a.count").increment();
            })
            // 4.9 join balance
             .join(balances,
                 (leftvalue, rightValue) -> rightValue)
            // 4.3 filter balance and amount
             .filter((key, payment) -> payment.getBalance() >= payment.getAmount())
            // 4.4 mapValues
             .mapValues((key, payment) -> new Fraud(payment.getIban()))
            // 4.5 peek balance ok
            .peek((key,payment) -> {
                System.out.println("Balance OK, next step is the fraud check");
            })
            // 4.6 to topic fraud
            .to("fraud");

        // 4.9 return stream4
         return paymentKStream;
    }

}
