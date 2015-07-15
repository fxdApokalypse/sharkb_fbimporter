package net.sharkfw.apps.fb.util.facebook.json.mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import org.springframework.social.facebook.api.AgeRange;

import java.io.IOException;


@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class AgeRangeMixin extends FacebookObjectMixin {

    @JsonProperty("max")
    Integer max;

    @JsonProperty("min")
    Integer min;


    public static class AgeRangeDeserializer extends JsonDeserializer<AgeRange> {
        @Override
        public AgeRange deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            JsonNode ageRangeNode = jp.readValueAs(JsonNode.class);
            JsonNode minNode = (JsonNode) ageRangeNode.get("min");
            JsonNode maxNode = (JsonNode) ageRangeNode.get("max");
            Integer min = minNode != null ? minNode.asInt() : null;
            Integer max = maxNode != null ? maxNode.asInt() : null;
            return AgeRange.fromMinMax(min, max);
        }
    }

    public static class AgeRangeSerializer extends JsonSerializer<AgeRange> {

        @Override
        public void serialize(AgeRange value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
            if (value != null) {
                jgen.writeStartObject();

                if (value.getMax() != null) {
                    jgen.writeNumberField("max", value.getMax());
                }
                if (value.getMin() != null) {
                    jgen.writeNumberField("min", value.getMin());
                }
                jgen.writeEndObject();
            }
        }

    }
}