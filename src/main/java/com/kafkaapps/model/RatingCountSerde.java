package com.kafkaapps.model;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

public class RatingCountSerde implements Serde<RatingCount>{

	private JsonSerializer<RatingCount> serializer ;
    private JsonDeserializer<RatingCount> jsonDeSerializer ;
    
   public  RatingCountSerde() {
    	this.serializer= new JsonSerializer<>();
    	this.jsonDeSerializer =new JsonDeserializer<>(RatingCount.class);
    }
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {}

    @Override
    public void close() {}

    @Override
    public Serializer<RatingCount> serializer() {
       return serializer;
    }

    @Override
    public Deserializer<RatingCount> deserializer() {
        return jsonDeSerializer;
    }
}
