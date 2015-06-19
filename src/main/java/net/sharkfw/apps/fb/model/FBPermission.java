package net.sharkfw.apps.fb.model;

import com.restfb.*;

public class FBPermission {

    /**
     * Constant which indicates that this permission is granted
     */
    protected static final String STATUS_GRANTED = "granted";

    /**
     * The name of the permission
     */
    @Facebook
    protected String permission;

    /**
     * Status of the permission granted or non granted.
     */
    @Facebook
    protected String status;

    /**
     * Retrieves the name of this permission.
     *
     * @return the name of this permission.
     */
    public String getPermission() {
        return permission;
    }

    /**
     * Checks if the permission is granted.
     *
     * @return true if the this permission is granted.
     */
    public boolean isGranted() {
        return STATUS_GRANTED.equals(status);
    }

    @Override
    public String toString() {
        return String.format(
            "FBPermission [%s = %s]",
            getPermission(),
            (isGranted() ? "" : "not ") + STATUS_GRANTED);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FBPermission that = (FBPermission) o;

        return !(permission != null ? !permission.equals(that.permission) : that.permission != null);
    }

    @Override
    public int hashCode() {
        int result = permission != null ? permission.hashCode() : 0;
        return result;
    }

    public static void main(String[] args) {
        String accessToken = "CAACEdEose0cBAPOiMoXBOegj633mmWRMNfr0rCZBucgJAZCn4vk8b7YjIgPhLzcPZCPacktdZAlKPZBtrzSnrWzwBBa5xU2NXAtxTtuBTcFQAetXtnnmfj1ZA7BXCZALcUk8gixqB7fPjsFBKstIqgVjBzYZAZAIELkW26uaZBi4ZCbUvh6ZCaM1MOq5jQPZBgkRPPqqparPobRozYX4HB8slIEUGbJAkQP9RgZBEZD";
        FacebookClient client = new DefaultFacebookClient(accessToken, Version.VERSION_2_3);
        Connection<FBPermission> permissions = client.fetchConnection("me/permissions", FBPermission.class);
        permissions.getData().forEach(System.out::println);
    }
}
