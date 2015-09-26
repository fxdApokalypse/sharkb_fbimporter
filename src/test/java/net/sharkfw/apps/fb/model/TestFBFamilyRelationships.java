package net.sharkfw.apps.fb.model;


import static org.junit.Assert.*;

import org.junit.Test;

public class TestFBFamilyRelationships {

	@Test
	public void normalizeRelationShip_WithValidRelationShips_RelationshipsMustBeGenderNeutral() {

		assertEquals("grandchild", FBFamilyRelationships.normalizeRelationShip("grandchild"));
		assertEquals("grandchild", FBFamilyRelationships.normalizeRelationShip("granddaughter"));
		assertEquals("grandchild", FBFamilyRelationships.normalizeRelationShip("grandson"));

		assertEquals("grandparent", FBFamilyRelationships.normalizeRelationShip("grandparent"));
		assertEquals("grandparent", FBFamilyRelationships.normalizeRelationShip("grandmother"));
		assertEquals("grandparent", FBFamilyRelationships.normalizeRelationShip("grandfather"));

		assertEquals("parent", FBFamilyRelationships.normalizeRelationShip("parent"));
		assertEquals("parent", FBFamilyRelationships.normalizeRelationShip("step parent"));
		assertEquals("parent", FBFamilyRelationships.normalizeRelationShip("mother"));
		assertEquals("parent", FBFamilyRelationships.normalizeRelationShip("father"));

		assertEquals("child", FBFamilyRelationships.normalizeRelationShip("child"));
		assertEquals("child", FBFamilyRelationships.normalizeRelationShip("step child"));
		assertEquals("child", FBFamilyRelationships.normalizeRelationShip("daughter"));
		assertEquals("child", FBFamilyRelationships.normalizeRelationShip("son"));

		assertEquals("sibling", FBFamilyRelationships.normalizeRelationShip("sibling"));
		assertEquals("sibling", FBFamilyRelationships.normalizeRelationShip("step sibling"));
		assertEquals("sibling", FBFamilyRelationships.normalizeRelationShip("sister"));
		assertEquals("sibling", FBFamilyRelationships.normalizeRelationShip("brother"));

		assertEquals("sibling of parent", FBFamilyRelationships.normalizeRelationShip("sibling of parent"));
		assertEquals("sibling of parent", FBFamilyRelationships.normalizeRelationShip("aunt"));
		assertEquals("sibling of parent", FBFamilyRelationships.normalizeRelationShip("uncle"));

		assertEquals("child of sibling", FBFamilyRelationships.normalizeRelationShip("child of sibling"));
		assertEquals("child of sibling", FBFamilyRelationships.normalizeRelationShip("niece"));
		assertEquals("child of sibling", FBFamilyRelationships.normalizeRelationShip("nephew"));

		assertEquals("cousin", FBFamilyRelationships.normalizeRelationShip("cousin"));
	}

	@Test
	public void normalizeRelationShip_WithInvalidRelationShips_ShouldReturnNull() {
		assertNull(FBFamilyRelationships.normalizeRelationShip("Not a Relationships"));
	}

	@Test
	public void reversedRelationShip_WithValidNormalizedRelationShips_RelationshipShouldBeReversed() {
		assertEquals("child of sibling", FBFamilyRelationships.reversedRelationShip("sibling of parent"));
		assertEquals("sibling of parent", FBFamilyRelationships.reversedRelationShip("child of sibling"));

		assertEquals("child", FBFamilyRelationships.reversedRelationShip("parent"));
		assertEquals("parent", FBFamilyRelationships.reversedRelationShip("child"));

		assertEquals("grandchild", FBFamilyRelationships.reversedRelationShip("grandparent"));
		assertEquals("grandparent", FBFamilyRelationships.reversedRelationShip("grandchild"));

		assertEquals("grandchild", FBFamilyRelationships.reversedRelationShip("grandparent"));
		assertEquals("grandparent", FBFamilyRelationships.reversedRelationShip("grandchild"));
	}

	@Test
	public void reversedRelationShip_WithInValidRelationShips_ReturnTheInvalidRelationship() {
		assertEquals("UNKNOWN", FBFamilyRelationships.reversedRelationShip("UNKNOWN"));
	}
}
