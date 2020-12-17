package day17;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import util.AdventUtils;

public class Day17Task1Main {

	private static List<List<List<Character>>> cube;

	public static void main(String[] args) {
		try {
			buildInitialCube();

			int totalActive = 0;

			for (int i = 0; i < 6; i++) {
				totalActive = 0;

				List<List<List<Character>>> tmpCube = new ArrayList<>();

				for (int z = 0; z < cube.size() + 2; z++) {

					List<List<Character>> dimension = new ArrayList<>();
					tmpCube.add(dimension);

					for (int y = 0; y < cube.get(0).size() + 2; y++) {

						List<Character> row = new ArrayList<>();
						dimension.add(row);

						for (int x = 0; x < cube.get(0).get(0).size() + 2; x++) {

							char currVal = getCurrValue(x - 1, y - 1, z - 1);
							int neighbours = countNeighbours(x - 1, y - 1, z - 1);

							if ((currVal == '#' && (neighbours == 2 || neighbours == 3))
									|| (currVal == '.' && neighbours == 3)) {
								row.add('#');
								totalActive++;
							} else {
								row.add('.');
							}
						}
					}
				}

				cube = tmpCube;

			}

			AdventUtils.publishResult(17, 1, totalActive);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static char getCurrValue(int x, int y, int z) {

		if (z < 0 || z >= cube.size()) {
			return '.';
		}

		List<List<Character>> dimension = cube.get(z);

		if (y < 0 || y >= dimension.size()) {
			return '.';
		}

		List<Character> row = dimension.get(y);

		if (x < 0 || x >= row.size()) {
			return '.';
		}

		return row.get(x);
	}

	private static int countNeighbours(int pX, int pY, int pZ) {
		int neighbours = 0;

		for (int z = pZ - 1; z <= pZ + 1; z++) {

			if (z < 0 || z >= cube.size()) {
				continue;
			}

			List<List<Character>> dimension = cube.get(z);

			for (int y = pY - 1; y <= pY + 1; y++) {

				if (y < 0 || y >= dimension.size()) {
					continue;
				}

				List<Character> row = dimension.get(y);

				for (int x = pX - 1; x <= pX + 1; x++) {

					if (x < 0 || x >= row.size()) {
						continue;
					}

					if (x == pX && y == pY && z == pZ) {
						continue;
					}

					if (row.get(x) == '#') {
						neighbours++;
					}

				}
			}
		}

		return neighbours;
	}

	private static void buildInitialCube() throws IOException {
		List<String> startValues = AdventUtils.getStringInput(17);

		cube = new ArrayList<>();
		List<List<Character>> dimension = new ArrayList<>();
		cube.add(dimension);

		for (int y = 0; y < startValues.size(); y++) {
			char[] line = startValues.get(y).toCharArray();

			List<Character> row = new ArrayList<>();
			dimension.add(row);

			for (int x = 0; x < line.length; x++) {
				row.add(line[x]);
			}
		}

	}
}
