package net.sharkfw.apps.fb.databind;

import net.sharkfw.knowledgeBase.Information;
import net.sharkfw.knowledgeBase.SharkKBException;

import java.io.IOException;
import java.util.List;


public interface InformationContentDeserializer {
	<T> T deserialize(Information information, Class<T> type) throws IOException, SharkKBException;
	<T> List<T>  deserializeList(Information information, Class<T> type) throws IOException, SharkKBException;
	<T> boolean  isDeserializeAble(Class<T> type, Information information) throws IOException, SharkKBException;
}
