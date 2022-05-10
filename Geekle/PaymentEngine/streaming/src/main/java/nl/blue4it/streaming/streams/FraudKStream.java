package nl.blue4it.streaming.streams;

import example.avro.Fraud;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Branched;
import org.apache.kafka.streams.kstream.KStream;

public class FraudKStream {

    public static KStream<String, Fraud> getFraudKStream(StreamsBuilder builder) {
        // 5.1 Create stream fraud

                // 5.2 peek

                // 5.3 fILter iban NL63ABNA332454654

                // 5.4 map values

                // 5.5 peek

                // 5.6 split

                // 5.7 branch

                // 5.8 defaullbranch

                // 5.9 return stream
        return null;
    }

    private static boolean reward(Fraud value) {
        return false;
    }
}
