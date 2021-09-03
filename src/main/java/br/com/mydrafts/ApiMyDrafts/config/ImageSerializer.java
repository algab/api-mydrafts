package br.com.mydrafts.ApiMyDrafts.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;

public class ImageSerializer extends JsonSerializer<String> {
    @Value("${tmdb.imgs-url}")
    private String imgsUrl;

    @Override
    public void serialize(String poster, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(String.format("%s%s", this.imgsUrl, poster));
    }
}
