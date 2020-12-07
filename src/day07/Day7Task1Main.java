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

// 16 not right
public class Day7Task1Main {

	private static final Pattern BAG_PATTERN = Pattern.compile("(\\d+) (\\w+\\s\\w+)");

	private static Map<String, Set<String>> path = new HashMap<>();

	public static void main(String[] args) {
		try {
			List<String> startValues = AdventUtils.getStringInput(7);

			for (String line : startValues) {

				String[] splitted = line.split(" bags contain ");
				String main = splitted[0];

				String[] containsRaw = splitted[1].replaceFirst(" bags?\\.", "").split(" bags?, ");

				Set<String> containing = new HashSet<>();

				for (String raw : containsRaw) {
					Matcher matcher = BAG_PATTERN.matcher(raw);

					if (matcher.matches()) {
						containing.add(matcher.group(2));
					}
				}

				path.put(main, containing);

			}

			int containsGold = 0;

			for (Set<String> containingBags : path.values()) {
				if (find("shiny gold", containingBags)) {
					containsGold++;
				}
			}

			AdventUtils.publishResult(7, 1, containsGold);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static boolean find(String target, Set<String> containingBags) {

		if (containingBags.contains(target)) {
			return true;
		}

		for (String bag : containingBags) {
			if (find(target, path.get(bag))) {
				return true;
			}
		}

		return false;
	}
}
