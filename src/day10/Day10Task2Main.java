package day10;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import util.AdventUtils;

public class Day10Task2Main {

	public static void main(String[] args) {
		try {
			List<Integer> startValues = AdventUtils.getIntegerInput(10);
			List<Integer> adapter = startValues.stream().sorted().collect(Collectors.toList());

			long count = 1;
			long splits = 0;

			for (int i = 1; i < adapter.size(); i++) {

				if (adapter.get(i) - adapter.get(i - 1) == 1) {
					splits++;
					if (count == 1) {
						count = 2;
					} else {
						count = count * 2 - count / splits;
					}
				}

			}

			AdventUtils.publishResult(10, 2, count);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
