package day11;

import java.io.IOException;
import java.util.List;

import util.AdventUtils;

public class Day11Task1Main {

	public static void main(String[] args) {
		try {
			List<String> startValues = AdventUtils.getStringInput(11);

			char[][] map = createMap(startValues);

			boolean seatChanged = false;
			int seats = 0;

			do {
				seatChanged = false;
				seats = 0;

				char[][] roundMap = new char[map.length][map[0].length];

				for (int y = 0; y < map.length; y++) {
					for (int x = 0; x < map[y].length; x++) {

						int count = countOccupied(map, y, x);

						if (map[y][x] == '#') {
							if (count >= 4) {
								roundMap[y][x] = 'L';
								seatChanged = true;
							} else {
								roundMap[y][x] = '#';
								seats++;
							}
						} else if (map[y][x] == 'L') {
							if (count == 0) {
								roundMap[y][x] = '#';
								seatChanged = true;
								seats++;
							} else {
								roundMap[y][x] = 'L';
							}
						} else {
							roundMap[y][x] = '.';
						}
					}
				}

				map = roundMap;

			} while (seatChanged);

			AdventUtils.printGrid(11, 1, map, false);
			AdventUtils.publishResult(11, 1, seats);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static int countOccupied(char[][] map, int y, int x) {
		int count = 0;

		if (y > 0) {
			if (x > 0 && map[y - 1][x - 1] == '#') {
				count++;
			}
			if (map[y - 1][x] == '#') {
				count++;
			}
			if (x < map[y].length - 1 && map[y - 1][x + 1] == '#') {
				count++;
			}
		}

		if (x > 0 && map[y][x - 1] == '#') {
			count++;
		}
		if (x < map[y].length - 1 && map[y][x + 1] == '#') {
			count++;
		}

		if (y < map.length - 1) {
			if (x > 0 && map[y + 1][x - 1] == '#') {
				count++;
			}
			if (map[y + 1][x] == '#') {
				count++;
			}
			if (x < map[y].length - 1 && map[y + 1][x + 1] == '#') {
				count++;
			}
		}

		return count;
	}

	private static char[][] createMap(List<String> startValues) {
		char[][] map = new char[startValues.size()][startValues.get(0).length()];

		for (int y = 0; y < startValues.size(); y++) {

			String line = startValues.get(y);

			for (int x = 0; x < line.length(); x++) {

				map[y][x] = line.charAt(x);

			}
		}

		return map;
	}
}
