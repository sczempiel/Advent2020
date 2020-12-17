package day17;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import util.AdventUtils;

// cycle 1: 29
public class Day17Task2Main {

	private static List<List<List<List<Character>>>> spatial;

	public static void main(String[] args) {
		try {
			buildInitialCube();

			int totalActive = 0;

			for (int i = 0; i < 6; i++) {
				totalActive = 0;

				List<List<List<List<Character>>>> tmpSpatial = new ArrayList<>();

				for (int w = 0; w < spatial.size() + 2; w++) {

					List<List<List<Character>>> cube = new ArrayList<>();
					tmpSpatial.add(cube);

					for (int z = 0; z < spatial.get(0).size() + 2; z++) {

						List<List<Character>> dimension = new ArrayList<>();
						cube.add(dimension);

						for (int y = 0; y < spatial.get(0).get(0).size() + 2; y++) {

							List<Character> row = new ArrayList<>();
							dimension.add(row);

							for (int x = 0; x < spatial.get(0).get(0).get(0).size() + 2; x++) {

								char currVal = getCurrValue(x - 1, y - 1, z - 1, w - 1);
								int neighbours = countNeighbours(x - 1, y - 1, z - 1, w - 1);

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
				}

				spatial = tmpSpatial;

			}

			AdventUtils.publishResult(17, 2, totalActive);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static char getCurrValue(int x, int y, int z, int w) {

		if (w < 0 || w >= spatial.size()) {
			return '.';
		}

		List<List<List<Character>>> cube = spatial.get(w);

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

	private static int countNeighbours(int pX, int pY, int pZ, int pW) {
		int neighbours = 0;

		for (int w = pW - 1; w <= pW + 1; w++) {

			if (w < 0 || w >= spatial.size()) {
				continue;
			}

			List<List<List<Character>>> cube = spatial.get(w);

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

						if (x == pX && y == pY && z == pZ && w == pW) {
							continue;
						}

						if (row.get(x) == '#') {
							neighbours++;
						}

					}
				}
			}
		}

		return neighbours;
	}

	private static void buildInitialCube() throws IOException {
		List<String> startValues = AdventUtils.getStringInput(17);

		spatial = new ArrayList<>();

		List<List<List<Character>>> cube = new ArrayList<>();
		spatial.add(cube);

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
