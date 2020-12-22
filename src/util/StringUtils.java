package util;

public class StringUtils {
	public static String reverse(String toReverse) {
		StringBuilder sb = new StringBuilder();

		for (int i = toReverse.length() - 1; i >= 0; i--) {
			sb.append(toReverse.charAt(i));
		}

		return sb.toString();
	}
}
