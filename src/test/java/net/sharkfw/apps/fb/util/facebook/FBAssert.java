package net.sharkfw.apps.fb.util.facebook;
import org.junit.Assert;
import org.springframework.social.facebook.api.Reference;
import org.springframework.social.facebook.api.User;

import java.util.List;


public class FBAssert {

    public static void assertEquals(User expected, User actual) {
        Assert.assertEquals(expected.getId() , actual.getId());
        Assert.assertEquals(expected.getFirstName() , actual.getFirstName());
        Assert.assertEquals(expected.getMiddleName() , actual.getMiddleName());
        Assert.assertEquals(expected.getLastName() , actual.getLastName());
        Assert.assertEquals(expected.getName(), actual.getName());
        Assert.assertEquals(expected.getLocale(), actual.getLocale());
        Assert.assertEquals(expected.getGender(), actual.getGender());
        Assert.assertEquals(expected.getEmail(), actual.getEmail());
        Assert.assertEquals(expected.getLink(), actual.getLink());
        Assert.assertEquals(expected.getThirdPartyId(), actual.getThirdPartyId());
        Assert.assertEquals(expected.getTimezone(), actual.getTimezone());
        Assert.assertEquals(expected.getUpdatedTime(), actual.getUpdatedTime());
        Assert.assertEquals(expected.isVerified(), actual.isVerified());
        Assert.assertEquals(expected.getAbout(), actual.getAbout());
        Assert.assertEquals(expected.getBio(), actual.getBio());
        Assert.assertEquals(expected.getBirthday(), actual.getBirthday());
        Assert.assertEquals(expected.getReligion(), actual.getReligion());
        assertEquals(expected.getLocation(), actual.getLocation());
        assertEquals(expected.getHometown(), actual.getHometown());
        Assert.assertEquals(expected.getPolitical(), actual.getPolitical());
        Assert.assertEquals(expected.getQuotes(), actual.getQuotes());
        Assert.assertEquals(expected.getRelationshipStatus(), actual.getRelationshipStatus());
        assertEquals(expected.getSignificantOther(), actual.getSignificantOther());
        Assert.assertEquals(expected.getWebsite(), actual.getWebsite());
        assertEquals(expected.getInspirationalPeople(), actual.getInspirationalPeople());
        assertEquals(expected.getLanguages(), actual.getLanguages());
        assertEquals(expected.getFavoriteTeams(), actual.getFavoriteTeams());
        assertEquals(expected.getFavoriteAtheletes(), actual.getFavoriteAtheletes());
    }

    public static void assertEquals(Reference expected, Reference actual) {
        if (expected == null) {
            Assert.assertNull(actual);
            return;
        }
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getName(), expected.getName());
    }

    public static void assertEquals(List<Reference> expected, List<Reference> actual) {
        if (expected == null) {
            Assert.assertNull(actual);
            return;
        }

        Assert.assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i), actual.get(i));
        }
    }

}
