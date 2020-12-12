package day12;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import util.AdventUtils;
import util.Tuple;

// 19728 l
// 13994 l
public class Day12Task2Main {

	private static char direction = 'E';
	private static int y = 0;
	private static int x = 0;

	// waypoint pos relative to ship
	private static int wY = 1;
	private static int wX = 10;

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
					moveWaypoint(instruction.getLeft(), instruction.getRight());
					break;
				case 'L':
					turnLeft(instruction.getRight());
					break;
				case 'R':
					turnRight(instruction.getRight());
					break;
				case 'F':
					move(instruction.getRight());
					break;
				}

			}

			AdventUtils.publishResult(12, 2, Math.abs(y) + Math.abs(x));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void turnRight(int degree) {

		int degreeLeft = degree;

		while (degreeLeft > 0) {
			int puffer = wX;
			switch (direction) {
			case 'N':
				direction = 'E';
				wX = wY;
				wY = puffer * -1;
				break;
			case 'E':
				direction = 'S';
				wX = wY;
				wY = puffer * -1;
				break;
			case 'S':
				direction = 'W';
				wX = wY;
				wY = puffer * -1;
				break;
			case 'W':
				direction = 'N';
				wX = wY;
				wY = puffer * -1;
				break;
			}

			degreeLeft -= 90;
		}

	}

	private static void turnLeft(int degree) {

		int degreeLeft = degree;

		while (degreeLeft > 0) {
			int puffer = wX;
			switch (direction) {
			case 'N':
				direction = 'W';
				wX = wY * -1;
				wY = puffer;
				break;
			case 'W':
				direction = 'S';
				wX = wY * -1;
				wY = puffer;
				break;
			case 'S':
				direction = 'E';
				wX = wY * -1;
				wY = puffer;
				break;
			case 'E':
				direction = 'N';
				wX = wY * -1;
				wY = puffer;
				break;
			}

			degreeLeft -= 90;
		}

	}

	private static void moveWaypoint(char direction, int distance) {
		switch (direction) {
		case 'N':
			wY += distance;
			break;
		case 'S':
			wY -= distance;
			break;
		case 'E':
			wX += distance;
			break;
		case 'W':
			wX -= distance;
			break;
		}
	}

	private static void move(int times) {
		for (int i = 0; i < times; i++) {
			x += wX;
			y += wY;
		}
	}

}
