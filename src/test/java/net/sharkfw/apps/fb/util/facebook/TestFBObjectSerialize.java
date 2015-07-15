package net.sharkfw.apps.fb.util.facebook;

import net.sharkfw.apps.fb.BaseFBImporterTests;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.social.facebook.api.User;
import org.springframework.util.StreamUtils;

public class TestFBObjectSerialize extends BaseFBImporterTests {

    @Test
    public void ObjectSerialize_serializeObject_objectIsSerializedAsExpected() throws FBSerializeException, Exception {


        User expectedUser = getTestJSONObject("me", User.class);
        FBObjectSerializer serializer = new FBObjectSerializer();
        String actualStr = serializer.serializeAsString(expectedUser);
        byte[] expectedBuffer = StreamUtils.copyToByteArray(getTestResource("me").getInputStream());
        String expectedStr = new String(expectedBuffer, "UTF-8");

        System.out.println(expectedStr);
        System.out.println(actualStr);

        JSONAssert.assertEquals(expectedStr, actualStr, false);


    }

}
