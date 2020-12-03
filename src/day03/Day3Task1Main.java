package day03;

import java.io.IOException;
import java.util.List;

import util.AdventUtils;

public class Day3Task1Main {

	public static void main(String[] args) {
		try {
			List<String> lines = AdventUtils.getStringInput(3);

			int lineLength = lines.get(0).length();

			int x = 0;
			int hits = 0;

			for (int y = 0; y < lines.size(); y++) {

				if (lines.get(y).charAt(x) == '#') {
					hits++;
				}

				x = (x + 3) % lineLength;
			}
			
			AdventUtils.publishResult(3, 1, hits);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
