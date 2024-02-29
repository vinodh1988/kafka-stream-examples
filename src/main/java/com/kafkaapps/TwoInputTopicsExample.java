package com.kafkaapps;

import java.time.Duration;
import java.util.Properties;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.Joined;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.StreamJoined;

public class TwoInputTopicsExample {

    public static void main(String[] args) {
        // Configure properties for Kafka Streams application
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "two-input-topics-example");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());


        // Create a StreamsBuilder
        StreamsBuilder builder = new StreamsBuilder();

        // Consume from 'input_topic_1'
        KStream<String, String> inputTopic1Stream = builder.stream("inputleft");

        // Consume from 'input_topic_2'
        KStream<String, String> inputTopic2Stream = builder.stream("inputright");

        // Process input_topic_1 stream
        KStream<String, String> processedStream1 = inputTopic1Stream
                .mapValues(value -> value.toUpperCase());
           

        // Process input_topic_2 stream
        KStream<String, String> processedStream2 = inputTopic2Stream
                .mapValues(value -> value.toLowerCase());
               

        // Join the processed streams based on a common key
        KStream<String, String> joinedStream = processedStream1.join(
                processedStream2,
                (value1, value2) -> value1+" <--> "+value2,
                JoinWindows.ofTimeDifferenceWithNoGrace(Duration.ofMinutes(5))
                // Example: 5-minute window for joining events
               
        );        
        // Write the joined stream to 'output_topic'
        joinedStream.to("outputleftright", Produced.with(Serdes.String(), Serdes.String()));

        // Build Kafka Streams topology
        KafkaStreams streams = new KafkaStreams(builder.build(), props);

        // Start the Kafka Streams application
        streams.start();

        // Add shutdown hook to gracefully close Kafka Streams on shutdown
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }
}
