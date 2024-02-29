package com.kafkaapps.model;


import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;

public class SerdesFactory {


 


    public static Serde<Review> review() {

        return  new ReviewSerde();
    }
    public static Serde<RatingCount> rating() {

        return  new RatingCountSerde();
    }
}
