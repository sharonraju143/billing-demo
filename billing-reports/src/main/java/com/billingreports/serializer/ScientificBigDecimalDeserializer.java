//package com.billingreports.serializer;
//
//import com.fasterxml.jackson.core.JsonParser;
//import com.fasterxml.jackson.databind.DeserializationContext;
//import com.fasterxml.jackson.databind.JsonDeserializer;
//
//import java.io.IOException;
//import java.math.BigDecimal;
//
//public class ScientificBigDecimalDeserializer extends JsonDeserializer<BigDecimal> {
//    @Override
//    public BigDecimal deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
//        String value = jsonParser.getText();
//        try {
//            // Attempt to parse as BigDecimal directly
//            return new BigDecimal(value);
//        } catch (NumberFormatException e) {
//            // If parsing fails, attempt to parse as double first and then convert to BigDecimal
//            Double doubleValue = Double.parseDouble(value);
//            return BigDecimal.valueOf(doubleValue);
//        }
//    }
//}
//
