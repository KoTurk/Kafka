package nl.blue4it.streaming;

import example.avro.Fraud;
import example.avro.Payment;
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

        paymentKStream
            .peek((key,payment) -> {
                System.out.println("Checking balance");
            })
            .filter((key,payment) -> payment.getBalance() >= payment.getAmount())
            .mapValues((key, payment) -> new example.avro.Fraud(payment.getIban()))
            .peek((key,payment) -> {
                System.out.println("Balance OK, next step is the fraud check");
            })
            .to("fraud");
        return paymentKStream;
    }

    @Bean
    public  KStream<String, example.avro.Fraud> handleFraudStream(StreamsBuilder builder) {
        // 5.1 Create stream fraud
        KStream<String, example.avro.Fraud> fraudKStream = builder.stream("fraud");

        fraudKStream
            .peek((key,payment) -> {
                System.out.println("Checking Fraud");
            })
            .filter((username, user) -> "NL63ABNA332454654".equals(user.getIban()))
            .mapValues((iban, user) -> new Fraud(user.getIban()))
            .peek((key,payment) -> {
                System.out.println("Got a Fraud, don't process");
            })
            .split()
            .branch((key, value) -> analytics(value), Branched.withConsumer(ks -> ks.to("analytics")))
            .defaultBranch(Branched.withConsumer(ks -> ks.to("blacklist")));

        return fraudKStream;
    }

    private boolean analytics(Fraud value) {
        return false;
    }
}
