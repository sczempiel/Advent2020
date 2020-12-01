package util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CollectionUtils {

	public static <T> Set<Set<T>> combinations(T[] toCombine) {
		return combinations(Arrays.asList(toCombine));
	}

	public static <T> Set<Set<T>> combinations(List<T> toCombine) {

		Set<Set<T>> combinations = new HashSet<>();

		for (long i = 1; i < Math.pow(2, toCombine.size()); i++) {

			Set<T> combination = new HashSet<>();

			for (int j = 0; j < toCombine.size(); j++) {

				if ((i & (long) Math.pow(2, j)) > 0) {
					combination.add(toCombine.get(j));
				}

			}

			combinations.add(combination);
		}
		return combinations;
	}
}
