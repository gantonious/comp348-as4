package part3;

import java.util.List;

/**
 * Created by George on 2018-01-04.
 */
public class StringUtils {
    public static String join(List<String> strings, String joinString) {
        if (strings.isEmpty()) {
            return "";
        }

        return strings.stream()
                .reduce("", (string, nextString) -> string + joinString + nextString)
                .substring(joinString.length());
    }
}
