package net.sharkfw.apps.fb.databind;

import net.sharkfw.knowledgeBase.ContextPoint;
import net.sharkfw.knowledgeBase.Information;
import net.sharkfw.knowledgeBase.SharkKBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.MimeTypeUtils;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;


public class ContextPointMapper {

	private Logger LOG = LoggerFactory.getLogger(ContextPointMapper.class);

	private InformationContentDeserializer deserializer = new JsonInformationContentDeserializer();
	private InformationContentSerializer serializer = new JsonInformationContentSerializer();

	protected ContextPoint userContextPoint;

	public ContextPointMapper(ContextPoint userContextPoint) {
		this.userContextPoint = userContextPoint;
	}

	private Information getInformation(String id, String mimeType) {
		Iterator<Information> it = userContextPoint.getInformation("id");
		while (it.hasNext()) {
			Information information = it.next();
			if (mimeType.equals(information.getContentType())) {
				return information;
			}
		}
		return null;
	}

	public <T> void removeInformation(String name, String mimeType) {
		Information information = getInformation(name, mimeType);
		if (information != null) {
			information.removeContent();
			userContextPoint.removeInformation(information);
		}
	}

	public <T> T readInformation(String name, Class<T> type) {
		return readInformation(name, MimeTypeUtils.TEXT_PLAIN_VALUE, type);
	}

	public <T> T readInformation(String name, String mimeType, Class<T> type) {
		Information information = getInformation(name, mimeType);
		if (information == null) return null;
		try {
			if (deserializer.isDeserializeAble(type, information)) {
				return deserializer.deserialize(information, type);
			}
		} catch (SharkKBException | IOException e) {
			LOG.error("Deserialization failed for the field " + name + " of the type: " + type.getName(), e);
		}
		return null;
	}

	public <T> List<T> readInformationList(String fieldName, Class<T> type) {
		return readInformationList(fieldName, MimeTypeUtils.APPLICATION_JSON_VALUE, type);
	}

	public <T> List<T> readInformationList(String fieldName, String mimeType, Class<T> type) {
		Information information = getInformation(fieldName, mimeType);
		if (information == null) return null;
		try {
			return deserializer.deserializeList(information, type);
		} catch (SharkKBException | IOException e) {
			LOG.error("Deserialization failed for the field " + fieldName + " of the type: " + type.getName(), e);
		}
		return null;
	}

	public <T> void writeInformationList(String fieldName, List<T> list, Class<T> elementType, String mimeType) {
		writeInformation(fieldName, mimeType, list);
	}

	public <T> void writeInformationList(String fieldName, List<T> list, Class<T> elementType) {
		writeInformationList(fieldName, list, elementType,  MimeTypeUtils.APPLICATION_JSON_VALUE);
	}

	public <T> void writeInformation(String name, T value) {
		writeInformation(name, MimeTypeUtils.APPLICATION_JSON_VALUE, value);
	}

	public <T> void writeInformation(String name, String mimeType, T value) {

		if (value == null) {
			return;
		}

		Information information = getInformation(name, mimeType);
		if (information == null) {
			information = userContextPoint.addInformation();
		}

		if (serializer.canSerialize(value)) {
			try {
				information.setName(name);
				serializer.serialize(value, information);
			} catch (SharkKBException | IOException e) {
				LOG.error("Serialization failed for the field " + name + " of the type: " + value.getClass(), e);
			}
		}
	}





}
