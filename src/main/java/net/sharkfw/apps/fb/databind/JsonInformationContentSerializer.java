package net.sharkfw.apps.fb.databind;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.sharkfw.knowledgeBase.Information;
import net.sharkfw.knowledgeBase.SharkKBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;
import org.springframework.util.StopWatch;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Component
public class JsonInformationContentSerializer implements InformationContentSerializer {

	private Logger LOG = LoggerFactory.getLogger(JsonInformationContentSerializer.class);

	@Autowired
	private ObjectMapper objectMapper;

	public JsonInformationContentSerializer() {
		this.objectMapper = new ObjectMapper();
	}

	public JsonInformationContentSerializer(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@Override
	public <T> void serialize(T object, Information information) throws IOException, SharkKBException {
		byte[] bytes = objectMapper.writeValueAsBytes(object);
		_serialize(object, information, bytes);

	}

	@Override
	public  <T> void serialize(List<T> object, Information information) throws IOException, SharkKBException {
		byte[] bytes = objectMapper.writeValueAsBytes(object);
		information.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
		information.setContent(bytes);
		information.setProperty(TYPE_PROPERTY, object.getClass().getName());
	}

	@Override
	public <T> boolean canSerialize(T object) {
		if (object == null) return false;
		return objectMapper.canSerialize(object.getClass());
	}

	private <T> void _serialize(T object, Information information, byte[] bytes) throws SharkKBException {
		information.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
		StopWatch s1 = new StopWatch();
		s1.start();
		information.setContent(bytes);
		s1.stop();
		System.out.println("Write to Information " + s1.getLastTaskTimeMillis());
		information.setProperty(TYPE_PROPERTY, object.getClass().getName());
	}
}
