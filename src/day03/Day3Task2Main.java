package day03;

import java.io.IOException;
import java.util.List;

import util.AdventUtils;

public class Day3Task2Main {

	public static void main(String[] args) {
		try {
			int[] xGrowth = new int[] { 1, 3, 5, 7, 1 };
			int[] yGrowth = new int[] { 1, 1, 1, 1, 2 };

			List<String> lines = AdventUtils.getStringInput(3);

			int lineLength = lines.get(0).length();

			long totalHits = 1;

			for (int g = 0; g < xGrowth.length; g++) {

				int x = 0;
				int hits = 0;

				for (int y = 0; y < lines.size(); y = y + yGrowth[g]) {

					if (lines.get(y).charAt(x) == '#') {
						hits++;
					}

					x = (x + xGrowth[g]) % lineLength;
				}

				totalHits *= hits;
			}

			AdventUtils.publishResult(3, 2, totalHits);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
