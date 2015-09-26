package net.sharkfw.apps.fb.model;

import java.util.Arrays;
import java.util.List;

/**
 * Helper class for handling the relationship status description
 * of a facebook user.
 */
public class FBRelationshipStatus {

	public static final String SINGLE = "single";
	public static final String IN_AN_OPEN_RELATIONSHIP = "In an open relationship";
	public static final String IN_AN_OPEN_RELATIONSHIP_PENDING = "In an open relationship (Pending)";
	public static final String ENGAGED = "Engaged";
	public static final String ENGAGED_PENDING = "Engaged (Pending)";
	public static final String MARRIED = "Married";
	public static final String MARRIED_PENDING = "Married (Pending)";
	public static final String IN_A_CIVIL_UNION = "In a civil union";
	public static final String IN_A_CIVIL_UNION_PENDING = "In a civil union (Pending)";
	public static final String IN_A_DOMESTIC_PARTNERSHIP = "In a domestic partnership";
	public static final String IN_A_DOMESTIC_PARTNERSHIP_PENDING = "In a domestic partnership (Pending)";
	public static final String IT_S_COMPLICATED = "It's complicated";
	public static final String IT_S_COMPLICATED_PENDING = "It's complicated (Pending)";
	public static final String SEPARATED = "Separated";
	public static final String WIDOWED = "Widowed";
	public static final String DIVORCED = "Divorced";

	public static final List<String> validStatuses = Arrays.asList(
			SINGLE,
			IN_AN_OPEN_RELATIONSHIP,
			IN_AN_OPEN_RELATIONSHIP_PENDING,
			ENGAGED,
			ENGAGED_PENDING,
			MARRIED,
			MARRIED_PENDING,
			IN_A_CIVIL_UNION,
			IN_A_CIVIL_UNION_PENDING,
			IN_A_DOMESTIC_PARTNERSHIP,
			IN_A_DOMESTIC_PARTNERSHIP_PENDING,
			IT_S_COMPLICATED,
			IT_S_COMPLICATED_PENDING,
			SEPARATED,
			DIVORCED,
			WIDOWED
	);

	public static final List<String> validActiveStatuses = Arrays.asList(
			IN_AN_OPEN_RELATIONSHIP,
			ENGAGED,
			MARRIED,
			IN_A_CIVIL_UNION,
			IN_A_DOMESTIC_PARTNERSHIP,
			IT_S_COMPLICATED
	);

	public static boolean isValid(String relationshipStatus) {
		return validStatuses.contains(relationshipStatus);
	}

	public static boolean isPending(String relationshipStatus) {
		return relationshipStatus.contains("(Pending)");
	}

	public static boolean isActiveRelationship(String relationshipStatus) {
		return validActiveStatuses.contains(relationshipStatus);
	}
}
