package day14;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import util.AdventUtils;

public class Day14Task1Main {

	private static Pattern PAT = Pattern.compile(".*\\[(\\d+)\\].*?(\\d+)");

	public static void main(String[] args) {
		try {
			List<String> startValues = AdventUtils.getStringInput(14);

			Map<Integer, Long> memory = new HashMap<>();
			String mask = null;

			for (String line : startValues) {
				if (line.startsWith("mask")) {
					mask = line.split(" = ")[1];
					continue;
				}

				Matcher matcher = PAT.matcher(line);
				matcher.matches();

				int adress = Integer.valueOf(matcher.group(1));
				int num = Integer.valueOf(matcher.group(2));

				String binaryString = Integer.toBinaryString(num);

				while (binaryString.length() < mask.length()) {
					binaryString = "0" + binaryString;
				}

				char[] charArray = binaryString.toCharArray();

				for (int i = 0; i < binaryString.length(); i++) {
					if (mask.charAt(i) == 'X') {
						continue;
					}

					charArray[i] = mask.charAt(i);
				}

				binaryString = String.copyValueOf(charArray);

				Long numToWrite = Long.parseLong(binaryString, 2);

				memory.put(adress, numToWrite);

			}

			AdventUtils.publishResult(14, 1, memory.values().stream().mapToLong(i -> i).sum());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
