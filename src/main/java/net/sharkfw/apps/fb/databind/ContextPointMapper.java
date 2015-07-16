package net.sharkfw.apps.fb.databind;

import net.sharkfw.knowledgeBase.ContextPoint;
import net.sharkfw.knowledgeBase.Information;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;

import java.util.Iterator;
import java.util.List;

/**
 * Created by fxdapokalypse on 16.07.2015.
 */
public class ContextPointMapper {
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

	public <T> T readInformation(String name, Class<T> type) {
		return readInformation(name, MimeTypeUtils.TEXT_PLAIN_VALUE, type);
	}

	public <T> T readInformation(String name, String mimeType, Class<T> type) {
		Information information = getInformation(name, mimeType);
		return null;
	}

	public <T> void writeInformation(String name, String mimeType, T value) {
		Information information = getInformation(name, mimeType);
		throw new IllegalArgumentException("not yet implemented");
	}

	public <T> void removeInformation(String name, String mimeType) {
		Information information = getInformation(name, mimeType);
		throw new IllegalArgumentException("not yet implemented");
	}

	protected <T> List<T> readInformationList(String meetingFor, MimeType textPlain, Class<T> stringClass) {
		return null;
	}
}
