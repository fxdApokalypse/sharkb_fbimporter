package net.sharkfw.apps.fb.databind;

import net.sharkfw.knowledgeBase.Information;
import net.sharkfw.knowledgeBase.SharkKBException;

import java.io.IOException;
import java.util.List;

public interface InformationContentSerializer<T> {

	public static final String TYPE_PROPERTY = InformationContentSerializer.class.getName() + "_TYPE";

	void serialize(T object, Information information) throws IOException, SharkKBException;
	void serialize(List<T> object, Information information) throws IOException, SharkKBException;
	boolean canSerialize(T object);
}
