package com.kafkaapps;

import java.util.Properties;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Predicate;

public class BranchingStreams {

	  public static void main(String[] args) {
	        Properties props = new Properties();
	        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "suspicious-activity-detector");
	        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
	        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, "org.apache.kafka.common.serialization.Serdes$StringSerde");
	        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, "org.apache.kafka.common.serialization.Serdes$StringSerde");

	        StreamsBuilder builder = new StreamsBuilder();

	        KStream<String, String> userActivityStream = builder.stream("user-activity");

	        // Identify suspicious activity
	        KStream<String, String> suspiciousActivityStream = userActivityStream.filter(new SuspiciousActivityPredicate());

	        suspiciousActivityStream.to("suspicious-activity");
	        
	        KStream<String, String> logActivityStream = userActivityStream.filterNot(new SuspiciousActivityPredicate());

	        logActivityStream.to("log-activity");

	        KafkaStreams streams = new KafkaStreams(builder.build(), props);
	        streams.start();

	        // Gracefully shutdown Kafka Streams on JVM shutdown
	        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
	    }

	  private static class SuspiciousActivityPredicate implements Predicate<String, String> {
	        @Override
	        public boolean test(String key, String value) {
	            // Example criteria for identifying suspicious activity
	            // For instance, consider flagging users who have failed login attempts more than 3 times within 5 minutes
	            // You can implement more sophisticated logic based on your use case
	            String[] terms= {"failed", "stopped", "wrong","abrupt","invalid","timeout","suspicious",
	            		"blocked"};
	            for(String x:terms)
	        	      if(value.contains(x))
	        	    	  return true;
	            
	            return false;
	        }
	    }

}
