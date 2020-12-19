package day19;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import util.AdventUtils;

public class Day19Task2Main {

	private static Map<String, String> rawRules = new HashMap<>();
	private static Map<String, String> rules = new HashMap<>();
	private static List<String> messages = new ArrayList<>();
	private static int maxMsgLength = 0;

	public static void main(String[] args) {
		try {
			readData();

			String regex = resolve0();
			AdventUtils.publishExtra(19, 2, regex, "regex");

			AdventUtils.publishResult(19, 2, "");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String resolve0() {
		return "^" + resolve8() + resolve11() + "$";
	}

	private static String resolve11() {
		String solutions42 = resolveSolution("42");
		String solutions31 = resolveSolution("31");
		return "(" + solutions42 + "(?1)?" + solutions31 + ")";
	}

	private static String resolve8() {
		return resolveSolution("8") + "+";
	}

	private static String resolveSolution(String ruleId) {

		String correctSolution = rules.get(ruleId);

		if (correctSolution != null) {
			return correctSolution;
		}

		String rawRule = rawRules.get(ruleId);
		if (rawRule == null) {
			return ruleId;
		}

		String[] orSplit = rawRule.split(" \\| ");
		StringBuilder sb = new StringBuilder();

		if (orSplit.length > 1) {
			sb.append("(?>");
		}

		for (int i = 0; i < orSplit.length; i++) {

			for (String subRules : orSplit[i].split(" ")) {
				sb.append(resolveSolution(subRules));
			}

			if (i + 1 < orSplit.length) {
				sb.append("|");
			}

		}
		if (orSplit.length > 1) {
			sb.append(")");
		}

		correctSolution = sb.toString();

		rules.put(ruleId, correctSolution);
		return correctSolution;

	}

	private static void readData() throws IOException {
		List<String> startValues = AdventUtils.getStringInput(19);

		Iterator<String> it = startValues.iterator();

		String line;
		while (!(line = it.next()).isEmpty()) {
			String[] split = line.replace("\"", "").split(": ");
			rawRules.put(split[0], split[1]);
		}

		while (it.hasNext()) {
			line = it.next();
			messages.add(line);

			if (line.length() > maxMsgLength) {
				maxMsgLength = line.length();
			}
		}
	}
}
