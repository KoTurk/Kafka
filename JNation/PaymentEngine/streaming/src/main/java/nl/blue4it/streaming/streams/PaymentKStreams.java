package nl.blue4it.streaming.streams;

import example.avro.Fraud;
import example.avro.Payment;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;

public class PaymentKStreams {

    public static KStream<String, Payment> getPaymentKStream(StreamsBuilder builder) {
        // 4.1 Create stream payments
        KStream<String, Payment> paymentKStream = builder.stream("payments");
        // 4.8 create table balances

        // 4.2 peek
        paymentKStream.peek((key,payment) -> {
            System.out.println("Checking balance");

            // 4.2.2 create metrics!!
            Counter.builder("a.message.count")
                .tag("bankcode", payment.getIban().substring(4, 8))
                .register(Metrics.globalRegistry)
                .increment();
            //http://localhost:8081/actuator/metrics/a.message.count?tag=bankcode:RABO

        })
        // 4.9 join balance
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
        // 4.7 return stream4
        return paymentKStream;
    }

}
