package day09;

import java.io.IOException;
import java.util.List;

import util.AdventUtils;

public class Day9Task2Main {

	private static final long NUMBER = 542529149;
	// private static final long NUMBER = 127;

	public static void main(String[] args) {
		try {
			List<Long> startValues = AdventUtils.getLongInput(9);

			int firstNum = 0;
			int lastNum = 0;
			long sum = 0;

			for (lastNum = 0; lastNum < startValues.size(); lastNum++) {

				sum += startValues.get(lastNum);

				while (sum > NUMBER) {

					sum -= startValues.get(firstNum);
					firstNum++;

				}

				if (sum == NUMBER) {
					break;
				}
			}

			Long min = null;
			Long max = null;

			for (int i = firstNum; i <= lastNum; i++) {

				long num = startValues.get(i);

				if (min == null || num < min) {
					min = num;
				}
				if (max == null || num > max) {
					max = num;
				}
			}

			AdventUtils.publishResult(9, 2, min + max);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
