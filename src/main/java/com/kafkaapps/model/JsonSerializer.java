package com.kafkaapps.model;

import java.nio.charset.StandardCharsets;

import org.apache.kafka.common.serialization.Serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;



public class JsonSerializer<T> implements Serializer<T> {

  private final ObjectMapper objectMapper = new ObjectMapper();
         
  @Override
  public byte[] serialize(String topic, T type) {
    try {
      return objectMapper.writeValueAsString(type).getBytes(StandardCharsets.UTF_8);
    } catch (JsonProcessingException e) {
    	e.printStackTrace();
      throw new RuntimeException(e);
    }catch (Exception e){
   e.printStackTrace();
      throw e;
    }
  }

  @Override
  public void close() {}
}
