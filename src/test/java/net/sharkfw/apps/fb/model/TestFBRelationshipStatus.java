package net.sharkfw.apps.fb.model;

import org.junit.Test;
import org.springframework.social.facebook.api.User;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.ReflectionUtils;

import java.util.Locale;

import static org.junit.Assert.*;

public class TestFBRelationshipStatus {

	@Test
	public void isValid_widthRandomRelationShips_shouldDetectValidRelationships() {
		assertTrue(FBRelationshipStatus.isValid("single"));
		assertTrue(FBRelationshipStatus.isValid("In an open relationship"));
		assertTrue(FBRelationshipStatus.isValid("In an open relationship (Pending)"));
		assertTrue(FBRelationshipStatus.isValid("Engaged"));
		assertTrue(FBRelationshipStatus.isValid("Engaged (Pending)"));
		assertTrue(FBRelationshipStatus.isValid("Married"));
		assertTrue(FBRelationshipStatus.isValid("Married (Pending)"));
		assertTrue(FBRelationshipStatus.isValid("In a civil union"));
		assertTrue(FBRelationshipStatus.isValid("In a civil union (Pending)"));
		assertTrue(FBRelationshipStatus.isValid("In a domestic partnership"));
		assertTrue(FBRelationshipStatus.isValid("In a domestic partnership (Pending)"));
		assertTrue(FBRelationshipStatus.isValid("It's complicated"));
		assertTrue(FBRelationshipStatus.isValid("It's complicated (Pending)"));
		assertTrue(FBRelationshipStatus.isValid("Separated"));
		assertTrue(FBRelationshipStatus.isValid("Divorced"));
		assertTrue(FBRelationshipStatus.isValid("Widowed"));
		assertFalse(FBRelationshipStatus.isValid("Lorem ipsum dolor sit"));
	}

	@Test
	public void isPending_widthRandomRelationShips_shouldDetectPendingRelationships() {
		assertFalse(FBRelationshipStatus.isPending("In an open relationship"));
		assertTrue(FBRelationshipStatus.isPending("In an open relationship (Pending)"));
		assertFalse(FBRelationshipStatus.isPending("Widowed"));
	}

	@Test
	public void isActiveRelationship_widthRandomRelationShips_shouldDetectActiveRelationships() {
		assertFalse(FBRelationshipStatus.isActiveRelationship("single"));
		assertTrue(FBRelationshipStatus.isActiveRelationship("In an open relationship"));
		assertFalse(FBRelationshipStatus.isActiveRelationship("In an open relationship (Pending)"));
		assertTrue(FBRelationshipStatus.isActiveRelationship("Engaged"));
		assertFalse(FBRelationshipStatus.isActiveRelationship("Engaged (Pending)"));
		assertTrue(FBRelationshipStatus.isActiveRelationship("Married"));
		assertFalse(FBRelationshipStatus.isActiveRelationship("Married (Pending)"));
		assertTrue(FBRelationshipStatus.isActiveRelationship("In a civil union"));
		assertFalse(FBRelationshipStatus.isActiveRelationship("In a civil union (Pending)"));
		assertTrue(FBRelationshipStatus.isActiveRelationship("In a domestic partnership"));
		assertFalse(FBRelationshipStatus.isActiveRelationship("In a domestic partnership (Pending)"));
		assertTrue(FBRelationshipStatus.isActiveRelationship("It's complicated"));
		assertFalse(FBRelationshipStatus.isActiveRelationship("It's complicated (Pending)"));
		assertFalse(FBRelationshipStatus.isActiveRelationship("Separated"));
		assertFalse(FBRelationshipStatus.isActiveRelationship("Divorced"));
		assertFalse(FBRelationshipStatus.isActiveRelationship("Widowed"));
		assertFalse(FBRelationshipStatus.isActiveRelationship("Lorem ipsum dolor sit"));
	}

	@Test
	public void userIsInRelationship() {
		User user = new User("id", "Name", "FirstName", "LastName", "Gender", Locale.getDefault());
		ReflectionTestUtils.setField(user, "relationshipStatus", "");

	}

}
