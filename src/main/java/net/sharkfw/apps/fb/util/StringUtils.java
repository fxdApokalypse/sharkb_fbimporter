package net.sharkfw.apps.fb.util;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StringUtils {
    /**
     * The new line character
     */
    public static final String NEWLINE = System.getProperty( "line.separator" );

    /**
     * Parse the fragment string from a url and
     * converted into a map.
     *
     * @param fragment the fragment
     *
     * @return the map which contains the key value pairs of the fragment.
     */
    public static Map<String, String> parseFragment(String fragment) {
        Stream<String> fragmentStream = Stream.of(fragment.split("&"));
        return fragmentStream
            .map((param) -> param.split("="))
            .collect(Collectors.toMap(param -> param[0], param -> param[1]));
    }
}
