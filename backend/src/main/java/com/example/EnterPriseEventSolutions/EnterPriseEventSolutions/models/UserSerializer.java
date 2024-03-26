package com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.models;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class UserSerializer extends JsonSerializer<User> {
    @Override
    public void serialize(User user, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject(); // Inicia el objeto JSON
        jsonGenerator.writeNumberField("id", user.getId()); // Agrega el campo "id" al JSON con el ID del usuario
        jsonGenerator.writeStringField("name", user.getUsername()); // Agrega el campo "name" al JSON con el nombre del usuario
        jsonGenerator.writeEndObject();
    }
}