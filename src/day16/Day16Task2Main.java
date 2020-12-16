package day16;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import util.AdventUtils;

public class Day16Task2Main {

	private static Map<Integer, List<String>> fieldPositions = new HashMap<>();
	private static Map<String, Field> fields = new HashMap<>();
	private static List<Integer> myTicket;
	private static Set<List<Integer>> nearbyTickets = new HashSet<>();

	public static void main(String[] args) {
		try {
			parse();
			filterInvalid();
			determinateFields();

			long result = 1;

			for (int i = 0; i < myTicket.size(); i++) {
				if (fieldPositions.get(i).get(0).startsWith("departure")) {
					result *= myTicket.get(i);
				}
			}

			AdventUtils.publishResult(16, 1, result);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void determinateFields() {

		for (int i = 0; i < myTicket.size(); i++) {
			fieldPositions.put(i, new ArrayList<>(fields.keySet()));
		}

		for (List<Integer> ticket : nearbyTickets) {
			for (int i = 0; i < ticket.size(); i++) {
				for (Field field : fields.values()) {
					if (!field.matches(ticket.get(i))) {
						fieldPositions.get(i).remove(field.getName());
					}
				}
			}
		}

		while (fieldPositions.values().stream().flatMap(s -> s.stream()).count() > myTicket.size()) {
			for (int i = 0; i < myTicket.size(); i++) {
				List<String> fields = fieldPositions.get(i);

				if (fields.size() != 1) {
					continue;
				}

				for (int j = 0; j < myTicket.size(); j++) {

					if (i == j) {
						continue;
					}

					fieldPositions.get(j).remove(fields.get(0));
				}
			}
		}

	}

	private static void filterInvalid() {
		for (Iterator<List<Integer>> ticket = nearbyTickets.iterator(); ticket.hasNext();) {
			for (Integer value : ticket.next()) {

				boolean match = false;

				for (Field field : fields.values()) {
					if (field.matches(value)) {
						match = true;
						break;
					}
				}

				if (!match) {
					ticket.remove();
				}

			}
		}
	}

	private static void parse() throws IOException {
		List<String> startValues = AdventUtils.getStringInput(16);

		Iterator<String> it = startValues.iterator();

		String line;

		while (!(line = it.next()).isEmpty()) {
			Field field = Field.fromLine(line);
			fields.put(field.getName(), field);
		}

		// skip headline
		it.next();

		myTicket = Arrays.asList(it.next().split(",")).stream().map(Integer::valueOf).collect(Collectors.toList());

		// skip empty line + headline
		it.next();
		it.next();

		while (it.hasNext()) {
			nearbyTickets.add(
					Arrays.asList(it.next().split(",")).stream().map(Integer::valueOf).collect(Collectors.toList()));
		}
	}
}
