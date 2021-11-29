package nl.blue4it.paymentengine.kafka.streams;

import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafkaStreams;


public class PaymentEngineTopology {
    public static void main(String[] args) {
        SpringApplication.run(PaymentEngineTopology.class, args);
    }

    @Bean
    public KStream<String, example.avro.Payment> handleStream(StreamsBuilder builder) {
        KStream<String, example.avro.Payment> paymentKStream = builder.stream("payments");

        KStream<String, example.avro.Fraud> fraudKStream = paymentKStream
                .filter((username, user) -> "NL63ABNA332454654".equals(user.getIban()))
                .mapValues((iban, user) -> new example.avro.Fraud(user.getIban()));
        fraudKStream.to("fraud-topic");

        return paymentKStream;
    }
}
