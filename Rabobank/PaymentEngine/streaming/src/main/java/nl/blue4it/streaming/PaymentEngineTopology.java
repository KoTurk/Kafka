package nl.blue4it.streaming;

import example.avro.Fraud;
import example.avro.Payment;
import io.micrometer.core.instrument.Metrics;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Branched;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafkaStreams;

@SpringBootApplication
@EnableKafkaStreams
public class PaymentEngineTopology {
    public static void main(String[] args) {
        SpringApplication.run(PaymentEngineTopology.class, args);
    }

    @Bean
    public KStream<String, Payment> handleStream(StreamsBuilder builder) {
        // 4.1 Create stream payments
        KStream<String, Payment> paymentKStream = builder.stream("payments");

        // 4.2 peek + metric
        paymentKStream.peek((key,payment) -> {
            System.out.println("Checking balance");
            Metrics.counter("a.count").increment();
        })
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

        // 4.7 return payment stream
        return paymentKStream;
    }

    @Bean
    public  KStream<String, example.avro.Fraud> handleFraudStream(StreamsBuilder builder) {
        // 5.1 Create stream fraud
        KStream<String, Fraud> fraudKStream = builder.stream("fraud");

        // 5.2 peek
        fraudKStream.peek((key,payment) -> {
            System.out.println("Checking Fraud");
        })
        // 5.3 fILter iban NL63ABNA332454654
         .filter((username, user) -> "NL63ABNA332454654".equals(user.getIban()))
        // 5.4 map values
        .mapValues((iban, user) -> new Fraud(user.getIban()))
        // 5.5 peek
        .peek((key,payment) -> {
            System.out.println("Got a Fraud, don't process");
        })
        // 5.6 split
        .split()
        // 5.7 branch
        .branch((key, value) -> analytics(value), Branched.withConsumer(ks -> ks.to("rewards")))
        // 5.8 defaullbranch
        .defaultBranch(Branched.withConsumer(ks -> ks.to("blacklist")));

        return  fraudKStream;
    }

    private boolean analytics(Fraud value) {
        return false;
    }
}
