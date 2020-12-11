package day11;

import java.io.IOException;
import java.util.List;

import util.AdventUtils;

public class Day11Task2Main {

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
							if (count >= 5) {
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

			AdventUtils.printGrid(11, 2, map, false);
			AdventUtils.publishResult(11, 2, seats);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static boolean occupiedInSight(char[][] map, int y, int x, int yGrow, int xGrow) {

		int nY = y + yGrow;

		if (nY < 0 || nY >= map.length) {
			return false;
		}

		int nX = x + xGrow;

		if (nX < 0 || nX >= map[nY].length) {
			return false;
		}

		if (map[nY][nX] == '.') {
			return occupiedInSight(map, nY, nX, yGrow, xGrow);
		}

		return map[nY][nX] == '#';
	}

	private static int countOccupied(char[][] map, int y, int x) {
		int count = 0;

		// top
		if (occupiedInSight(map, y, x, -1, -1)) {
			count++;
		}
		if (occupiedInSight(map, y, x, -1, 0)) {
			count++;
		}
		if (occupiedInSight(map, y, x, -1, 1)) {
			count++;
		}

		// same
		if (occupiedInSight(map, y, x, 0, -1)) {
			count++;
		}
		if (occupiedInSight(map, y, x, 0, 1)) {
			count++;
		}

		// bot
		if (occupiedInSight(map, y, x, 1, -1)) {
			count++;
		}
		if (occupiedInSight(map, y, x, 1, 0)) {
			count++;
		}
		if (occupiedInSight(map, y, x, 1, 1)) {
			count++;
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
