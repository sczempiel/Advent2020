package day06;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.AdventUtils;
import util.Tuple;

public class Day6Task2Main {

	public static void main(String[] args) {
		try {
			List<String> startValues = AdventUtils.getStringInput(6);

			List<Tuple<Integer, Map<Character, Integer>>> answers = new ArrayList<>();
			Map<Character, Integer> chars = new HashMap<>();
			Tuple<Integer, Map<Character, Integer>> group = new Tuple<>(0, chars);
			answers.add(group);

			for (String line : startValues) {

				if (line.isEmpty()) {
					chars = new HashMap<>();
					group = new Tuple<>(0, chars);
					answers.add(group);
					continue;
				}

				group.setLeft(group.getLeft() + 1);

				for (char c : line.toCharArray()) {
					Integer count = chars.get(c);

					if (count == null) {
						count = 0;
					}

					chars.put(c, count + 1);
				}

			}

			long resultSum = answers.stream()
					.mapToLong(
							g -> g.getRight().entrySet().stream().filter(e -> e.getValue().equals(g.getLeft())).count())
					.sum();
			AdventUtils.publishResult(6, 2, resultSum);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
