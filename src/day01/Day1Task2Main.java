package day01;

import java.io.IOException;
import java.util.List;

import util.AdventUtils;

public class Day1Task2Main {

	public static void main(String[] args) {
		try {
			List<Integer> startValues = AdventUtils.getIntegerInput(1);

			for (Integer outer : startValues) {
				for (Integer middle : startValues) {
					for (Integer inner : startValues) {

						if (outer + middle + inner == 2020) {
							AdventUtils.publishResult(1, 2, outer * middle * inner);
							return;
						}
					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
