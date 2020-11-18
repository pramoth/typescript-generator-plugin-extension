package th.co.geniustree.typescript;

public class StringUtils {
    public static String camelCaseToUnderscores(String camelCase) {
        StringBuilder builder = new StringBuilder();
        char[] chars = camelCase.toCharArray();
        for (int j = 0; j < chars.length; j++) {
            if (Character.isUpperCase(chars[j])) {
                if (j != 0) {
                    builder.append('_');
                }
                builder.append(Character.toLowerCase(chars[j]));
            } else {
                builder.append(chars[j]);
            }
        }
        return builder.toString();
    }
}
