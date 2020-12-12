package day10;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import util.AdventUtils;

public class Day10Task1Main {

	public static void main(String[] args) {
		try {
			List<Integer> startValues = AdventUtils.getIntegerInput(10);
			List<Integer> adapter = startValues.stream().sorted().collect(Collectors.toList());

			int count1 = 0;
			int count3 = 0;

			for (int i = 0; i < adapter.size(); i++) {

				int last = 0;

				if (i > 0) {
					last = adapter.get(i - 1);
				}

				if (adapter.get(i) - last == 1) {
					count1++;
				} else {
					count3++;
				}

			}

			count3++;

			AdventUtils.publishResult(10, 1, count1 * count3);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
