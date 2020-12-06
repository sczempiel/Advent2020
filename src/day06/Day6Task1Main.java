package day06;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import util.AdventUtils;

public class Day6Task1Main {

	public static void main(String[] args) {
		try {
			List<String> startValues = AdventUtils.getStringInput(6);

			List<Set<Character>> answers = new ArrayList<>();
			Set<Character> group = new HashSet<>();
			answers.add(group);

			for (String line : startValues) {

				if (line.isEmpty()) {
					group = new HashSet<>();
					answers.add(group);
					continue;
				}

				for (char c : line.toCharArray()) {
					group.add(c);
				}

			}

			int resultSum = answers.stream().mapToInt(g -> g.size()).sum();
			AdventUtils.publishResult(6, 1, resultSum);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
