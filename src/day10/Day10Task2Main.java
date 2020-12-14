package day10;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import util.AdventUtils;

public class Day10Task2Main {

	public static void main(String[] args) {
		try {
			List<Integer> startValues = AdventUtils.getIntegerInput(10);
			List<Integer> adapter = startValues.stream().sorted().collect(Collectors.toList());

			List<Integer> splits = new ArrayList<>();

			int split = 0;

			for (int i = 0; i < adapter.size(); i++) {

				int last = 0;

				if (i > 0) {
					last = adapter.get(i - 1);
				}

				if (adapter.get(i) - last == 1) {
					split++;
				} else {
					if (split != 0) {
						splits.add(split);
					}
					split = 0;
				}

			}

			if (split != 0) {
				splits.add(split);
			}

			Map<Integer, Long> combMap = new HashMap<>();

			for (Integer splitLength : new HashSet<>(splits)) {
				combMap.put(splitLength, calcCombinations(0, splitLength) + 1);
			}

			Long totalCombinations = 1l;

			for (Integer spl : splits) {
				totalCombinations *= combMap.get(spl);
			}

			AdventUtils.publishExtra(10, 2, combMap.toString(), "combMap");
			AdventUtils.publishResult(10, 2, totalCombinations);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static long calcCombinations(int curr, int maxDepth) {

		long combinations = 0;

		if (curr + 1 <= maxDepth) {
			combinations += calcCombinations(curr + 1, maxDepth);
		}

		if (curr + 2 <= maxDepth) {
			combinations++;
			combinations += calcCombinations(curr + 2, maxDepth);
		}

		if (curr + 3 <= maxDepth) {
			combinations++;
			combinations += calcCombinations(curr + 3, maxDepth);
		}

		return combinations;
	}

}
