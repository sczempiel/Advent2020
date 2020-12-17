package day13;

import java.util.ArrayList;
import java.util.List;

import util.AdventUtils;
import util.Tuple;

public class Day13Task2Main {

	public static void main(String[] args) {
		try {
			String[] startValues = AdventUtils.getStringInput(13).get(1).split(",");

			List<Tuple<Long, Long>> busses = new ArrayList<>();

			for (int i = 0; i < startValues.length; i++) {
				if (startValues[i].equals("x")) {
					continue;
				}

				busses.add(Tuple.of(Long.valueOf(startValues[i]), new Long(i)));
			}

			while (busses.size() > 1) {
				List<Tuple<Long, Long>> result = new ArrayList<>();

				for (int i = 0; i < busses.size(); i = i + 2) {
					if (i + 1 < busses.size()) {
						result.add(reduce(busses.get(i), busses.get(i + 1)));
					} else {
						result.add(reduce(busses.get(i), busses.get(i - 1)));
					}

				}

				busses = result;
			}

			AdventUtils.publishResult(13, 2, busses.get(0).getRight() * -1);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static Tuple<Long, Long> reduce(Tuple<Long, Long> bus0, Tuple<Long, Long> bus1s) {

		long currentBus0 = bus0.getRight() * -1;
		long currentBus1 = bus1s.getRight() * -1;

		List<Long> turns = new ArrayList<>();

		long loop = 0l;
		while (loop == 0l) {

			currentBus0 += bus0.getLeft();

			while (currentBus0 > currentBus1) {
				currentBus1 += bus1s.getLeft();
			}

			if (currentBus0 == currentBus1) {
				turns.add(currentBus0);

				if (turns.size() > 2) {

					loop = turns.get(turns.size() - 1) - turns.get(turns.size() - 2);

					if (turns.get(turns.size() - 2) - turns.get(turns.size() - 3) != loop) {
						loop = 0;
					}
				}

			}

		}

		// return the intervall with the offset
		return Tuple.of(loop, turns.get(0) * -1);
	}
}
