package day08;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import util.AdventUtils;
import util.Tuple;

public class Day8Task1Main {

	public static void main(String[] args) {
		try {
			List<String> startValues = AdventUtils.getStringInput(8);

			List<Tuple<String, Integer>> code = startValues.stream().map(l -> {
				String[] splitted = l.split(" ");
				return new Tuple<>(splitted[0], Integer.valueOf(splitted[1]));
			}).collect(Collectors.toList());

			AdventUtils.publishResult(8, 1, run(code));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static long run(List<Tuple<String, Integer>> code) {
		long accumulator = 0;
		int pointer = 0;

		Set<Integer> visitedPointer = new HashSet<>();

		while (!visitedPointer.contains(pointer)) {
			visitedPointer.add(pointer);

			Tuple<String, Integer> codeLine = code.get(pointer);

			switch (codeLine.getLeft()) {

			case "acc":
				accumulator += codeLine.getRight();
				pointer++;
				break;
			case "jmp":
				pointer += codeLine.getRight();
				break;
			case "nop":
				pointer++;
				break;
			}

		}

		return accumulator;

	}
}
