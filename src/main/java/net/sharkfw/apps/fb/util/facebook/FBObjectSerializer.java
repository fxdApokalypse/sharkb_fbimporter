package net.sharkfw.apps.fb.util.facebook;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import net.sharkfw.apps.fb.util.facebook.json.mixin.AgeRangeMixin;
import net.sharkfw.apps.fb.util.facebook.json.mixin.SecuritySettingsSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.facebook.api.AgeRange;
import org.springframework.social.facebook.api.FacebookObject;
import org.springframework.social.facebook.api.SecuritySettings;
import org.springframework.social.facebook.api.impl.json.FacebookModule;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class FBObjectSerializer {

    private Logger LOG = LoggerFactory.getLogger(FBObjectSerializer.class);
    private ObjectMapper jsonObjectMapper;

    public FBObjectSerializer() {
        jsonObjectMapper = new ObjectMapper();
        FacebookModule module = new FacebookModule() {
            @Override
            public void setupModule(SetupContext context) {
                super.setupModule(context);
                addSerializer(AgeRange.class, new AgeRangeMixin.AgeRangeSerializer());
                addSerializer(SecuritySettings.class, new SecuritySettingsSerializer());
                context.addSerializers(_serializers);
            }
        };
        jsonObjectMapper.registerModule(module);
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        simpleDateFormat.setTimeZone(timeZone);
        jsonObjectMapper.setDateFormat(simpleDateFormat);
    }

    public void serialize(FacebookObject object, OutputStream out) throws FBSerializeException {
        if (!jsonObjectMapper.canSerialize(object.getClass())) {
            throw new FBSerializeException("The specified object from type " + object.getClass().getName() + "isn't serialize able.");
        }
        try {
            jsonObjectMapper.writeValue(out, object);
        } catch (IOException ex) {
            LOG.error("The write of the facebook type " + object.getClass().getName() + "wasn't possible.", ex);
            throw new FBSerializeException(ex);
        }
    }

    public byte[] serializeAsByte(FacebookObject object) throws FBSerializeException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        serialize(object, out);
        return out.toByteArray();
    }

    public String serializeAsString(FacebookObject object) throws  FBSerializeException {
        if (!jsonObjectMapper.canSerialize(object.getClass())) {
            throw new FBSerializeException("The specified object from type " + object.getClass().getName() + "isn't serialize able.");
        }
        try {
            return jsonObjectMapper.writeValueAsString(object);
        } catch (IOException ex) {
            LOG.error("The write of the facebook type " + object.getClass().getName() + "wasn't possible.", ex);
            throw new FBSerializeException(ex);
        }
    }
}
