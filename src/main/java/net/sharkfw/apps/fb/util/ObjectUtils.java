package net.sharkfw.apps.fb.util;

import java.util.Objects;

/**
 * Convenient Object utility functions.
 */
public class ObjectUtils {

	/**
	 * Returns a alternative object if a given object is null
	 *
	 * @param object the object which may become null.
	 * @param nullAlternative the alternative object which should replace the given object if it's null.
	 * @param <T> The type of the object is used for type safety.
	 * @return the object or the alternative object if object is null.
	 */
	public static <T> T nullAlternative(T object, T nullAlternative) {
		return Objects.isNull(object) ? object : nullAlternative;
	}
}
