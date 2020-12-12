package day07;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import util.AdventUtils;
import util.Tuple;

public class Day7Task2Main {

	private static final Pattern BAG_PATTERN = Pattern.compile("(\\d+) (\\w+\\s\\w+)");

	private static Map<String, Set<Tuple<String, Integer>>> path = new HashMap<>();

	public static void main(String[] args) {
		try {
			List<String> startValues = AdventUtils.getStringInput(7);

			for (String line : startValues) {

				String[] splitted = line.split(" bags contain ");
				String main = splitted[0];

				String[] containsRaw = splitted[1].replaceFirst(" bags?\\.", "").split(" bags?, ");

				Set<Tuple<String, Integer>> containing = new HashSet<>();

				for (String raw : containsRaw) {
					Matcher matcher = BAG_PATTERN.matcher(raw);

					if (matcher.matches()) {
						containing.add(Tuple.of(matcher.group(2), Integer.valueOf(matcher.group(1))));
					}
				}

				path.put(main, containing);

			}

			AdventUtils.publishResult(7, 2, count(path.get("shiny gold"), 1));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static long count(Set<Tuple<String, Integer>> containingBags, long parentCount) {

		long totalCount = 0;
		for (Tuple<String, Integer> bag : containingBags) {
			long count = bag.getRight() * parentCount;

			totalCount += count;

			totalCount += count(path.get(bag.getLeft()), count);
		}

		return totalCount;
	}
}
