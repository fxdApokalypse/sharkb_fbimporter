package net.sharkfw.apps.fb.databind;


import net.sharkfw.apps.fb.BaseTest;
import net.sharkfw.apps.fb.util.shark.ContextCoordinatesBuilder;
import net.sharkfw.knowledgeBase.ContextPoint;
import net.sharkfw.knowledgeBase.Information;
import net.sharkfw.knowledgeBase.SharkKBException;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class JSONInformationContentDataBindTest extends BaseTest {

	@Test
	public void serializeAndDeserializeAString() throws SharkKBException, IOException {
		ContextPoint cp = ContextCoordinatesBuilder.create(getKB()).createContextPoint();
		Information info = null;
		JsonInformationContentDeserializer deserializer = new JsonInformationContentDeserializer();
		JsonInformationContentSerializer serializer = new JsonInformationContentSerializer();

		String testData = "Du Du Du DU DU";
		String actual = null;

		if (serializer.canSerialize(testData)) {
			info = cp.addInformation();
			info.setName("testData");
			serializer.serialize(testData, info);
		}

		if (deserializer.isDeserializeAble(String.class, info)) {
			actual = deserializer.deserialize(info, String.class);
		}


		Assert.assertEquals(testData, actual);
	}

	@Test
	public void serializeAndDeserializeAList() throws SharkKBException, IOException {
		ContextPoint cp = ContextCoordinatesBuilder.create(getKB()).createContextPoint();
		Information info = null;
		JsonInformationContentDeserializer deserializer = new JsonInformationContentDeserializer();
		JsonInformationContentSerializer serializer = new JsonInformationContentSerializer();

		List<String> testData = Arrays.asList("Blu", "Blu");
		List<String> actual = null;

		if (serializer.canSerialize(testData.get(0))) {
			info = cp.addInformation();
			info.setName("testData");
			serializer.serialize(testData, info);
		}

		if (deserializer.isDeserializeAble(String.class, info)) {
			actual = deserializer.deserializeList(info, String.class);
		}

		Assert.assertNotNull(actual);
		Assert.assertEquals(testData.size(), actual.size());
		Assert.assertEquals(testData.get(0), actual.get(0));
		Assert.assertEquals(testData.get(1), actual.get(1));
	}

}
