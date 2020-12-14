package day13;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import util.AdventUtils;

public class Day13Task1Main {

	public static void main(String[] args) {
		try {
			List<String> startValues = AdventUtils.getStringInput(13);

			int earliestTime = Integer.valueOf(startValues.get(0));
			List<Integer> busses = Arrays.asList(startValues.get(1).split(",")).stream().filter(s -> !s.equals("x"))
					.map(Integer::valueOf).collect(Collectors.toList());

			Integer smallestBus = null;
			Integer smallestTime = null;

			for (Integer bus : busses) {

				int time = 0;

				do {
					time += bus;
				} while (time < earliestTime);

				if (smallestTime == null || smallestTime > time) {
					smallestBus = bus;
					smallestTime = time;
				}

			}

			AdventUtils.publishResult(13, 1, (smallestTime - earliestTime) * smallestBus);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
