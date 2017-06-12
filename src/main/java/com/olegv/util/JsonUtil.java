package com.olegv.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;

public class JsonUtil {
    
    public static String dataToJson(Object data) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            // mapper.enable(SerializationFeature.INDENT_OUTPUT);
            StringWriter sw = new StringWriter();
            mapper.writeValue(sw, data);
            return sw.toString();
        } catch (IOException e) {
            throw new RuntimeException("IOException while mapping object (" + data + ") to JSON");
        }
    }
   
    
}
