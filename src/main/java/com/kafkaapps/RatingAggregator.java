package com.kafkaapps;

import java.util.Properties;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.scala.kstream.Consumed;
import org.apache.kafka.streams.state.KeyValueStore;


import com.kafkaapps.model.RatingCount;
import com.kafkaapps.model.Review;
import com.kafkaapps.model.SerdesFactory;

public class RatingAggregator {
    public static void main(String[] args) {
 
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "rating-aggregator-thursday");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG,SerdesFactory.review().getClass());

        StreamsBuilder builder = new StreamsBuilder();

        KStream<String, Review> source = builder.stream("productReviews",Consumed.with(Serdes.String(), SerdesFactory.review()));

        KTable<String, String> aggregatedRatings = source
                .groupByKey()
                .aggregate(
                    () -> new RatingCount(0, 0),
                    (key, review, newRating) -> {
                    	System.out.println(review);
                        newRating.count += 1;
                        newRating.total += review.rating;
                        return newRating;
                    },
                    Materialized.with(Serdes.String(), SerdesFactory.rating())
                )
                .mapValues((key, ratingStats) -> ""+ ratingStats.total / ratingStats.count);

            aggregatedRatings.toStream().to("aggregated-product-ratings", Produced.with(Serdes.String(), Serdes.String()));

            /*.aggregate(
                () -> new RatingCount(0, 0),
                (key,reviews, ratingStats) -> {
                	System.out.println(reviews);
                	System.out.println(ratingStats);
                    ratingStats.count += 1;
                    ratingStats.total += reviews.rating;
                    return ratingStats;
                },
                Materialized.with(Serdes.String(), SerdesFactory.rating())
            )
            .mapValues((key, ratingStats) -> 
            {
                System.out.println(ratingStats);
            	return ratingStats.total / ratingStats.count;
            	
            });

        aggregatedRatings.toStream().to("aggregated-rating", Produced.with(Serdes.String(), Serdes.Double()));
*/
        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();

        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }
}