package day13;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

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

			ExecutorService executor = Executors.newFixedThreadPool(6);

			List<Callable<Tuple<Long, Long>>> tasks = new ArrayList<>();
			for (int i = 0; i < busses.size(); i = i + 2) {
				final int bus0 = i;
				final int bus1 = i + 1;

				tasks.add(() -> {
					if (bus1 < busses.size()) {
						Tuple<Long, Long> result = reduce(busses.get(bus0), busses.get(bus1));
						return result;
					} else {
						return busses.get(bus0);
					}
				});

			}

			List<Tuple<Long, Long>> result = executor.invokeAll(tasks).stream().map(f -> {
				try {
					return f.get();
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
					System.exit(-1);
					return null;
				}
			}).sorted(Comparator.comparing(Tuple::getLeft)).collect(Collectors.toList());
			executor.shutdown();

			List<Long> currentValues = result.stream().map(Tuple::getRight).collect(Collectors.toList());

			boolean matches = false;
			while (!matches) {

				matches = true;
				currentValues.set(0, currentValues.get(0) + result.get(0).getLeft());

				for (int i = 1; i < result.size(); i++) {

					long prevValue = currentValues.get(i - 1);
					long currentValue = currentValues.get(i);
					long increment = result.get(i).getLeft();

					while (prevValue > currentValue) {
						currentValue += increment;
					}

					currentValues.set(i, currentValue);

					if (currentValue != prevValue) {
						matches = false;
						break;
					}
				}

			}

			AdventUtils.publishResult(13, 2, currentValues.get(0));

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

		return Tuple.of(loop, turns.get(0));
	}
}
