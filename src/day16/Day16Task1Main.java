package day16;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import util.AdventUtils;

public class Day16Task1Main {

	private static Set<Field> fields = new HashSet<>();
	private static Set<Integer> myTicket;
	private static Set<Set<Integer>> nearbyTickets = new HashSet<>();

	public static void main(String[] args) {
		try {
			parse();

			int failSum = 0;

			for (Set<Integer> ticket : nearbyTickets) {
				for (Integer value : ticket) {

					boolean match = false;

					for (Field field : fields) {
						if (field.matches(value)) {
							match = true;
							break;
						}
					}

					if (!match) {
						failSum += value;
					}

				}
			}

			AdventUtils.publishResult(16, 1, failSum);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void parse() throws IOException {
		List<String> startValues = AdventUtils.getStringInput(16);

		Iterator<String> it = startValues.iterator();

		String line;

		while (!(line = it.next()).isEmpty()) {
			fields.add(Field.fromLine(line));
		}

		// skip headline
		it.next();

		myTicket = Arrays.asList(it.next().split(",")).stream().map(Integer::valueOf).collect(Collectors.toSet());

		// skip empty line + headline
		it.next();
		it.next();

		while (it.hasNext()) {
			nearbyTickets.add(
					Arrays.asList(it.next().split(",")).stream().map(Integer::valueOf).collect(Collectors.toSet()));
		}
	}
}
