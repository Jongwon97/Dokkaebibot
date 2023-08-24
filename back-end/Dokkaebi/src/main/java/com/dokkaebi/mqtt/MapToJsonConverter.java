//package com.dokkaebi.mqtt;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.persistence.AttributeConverter;
//import jakarta.persistence.Converter;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Hashtable;
//import java.util.Map;
//import org.hibernate.mapping.Any;
//
//@Converter
//public class MapToJsonConverter implements AttributeConverter<Map<String, Any>, String> {
//
//  @Override
//  public String convertToDatabaseColumn(Map<String, Any> attribute) {
//    try {
//      ObjectMapper objectMapper = new ObjectMapper();
//      return objectMapper.writeValueAsString(attribute);
//    } catch (JsonProcessingException e) {
//      System.out.println(e);
////      LOGGER.error("Could not convert map to json string.");
//      return null;
//    }
//  }
//
//  @Override
//  public Map<String, Any> convertToEntityAttribute(String dbData) {
//    if (dbData == null) {
//      return new HashMap<>();
//    }
//    try {
//      ObjectMapper objectMapper = new ObjectMapper();
//      return objectMapper.readValue(dbData, Hashtable.class);
//    } catch (IOException e) {
//      System.out.println(e);
//    }
//    return new HashMap<>();
//  }
//}
