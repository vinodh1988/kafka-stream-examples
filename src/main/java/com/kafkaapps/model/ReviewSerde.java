package com.kafkaapps.model;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import com.fasterxml.jackson.databind.ObjectMapper;


public class ReviewSerde implements Serde<Review> {
    
    
    private JsonSerializer<Review> serializer ;
    private JsonDeserializer<Review> jsonDeSerializer ;
    
   public  ReviewSerde() {
    	this.serializer= new JsonSerializer<>();
    	this.jsonDeSerializer =new JsonDeserializer<>(Review.class);
    }
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {}

    @Override
    public void close() {}

    @Override
    public Serializer<Review> serializer() {
       return serializer;
    }

    @Override
    public Deserializer<Review> deserializer() {
        return jsonDeSerializer;
    }
}
