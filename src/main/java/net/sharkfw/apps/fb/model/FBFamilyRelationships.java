package net.sharkfw.apps.fb.model;

import net.sharkfw.apps.fb.util.KBUtils;
import net.sharkfw.knowledgeBase.PeerSNSemanticTag;
import net.sharkfw.knowledgeBase.SharkKB;
import net.sharkfw.knowledgeBase.SharkKBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.facebook.api.FamilyMember;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FBFamilyRelationships {

    public static final Logger LOG = LoggerFactory.getLogger(FBFamilyRelationships.class);

    public static final String GRANDCHILD = "grandchild";
    public static final String GRANDDAUGHTER = "granddaughter";
    public static final String GRANDSON = "grandson";
    public static final List<String> grandChildTerms = Arrays.asList(
        GRANDCHILD, GRANDDAUGHTER,
        GRANDSON
    );

    public static final String GRANDPARENT = "grandparent";
    public static final String GRANDMOTHER = "grandmother";
    public static final String GRANDFATHER = "grandfather";
    public static final List<String> grandParentTerms = Arrays.asList(
        GRANDPARENT, GRANDMOTHER,
        GRANDFATHER
    );

    public static final String STEP_PARENT = "step parent";
    public static final String PARENT ="parent";
    public static final String MOTHER ="mother";
    public static final String FATHER ="father";
    public static final List<String> parentTerms = Arrays.asList(
        PARENT, STEP_PARENT,
        MOTHER, FATHER
    );

    public static final String STEP_CHILD ="step child";
    public static final String CHILD = "child";
    public static final String DAUGHTER = "daughter";
    public static final String SON = "son";
    public static final List<String> childTerms = Arrays.asList(
        CHILD, STEP_CHILD,
        DAUGHTER, SON
    );

    public static final String STEP_SIBLING="step_sibling";
    public static final String SIBLING="sibling";
    public static final String SISTER ="sister";
    public static final String BROTHER ="brother";
    public static final List<String> siblingTerms = Arrays.asList(
        SIBLING, STEP_SIBLING,
        SISTER, BROTHER
    );

    public static final String SIBLING_OF_PARENT = "sibling of parent";
    public static final String AUNT = "aunt";
    public static final String UNCLE = "uncle";
    public static final List<String> siblingOfParentTerms = Arrays.asList(
        SIBLING_OF_PARENT, AUNT,
        UNCLE
    );

    public static final String CHILD_OF_SIBLING = "child of sibling";
    public static final String NIECE = "niece";
    public static final String NEPHEW = "nephew";
    public static final List<String> childOfSiblingTerms = Arrays.asList(
        CHILD_OF_SIBLING, NIECE,
        NEPHEW
    );

    public static final String COUSINE = "cousin";
    public static final String COUSIN = "cousin";
    public static final List<String> cousinTerms = Arrays.asList(
        COUSIN, COUSINE
    );

    public static final String PARENT_IN_LAW = "parent-in-law";
    public static final String CHILD_IN_Law="child-in-law";

    public static void connect(PeerSNSemanticTag user, FamilyMember member, SharkKB kb) throws SharkKBException {
        String relationShip = member.getRelationship();
        String normalizedRelationShip = normalizeRelationShip(relationShip);

        if (normalizedRelationShip == null) {
            LOG.error(String.format("Unsupported relationship was found %s", relationShip));
        }
        PeerSNSemanticTag familyMemberSemanticTag = KBUtils.createPeerSNTagFrom(member, kb);
        KBUtils.connectDirectional(normalizedRelationShip, user, familyMemberSemanticTag);
        KBUtils.connectDirectional(reversedRelationShip(normalizedRelationShip), familyMemberSemanticTag, user);

    }

    private static String reversedRelationShip(String normalizedRelationShip) {
        switch (normalizedRelationShip) {
            case CHILD_OF_SIBLING:
                return SIBLING_OF_PARENT;
            case SIBLING_OF_PARENT:
                return CHILD_OF_SIBLING;
            case CHILD:
                return PARENT;
            case PARENT:
                return CHILD;
            case GRANDPARENT:
                return GRANDCHILD;
            case GRANDCHILD:
                return GRANDPARENT;
        }
        return normalizedRelationShip;
    }

    /**
     * Normalize the family relationship into a gender neutral description.
     * For example the call <code>normalizeRelationShip("sister")</code>
     * will be resulting in the relationship description <code>sibling</code>.
     *
     * @param relationShip the to be converted relationship.
     *
     * @return the normalized relationship description or null if the conversion wasn't successfully
     */
    public static String normalizeRelationShip( String relationShip ) {
        Field[] fields = FBFamilyRelationships.class.getFields();
        List<String> relationShipTerms = Arrays.stream(fields)
            .filter(ReflectionUtils::isPublicStaticFinal)
            .filter((field) -> field.getType().equals(List.class))
            .filter((field) -> field.getName().indexOf("Terms") != 0)
            .map((field) -> {
                try {
                    return (List<String>) field.get(null);
                } catch (IllegalAccessException e) {
                    return null;
                }
            })
            .filter(Objects::nonNull)
            .filter((rel) -> rel.contains(relationShip))
            .map((rel) -> rel.get(0))
            .collect(Collectors.<String>toList());

        return relationShipTerms.size() == 0 ? null : relationShipTerms.get(0);

    }

}
