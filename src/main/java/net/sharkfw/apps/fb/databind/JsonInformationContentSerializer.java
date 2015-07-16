package net.sharkfw.apps.fb.databind;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.sharkfw.knowledgeBase.Information;
import net.sharkfw.knowledgeBase.SharkKBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;

import java.io.IOException;
import java.util.List;

@Component
public class JsonInformationContentSerializer<T> implements InformationContentSerializer<T> {

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
	public void serialize(T object, Information information) throws IOException, SharkKBException {
		byte[] bytes = objectMapper.writeValueAsBytes(object);
		_serialize(object, information, bytes);
	}

	@Override
	public void serialize(List<T> object, Information information) throws IOException, SharkKBException {
		byte[] bytes = objectMapper.writeValueAsBytes(object);
		information.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
		information.setContent(bytes);
		information.setProperty(TYPE_PROPERTY, object.getClass().getName());
	}

	@Override
	public boolean canSerialize(T object) {
		return objectMapper.canSerialize(object.getClass());
	}

	private void _serialize(T object, Information information, byte[] bytes) throws SharkKBException {
		information.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
		information.setContent(bytes);
		information.setProperty(TYPE_PROPERTY, object.getClass().getName());
	}
}
