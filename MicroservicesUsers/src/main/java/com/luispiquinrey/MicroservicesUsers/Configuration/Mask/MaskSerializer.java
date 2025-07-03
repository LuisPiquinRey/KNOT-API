package com.luispiquinrey.MicroservicesUsers.Configuration.Mask;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class MaskSerializer extends JsonSerializer<String>{

    @Override
    public void serialize(String arg0, JsonGenerator arg1, SerializerProvider arg2) throws IOException {
        String maskedValue = maskValue(arg0);
        arg1.writeString(maskedValue);
    }
    
    private String maskValue(String value) {
        return value.replaceAll(".(?=.{4})", "*");
    }
    
}
