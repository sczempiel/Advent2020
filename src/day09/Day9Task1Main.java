package day09;

import java.io.IOException;
import java.util.List;

import util.AdventUtils;

public class Day9Task1Main {

	private static int NUMBERS_TO_CHECK = 25;

	public static void main(String[] args) {
		try {
			List<Long> startValues = AdventUtils.getLongInput(9);

			long number = 0;

			for (int i = NUMBERS_TO_CHECK; i < startValues.size(); i++) {

				number = startValues.get(i);
				boolean valid = false;

				numFind: for (int n1 = i - NUMBERS_TO_CHECK; n1 < i; n1++) {
					for (int n2 = i - NUMBERS_TO_CHECK; n2 < i; n2++) {
						if (n1 == n2) {
							continue;
						}

						if (startValues.get(n1) + startValues.get(n2) == number) {
							valid = true;
							break numFind;
						}

					}
				}

				if (!valid) {
					break;
				}

			}

			AdventUtils.publishResult(9, 1, number);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
