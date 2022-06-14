package nl.blue4it.streaming;


import example.avro.Account;
import example.avro.Fraud;
import example.avro.Payment;
import io.confluent.kafka.schemaregistry.testutil.MockSchemaRegistry;
import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.TestInputTopic;
import org.apache.kafka.streams.TestOutputTopic;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.TopologyTestDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PaymentEngineTopologyTest {
    private static final String SCHEMA_REGISTRY_SCOPE = PaymentEngineTopologyTest.class.getName();
    private static final String MOCK_SCHEMA_REGISTRY_URL = "mock://" + SCHEMA_REGISTRY_SCOPE;

    private TopologyTestDriver testDriver;

    private TestInputTopic<Account, Payment> paymentTopic;
    private TestOutputTopic<Account, Fraud> fraudTopic;

    @BeforeEach
    void beforeEach() throws Exception {
        // Create topology to handle stream of users
        StreamsBuilder builder = new StreamsBuilder();
        new PaymentEngineTopology().handleStream(builder);
        Topology topology = builder.build();

        // Dummy properties needed for test diver
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "test");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "dummy:1234");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, SpecificAvroSerde.class);
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, SpecificAvroSerde.class);
        props.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, MOCK_SCHEMA_REGISTRY_URL);

        // Create test driver
        testDriver = new TopologyTestDriver(topology, props);

        // Create Serdes used for test record keys and values
        Serde<Account> accountSerde = new SpecificAvroSerde<>();
        Serde<Payment> avroPaymentSerde = new SpecificAvroSerde<>();
        Serde<Fraud> avroFraudSerde = new SpecificAvroSerde<>();

        // Configure Serdes to use the same mock schema registry URL
        Map<String, String> config = Map.of(
                AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, MOCK_SCHEMA_REGISTRY_URL);
        avroPaymentSerde.configure(config, false);
        avroFraudSerde.configure(config, false);

        // Define input and output topics to use in tests
        paymentTopic = testDriver.createInputTopic(
                "payments",
                accountSerde.serializer(),
                avroPaymentSerde.serializer());
        fraudTopic = testDriver.createOutputTopic(
                "fraud",
                accountSerde.deserializer(),
                avroFraudSerde.deserializer());
    }

    @AfterEach
    void afterEach() {
        testDriver.close();
        MockSchemaRegistry.dropScope(SCHEMA_REGISTRY_SCOPE);
    }

    @Test
    void shouldPropagateUserWithFavoriteColorRed() throws Exception {
        Payment payment = new Payment("SomeEvilPerson", "NL63ABNA332454654", "NL63RABO332454652", 100F, 500F, true);
        paymentTopic.pipeInput(new Account("SomeEvilPerson", "NL63ABNA332454654"), payment);
        assertEquals(new Fraud("NL63ABNA332454654"), fraudTopic.readValue());
    }
}