package net.sharkfw.apps.fb.model;

import net.sharkfw.apps.fb.databind.ContextPointMapper;
import net.sharkfw.knowledgeBase.ContextPoint;
import net.sharkfw.knowledgeBase.PeerSNSemanticTag;
import org.springframework.social.facebook.api.*;
import org.springframework.util.MimeTypeUtils;

import java.io.Serializable;
import java.util.*;


public class FBUser extends ContextPointMapper implements Serializable {

	public static final String ID = "id";
	public static final String ADDRESS = "address";
	public static final String NAME = "name";
	public static final String FIRST_NAME = "first_name";
	public static final String MEETING_FOR = "meetingFor";
	public static final String QUOTES = "quotes";
	public static final String POLITICAL = "political";
	public static final String RELIGION = "religion";
	public static final String LANGUAGE = "language";
	public static final String IDENTITY_VERIFIED = "identityVerified";
	public static final String HOMETOWN = "hometown";
	public static final String INSPIRATIONAL_PEOPLE = "inspirationalPeople";
	public static final String LOCATION = "location";
	public static final String BIRTHDAY = "Birthday";
	public static final String BIO = "bio";
	public static final String ABOUT = "about";
	public static final String VERIFIED = "verified";
	public static final String UPDATE_TIME = "updateTime";
	public static final String THIRD_PARTY_ID = "thirdPartyId";
	public static final String TIMEZONE = "timezone";
	public static final String WEBSITE = "website";
	public static final String LINK = "link";
	public static final String EMAIL = "email";
	public static final String LOCALE = "locale";
	public static final String INTERESTED_IN = "interestedIn";
	public static final String GENDER = "gender";
	public static final String NAME_FORMAT = "nameFormat";
	public static final String LAST_NAME = "lastName";
	public static final String MIDDLE_NAME = "middleName";

	private PeerSNSemanticTag userTag;

	public FBUser(PeerSNSemanticTag userTag, ContextPoint userContextPoint) {
		super(userContextPoint);
		this.userTag = userTag;
	}
	/**
	 * The user's Facebook ID
	 *
	 * @return The user's Facebook ID
	 */
	public String getId() {
		return readInformation(ID, String.class);
	}

	public Location getAddress() {
		return readInformation(ADDRESS, Location.class);
	}

	/**
	 * The user's full name
	 *
	 * @return The user's full name
	 */
	public String getName() {
		return readInformation(NAME, String.class);
	}

	/**
	 * The user's first name
	 *
	 * @return The user's first name
	 */
	public String getFirstName() {
		return readInformation(FIRST_NAME, String.class);
	}

	public List<String> getMeetingFor() {
		return readInformationList(MEETING_FOR, MimeTypeUtils.TEXT_PLAIN_VALUE, String.class);
	}

	/**
	 * The user's middle name
	 *
	 * @return The user's middle name
	 */
	public String getMiddleName() {
		return readInformation(MIDDLE_NAME, String.class);
	}

	/**
	 * The user's last name
	 *
	 * @return The user's last name
	 */
	public String getLastName() {
		return readInformation(LAST_NAME, String.class);
	}

	/**
	 * @return the name format used to correctly handle Chinese, Japanese, Korean ordering
	 */
	public String getNameFormat() {
		return readInformation(NAME_FORMAT, String.class);
	}

	/**
	 * The user's gender
	 *
	 * @return the user's gender
	 */
	public String getGender() {
		return readInformation(GENDER, String.class);
	}

	public List<String> getInterestedIn() {
		return readInformationList(INTERESTED_IN, MimeTypeUtils.TEXT_PLAIN_VALUE, String.class);
	}

	/**
	 * The user's locale
	 *
	 * @return the user's locale
	 */
	public Locale getLocale() {
		return readInformation(LOCALE, Locale.class);
	}

	/**
	 * The user's email address.
	 * Available only with "email" permission.
	 *
	 * @return The user's email address
	 */
	public String getEmail() {
		return readInformation(EMAIL, String.class);
	}

	/**
	 * A link to the user's profile on Facebook.
	 * Available only if requested by an authenticated user.
	 *
	 * @return the user's profile link or null if requested anonymously
	 */
	public String getLink() {
		return readInformation(LINK, String.class);
	}

	/**
	 * A link to the user's personal website. Available only with "user_website" permission.
	 *
	 * @return a link to the user's personal website.
	 */
	public String getWebsite() {
		return readInformation(WEBSITE, String.class);
	}

	/**
	 * The user's timezone offset from UTC.
	 * Available only for the authenticated user.
	 *
	 * @return the user's timezone offset from UTC or null if the user isn't the authenticated user
	 */
	public Float getTimezone() {
		return readInformation(TIMEZONE, Float.class);
	}

	/**
	 * An anonymous, but unique identifier for the user. Available only if
	 * requested by an authenticated user.
	 *
	 * @return the user's third-party ID or null if not available
	 */
	public String getThirdPartyId() {
		return readInformation(THIRD_PARTY_ID, String.class);
	}

	/**
	 * The last time the user's profile was updated.
	 *
	 * @return the time that the user's profile was updated
	 */
	public Date getUpdatedTime() {
		return readInformation(UPDATE_TIME, Date.class);
	}

	/**
	 * The user's account verification status.
	 * Available only if requested by an authenticated user.
	 *
	 * @return true if the profile has been verified, false if it has not, or null if not available.
	 */
	public Boolean isVerified() {
		return readInformation(VERIFIED, Boolean.class);
	}

	/**
	 * The user's brief about blurb.
	 * Available only with "user_about_me" permission for the authenticated user for the authenticated user's friends.
	 *
	 * @return the user's about blurb, if available.
	 */
	public String getAbout() {
		return readInformation(ABOUT, String.class);
	}

	/**
	 * The user's bio.
	 * Available only with "user_about_me" permission for the authenticated user.
	 *
	 * @return the user's bio, if available.
	 */
	public String getBio() {
		 return readInformation(BIO, String.class);
	}

	/**
	 * The user's birthday.
	 * Available only with "user_birthday" permission for the authentication user permission for the user's friends.
	 *
	 * @return the user's birthday
	 */
	public Date getBirthday() {
		return readInformation(BIRTHDAY, Date.class);
	}

	/**
	 * The user's location.
	 * Available only with "user_location" permission.
	 *
	 * @return a {@link Reference} to the user's location, if available
	 */
	public Reference getLocation() {
		return readInformation(LOCATION, Reference.class);
	}

	/**
	 * A list of references to people the user is inspired by.
	 *
	 * @return a list of {@link Reference} to people the user is inspired by, if available.
	 */
	public List<Reference> getInspirationalPeople() {
		return readInformationList(INSPIRATIONAL_PEOPLE, MimeTypeUtils.TEXT_PLAIN_VALUE, Reference.class);
	}

	/**
	 * The user's hometown.
	 * Available only with "user_hometown" permission.
	 *
	 * @return a {@link Reference} to the user's hometown, if available
	 */
	public Reference getHometown() {
		return readInformation(HOMETOWN, Reference.class);
	}

	public boolean isIdentityVerified() {
		return readInformation(IDENTITY_VERIFIED, Boolean.class );
	}

	/**
	 * A list of references to languages the user claims to know.
	 *
	 * @return a list of {@link Reference} to languages the user knows, if available.
	 */
	public List<Reference> getLanguages() {
		return readInformationList(LANGUAGE, MimeTypeUtils.TEXT_PLAIN_VALUE, Reference.class);
	}

	/**
	 * The user's religion.
	 * Available only with "user_religion_politics" permission.
	 *
	 * @return the user's religion, if available.
	 */
	public String getReligion() {
		return readInformation(RELIGION, String.class);
	}


	/**
	 * The user's political affiliation.
	 * Available only with "user_religion_politics" permission.
	 *
	 * @return the user's political affiliation, if available.
	 */
	public String getPolitical() {
		return readInformation(POLITICAL, String.class);
	}

	/**
	 * The user's quotations.
	 * Available only with "user_about_me" permission.
	 *
	 * @return the user's quotations, if available.
	 */
	public String getQuotes() {
		return readInformation(QUOTES, String.class);
	}

	/**
	 * The user's relationship status.
	 * Available only with "user_relationships" permission.
	 *
	 * @return the user's relationship status, if available.
	 */
	public String getRelationshipStatus() {
		return readInformation("relationshipStatus", String.class);
	}

	/**
	 * The user's significant other.
	 * Available only for certain relationship statuses and with "user_relationship_details" permission.
	 *
	 * @return a {@link Reference} to the user's significant other, if available.
	 */
	public List<ContextPointMapper> getSignificantOther() {
		return null;
	}

}
