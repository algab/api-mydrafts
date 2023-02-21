package br.com.mydrafts.apimydrafts.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;

public class ImageSerializer extends JsonSerializer<String> {

    @Value("${tmdb.image}")
    private String url;

    @Override
    public void serialize(String poster, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(String.format("%s%s", this.url, poster));
    }

}
