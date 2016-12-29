package com.example.web.api;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

public class CustomOffsetDateTimeDeserializer extends StdDeserializer<OffsetDateTime>{

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z");

    public CustomOffsetDateTimeDeserializer() {
        this(null);
    }

    public CustomOffsetDateTimeDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public OffsetDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String date = p.getText();
        return OffsetDateTime.parse(date, formatter);
    }
}
