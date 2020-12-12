package day08;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import util.AdventUtils;
import util.Tuple;

public class Day8Task2Main {

	public static void main(String[] args) {
		try {
			List<String> startValues = AdventUtils.getStringInput(8);

			List<Tuple<String, Integer>> code = startValues.stream().map(l -> {
				String[] splitted = l.split(" ");
				return Tuple.of(splitted[0], Integer.valueOf(splitted[1]));
			}).collect(Collectors.toList());

			Long result = null;
			for (Tuple<String, Integer> line : code) {
				if (line.getLeft().equals("acc")) {
					continue;
				}

				if (line.getLeft().equals("jmp")) {
					line.setLeft("nop");
				} else {
					line.setLeft("jmp");
				}

				result = run(code);

				if (line.getLeft().equals("nop")) {
					line.setLeft("jmp");
				} else {
					line.setLeft("nop");
				}

				if (result != null) {
					break;
				}
			}

			AdventUtils.publishResult(8, 1, result);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static Long run(List<Tuple<String, Integer>> code) {
		long accumulator = 0;
		int pointer = 0;

		Set<Integer> visitedPointer = new HashSet<>();

		do {
			if (visitedPointer.contains(pointer)) {
				return null;
			}
			visitedPointer.add(pointer);

			Tuple<String, Integer> line = code.get(pointer);

			switch (line.getLeft()) {

			case "acc":
				accumulator += line.getRight();
				pointer++;
				break;
			case "jmp":
				pointer += line.getRight();
				break;
			case "nop":
				pointer++;
				break;
			}

		} while (pointer < code.size());

		return accumulator;

	}
}
