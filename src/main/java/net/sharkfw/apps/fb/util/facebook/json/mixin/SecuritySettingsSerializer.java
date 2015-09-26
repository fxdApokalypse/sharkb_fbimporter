package net.sharkfw.apps.fb.util.facebook.json.mixin;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.social.facebook.api.SecuritySettings;

import java.io.IOException;

public class SecuritySettingsSerializer extends JsonSerializer<SecuritySettings> {
    @Override
    public void serialize(SecuritySettings value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        jgen.writeObjectFieldStart("security_settings");
        if (value != null) {
            SecuritySettings.SecureBrowsing sb = value.getSecureBrowsing();
            if (sb != null) {
                jgen.writeObjectFieldStart("secure_browsing");
                jgen.writeBooleanField("enabled", sb.isEnabled());
                jgen.writeEndObject();
            }
        }
        jgen.writeEndObject();
    }
}
