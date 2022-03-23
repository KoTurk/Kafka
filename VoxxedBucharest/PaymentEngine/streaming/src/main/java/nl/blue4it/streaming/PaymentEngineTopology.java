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
        KStream<String, Payment> paymentKStream;

        // 4.2 peek
        // 4.3 filter balance and amount
        // 4.4 mapValues
        // 4.5 peek balance ok
        // 4.6 to topic fraud
        // 4.7 return payment stream
        return null;
    }

    @Bean
    public  KStream<String, example.avro.Fraud> handleFraudStream(StreamsBuilder builder) {
        // 5.1 Create stream fraud
        KStream<String, example.avro.Fraud> fraudKStream;

        // 5.2 peek
        // 5.3 fILter iban NL63ABNA332454654
        // 5.4 map values
        // 5.5 peek
        // 5.6 split
        // 5.7 branch
        // 5.8 defaullbranch
        
        return null;
    }

    private boolean reward(Fraud value) {
        return false;
    }
}
