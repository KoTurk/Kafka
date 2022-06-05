package nl.blue4it.streaming;

import example.avro.Fraud;
import example.avro.Payment;
import nl.blue4it.streaming.streams.FraudKStream;
import nl.blue4it.streaming.streams.PaymentKStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
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
        // 4 Create Payment Stream
        return PaymentKStreams.getPaymentKStream(builder);
    }

    @Bean
    public  KStream<String, Fraud> handleFraudStream(StreamsBuilder builder) {
        // 5 Create Fraud Stream
        return FraudKStream.getFraudKStream(builder);
    }

    @Bean
    public Topology buildTopology(StreamsBuilder builder) {

        // 6 Build Topology and visualise6.
        // at https://zz85.github.io/kafka-streams-viz/
        Topology topology = builder.build();
        System.out.println("Topology description" + topology.describe());

        return builder.build();
    }
}
