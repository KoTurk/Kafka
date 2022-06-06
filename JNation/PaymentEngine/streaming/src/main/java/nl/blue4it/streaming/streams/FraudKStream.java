package nl.blue4it.streaming.streams;

import example.avro.Fraud;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Branched;
import org.apache.kafka.streams.kstream.KStream;

public class FraudKStream {

    public static KStream<String, Fraud> getFraudKStream(StreamsBuilder builder) {
        // 5.1 Create stream fraud
        KStream<String, Fraud> fraudKStream = builder.stream("fraud");

        // 5.2 peek
        fraudKStream.peek((key,payment) -> {
            System.out.println("Checking Fraud");
        })
        // 5.3 fILter iban NL63ABNA332454654
        .filter((username, user) -> "NL63ABNA332454654".equals(user.getIban()))
        // .filter((username, user) -> filterOrThrow(user))
        // 5.4 map values
        .mapValues((iban, user) -> new Fraud(user.getIban()))
        // 5.5 peek
        .peek((key,payment) -> {
            System.out.println("Got a Fraud, don't process");
        })
        // 5.6 split
        .split()
        // 5.7 branch
        .branch((key, value) -> reward(value), Branched.withConsumer(ks -> ks.to("rewards")))
        // 5.8 defaullbranch
        .defaultBranch(Branched.withConsumer(ks -> ks.to("blacklist")));
        // 5.9 return stream
        return fraudKStream;
    }

    private static boolean filterOrThrow(Fraud user) {
        if ("NL63DEUT332454654".equals(user.getIban())) {
            throw new NullPointerException("story of my life");
        } else {
            return true;
        }
    }

    private static boolean reward(Fraud value) {
        return false;
    }
}
