package day12;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import util.AdventUtils;
import util.Tuple;

public class Day12Task1Main {

	private static char direction = 'E';
	private static int y = 0;
	private static int x = 0;

	public static void main(String[] args) {
		try {
			Pattern pattern = Pattern.compile("(\\w)(\\d+)");

			List<String> startValues = AdventUtils.getStringInput(12);
			List<Tuple<Character, Integer>> instructions = startValues.stream().map(l -> {
				Matcher matcher = pattern.matcher(l);
				matcher.matches();

				return Tuple.of(matcher.group(1).charAt(0), Integer.valueOf(matcher.group(2)));
			}).collect(Collectors.toList());

			for (Tuple<Character, Integer> instruction : instructions) {

				switch (instruction.getLeft()) {
				case 'N':
				case 'S':
				case 'E':
				case 'W':
					move(instruction.getLeft(), instruction.getRight());
					break;
				case 'L':
					turnLeft(instruction.getRight());
					break;
				case 'R':
					turnRight(instruction.getRight());
					break;
				case 'F':
					move(direction, instruction.getRight());
					break;
				}

			}

			AdventUtils.publishResult(12, 1, Math.abs(y) + Math.abs(x));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void turnRight(int degree) {

		int degreeLeft = degree;

		while (degreeLeft > 0) {
			switch (direction) {
			case 'N':
				direction = 'E';
				break;
			case 'E':
				direction = 'S';
				break;
			case 'S':
				direction = 'W';
				break;
			case 'W':
				direction = 'N';
				break;
			}

			degreeLeft -= 90;
		}

	}

	private static void turnLeft(int degree) {

		int degreeLeft = degree;

		while (degreeLeft > 0) {
			switch (direction) {
			case 'N':
				direction = 'W';
				break;
			case 'W':
				direction = 'S';
				break;
			case 'S':
				direction = 'E';
				break;
			case 'E':
				direction = 'N';
				break;
			}

			degreeLeft -= 90;
		}

	}

	private static void move(char direction, int distance) {
		switch (direction) {
		case 'N':
			y += distance;
			break;
		case 'S':
			y -= distance;
			break;
		case 'E':
			x += distance;
			break;
		case 'W':
			x -= distance;
			break;
		}
	}

}
