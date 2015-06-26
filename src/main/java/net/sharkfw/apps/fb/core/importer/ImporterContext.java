package net.sharkfw.apps.fb.core.importer;

import net.sharkfw.knowledgeBase.*;
import org.springframework.social.facebook.api.User;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>The Important context is shared between multiple importers in order to share
 * information between importers.
 * E.g. so it is possible to reuse the already retrieved user profile or
 * the user peer by other importers.
 * </p>
 */
public class ImporterContext {

    /**
     * Property for the current facebook user which reference to the
     * user which is should be imported by the importer.
     */
    private static final String CURRENT_FB_USER = "current_fb_user";

    /**
     * Property which reference to the {@link PeerSemanticTag} for the user which should
     * be imported by the importer.
     */
    private static final String CURRENT_USER_PEER_SEMANTIC_TAG = "current_pst_user";

    /**
     * Abstraction of the property file in order to achieve type type safety.
     * @param <T> the type of the property value
     */
    private static class PropertyValue<T> {
        @SuppressWarnings("unchecked")
        static final PropertyValue NULL = new PropertyValue(null);
        private final T value;

        PropertyValue(T value) {
            this.value = value;
        }
        @SuppressWarnings("unchecked")
        <ExpectedType> ExpectedType getTypeAware(Class<ExpectedType> expectedType) {

            if (expectedType.isPrimitive() || expectedType.isInstance(this.value))
                return (ExpectedType) this.value;
            return  null;
        }

    }

    /**
     * The Set of properties nothing special here.
     */
    private final Map<String, PropertyValue> properties;

    /**
     * Creates an empty ImporterContext.
     */
    public ImporterContext() {
        properties = new HashMap<>();
    }

    /**
     * Retrieves the current facebook user which should be imported.
     *
     * @return the current facebook user or null if there is no such a facebook user.
     */
    public User getCurrentFBUser() {
        return getProperty(CURRENT_FB_USER, User.class);
    }

    /**
     * Defines the current facebook user which should be imported.
     *
     * @param user the facebook user which should be imported.
     */
    public void setCurrentFBUser(User user) {
        setProperty(CURRENT_FB_USER, user);
    }

    /**
     * Retrieves the {@link PeerSemanticTag} for the facebook user which should be imported.
     *
     * @return the {@link PeerSemanticTag} for the current facebook user or null if there is no such a PeerSemanticTag.
     */
    public PeerSemanticTag getCurrentUserPeerSemanticTag() {
         return getProperty(CURRENT_USER_PEER_SEMANTIC_TAG, PeerSemanticTag.class);
    }

    /**
     * Defines the {@link PeerSemanticTag} for the facebook user which should be imported.
     *
     * @param peerSemanticTag the {@link PeerSemanticTag} for the current facebook
     */
    public void setCurrentUserPeerSemanticTag(PeerSemanticTag peerSemanticTag) {
        setProperty(CURRENT_USER_PEER_SEMANTIC_TAG, peerSemanticTag);
    }

    /**
     * Retrieves the Boolean property by an id.
     *
     * @param id the id of the desired boolean property
     *
     * @return the boolean property or null if there is no such boolean property.
     */
    public Boolean getBoolean(String id) {
        return getProperty(id, Boolean.class);
    }

    /**
     * <p>Defines a boolean property with a specific id.</p>
     *
     * @param id the id of the property.
     * @param bool the value of the property
     */
    public void setBoolean(String id, Boolean bool) {
        setProperty(id, bool);
    }

    /**
     * Retrieves the byte property by an id.
     *
     * @param id the id of the desired byte property
     *
     * @return the byte property or null if there is no such byte property.
     */
    public Byte getByte(String id) {
        return getProperty(id, Byte.class);
    }

    /**
     * <p>Defines a byte property with a specific id.</p>
     *
     * @param id the id of the property.
     * @param byteValue the value of the property
     */
    public void setByte(String id, Byte byteValue) {
        setProperty(id, byteValue);
    }

    /**
     * Retrieves the short property by an id.
     *
     * @param id the id of the desired short property
     *
     * @return the short property or null if there is no such byte property.
     */
    public Short getShort(String id) {
        return getProperty(id, Short.class);
    }

    /**
     * <p>Defines a short property with a specific id.</p>
     *
     * @param id the id of the property.
     * @param shortValue the value of the property
     */
    public void setShort(String id, Short shortValue) {
        setProperty(id, shortValue);
    }

    /**
     * Retrieves the integer property by an id.
     *
     * @param id the id of the desired integer property
     *
     * @return the integer property or null if there is no such integer property.
     */
    public Integer getInteger(String id) {
        return getProperty(id, Integer.class);
    }

    /**
     * <p>Defines a integer property with a specific id.</p>
     *
     * @param id the id of the property.
     * @param integerValue the value of the property
     */
    public void setInteger(String id, Integer integerValue) {
        setProperty(id, integerValue);
    }

    /**
     * Retrieves the long property by an id.
     *
     * @param id the id of the desired long property
     *
     * @return the long property or null if there is no such long property.
     */
    public Long getLong(String id) {
        return getProperty(id, Long.class);
    }

    /**
     * <p>Defines a long property with a specific id.</p>
     *
     * @param id the id of the property.
     * @param longValue the value of the property
     */
    public void setLong(String id, Long longValue) {
        setProperty(id, longValue);
    }

    /**
     * Retrieves the string property by an id.
     *
     * @param id the id of the desired string property
     *
     * @return the string property or null if there is no such string property.
     */
    public String getString(String id) {
        return getProperty(id, String.class);
    }

    /**
     * <p>Defines a string property with a specific id.</p>
     *
     * @param id the id of the property.
     * @param string the value of the property
     */
    public void setString(String id, String string) {
        setProperty(id, string);
    }

    /**
     * Retrieves the SemanticTag property by an id.
     *
     * @param id the id of the desired SemanticTag property
     *
     * @return the SemanticTag property or null if there is no such SemanticTag property.
     */
    public SemanticTag getSemanticTag(String id) {
        return getProperty(id, SemanticTag.class);
    }

    /**
     * <p>Defines a SemanticTag property with a specific id.</p>
     *
     * @param id the id of the property.
     * @param semanticTag the value of the property
     */
    public void setSemanticTag(String id, SemanticTag semanticTag ) {
        setProperty(id, semanticTag);
    }

    /**
     * Retrieves the TimeSemanticTag property by an id.
     *
     * @param id the id of the desired TimeSemanticTag property
     *
     * @return the TimeSemanticTag property or null if there is no such TimeSemanticTag property.
     */
    public TimeSemanticTag getTimeSemanticTag(String id) {
        return getProperty(id, TimeSemanticTag.class);
    }

    /**
     * <p>Defines a TimeSemanticTag property with a specific id.</p>
     *
     * @param id the id of the property.
     * @param semanticTag the value of the property
     */
    public void setTimeSemanticTag(String id, TimeSemanticTag semanticTag ) {
        setProperty(id, semanticTag);
    }

    /**
     * Retrieves the SpatialSemanticTag property by an id.
     *
     * @param id the id of the desired SpatialSemanticTag property
     *
     * @return the SpatialSemanticTag property or null if there is no such SpatialSemanticTag property.
     */
    public SpatialSemanticTag getSpatialSemanticTag(String id) {
        return getProperty(id, SpatialSemanticTag.class);
    }

    /**
     * <p>Defines a SpatialSemanticTag property with a specific id.</p>
     *
     * @param id the id of the property.
     * @param semanticTag the value of the property
     */
    public void setSpatialSemanticTag(String id, SpatialSemanticTag semanticTag ) {
        setProperty(id, semanticTag);
    }

    /**
     * Retrieves the PeerSemanticTag property by an id.
     *
     * @param id the id of the desired PeerSemanticTag property
     *
     * @return the PeerSemanticTag property or null if there is no such PeerSemanticTag property.
     */
    public PeerSemanticTag getPeerSemanticTag(String id) {
        return getProperty(id, PeerSemanticTag.class);
    }

    /**
     * <p>Defines a PeerSemanticTag property with a specific id.</p>
     *
     * @param id the id of the property.
     * @param semanticTag the value of the property
     */
    public void setPeerSemanticTag(String id, PeerSemanticTag semanticTag ) {
        setProperty(id, semanticTag);
    }

    /**
     * Retrieves the SNSemanticTag property by an id.
     *
     * @param id the id of the desired SNSemanticTag property
     *
     * @return the SNSemanticTag property or null if there is no such SNSemanticTag property.
     */
    public SNSemanticTag getSNSemanticTag(String id) {
        return getProperty(id, SNSemanticTag.class);
    }

    /**
     * <p>Defines a setSNSemanticTag property with a specific id.</p>
     *
     * @param id the id of the property.
     * @param semanticTag the value of the property
     */
    public void setSNSemanticTag(String id, SNSemanticTag semanticTag ) {
        setProperty(id, semanticTag);
    }

    /**
     * Retrieves the TXSemanticTag property by an id.
     *
     * @param id the id of the desired TXSemanticTag property
     *
     * @return the TXSemanticTag property or null if there is no such TXSemanticTag property.
     */
    public TXSemanticTag getTXSemanticTag(String id) {
        return getProperty(id, TXSemanticTag.class);
    }

    /**
     * <p>Defines a TXSemanticTag property with a specific id.</p>
     *
     * @param id the id of the property.
     * @param semanticTag the value of the property
     */
    public void setTXSemanticTag(String id, SNSemanticTag semanticTag ) {
        setProperty(id, semanticTag);
    }

    /**
     * <p>Defines a property with a specific id.</p>
     *
     * @param id the id of the property.
     * @param value the value of the property
     * @param <T> the type of the property.
     */
    @SuppressWarnings("unchecked")
    public <T> void setProperty(String id, T value) {
        this.properties.put(id, new PropertyValue(value));
    }

    /**
     * <p>Retrieves a property by it's id.</p>
     *
     * <p>In order to achieve type safety a caller must provide
     * the type of the desired property.</p>
     *
     * @param id the id of the desired property.
     * @param type the expected type of the desired property.
     * @param <T> the expected type of the desired property
     *
     * @return the desired property or null if there is no such a property with the specified id.
     */
    public <T> T getProperty(String id, Class<T> type) {
        PropertyValue property = properties.getOrDefault(id, PropertyValue.NULL);
        return type.cast(property.getTypeAware(type));
    }

    /**
     * Checks if there is a property with a specified id and a specific type.
     *
     * @param id the id of the property.
     * @param type the expected type of the property
     *
     * @return true the requested property exists.
     */
    public boolean hasProperty(String id, Class type) {
        return getProperty(id, type) != null;
    }

}
