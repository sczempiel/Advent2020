package day14;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import util.AdventUtils;

public class Day14Task2Main {

	private static Pattern PAT = Pattern.compile(".*\\[(\\d+)\\].*?(\\d+)");

	public static void main(String[] args) {
		try {
			List<String> startValues = AdventUtils.getStringInput(14);

			Map<Long, Long> memory = new HashMap<>();
			String mask = null;

			for (String line : startValues) {
				if (line.startsWith("mask")) {
					mask = line.split(" = ")[1];
					continue;
				}

				Matcher matcher = PAT.matcher(line);
				matcher.matches();

				long rawAdress = Long.valueOf(matcher.group(1));
				long num = Long.valueOf(matcher.group(2));

				String binaryString = Long.toBinaryString(rawAdress);

				while (binaryString.length() < mask.length()) {
					binaryString = "0" + binaryString;
				}

				char[] charArray = binaryString.toCharArray();

				Set<char[]> arrays = new HashSet<>();
				arrays.add(charArray);

				for (int i = 0; i < binaryString.length(); i++) {
					if (mask.charAt(i) == '0') {
						continue;
					}

					if (mask.charAt(i) == '1') {
						for (char[] a : arrays) {
							a[i] = '1';
						}
						continue;
					}

					Set<char[]> tmp = new HashSet<>();

					for (char[] a : arrays) {
						char[] b = Arrays.copyOf(a, a.length);
						b[i] = '0';
						tmp.add(b);

						char[] c = Arrays.copyOf(a, a.length);
						c[i] = '1';
						tmp.add(c);
					}

					arrays = tmp;
				}

				for (char[] a : arrays) {
					String binaryAdress = String.copyValueOf(a);

					Long adress = Long.parseLong(binaryAdress, 2);

					memory.put(adress, num);
				}

			}

			AdventUtils.publishResult(14, 2, memory.values().stream().mapToLong(i -> i).sum());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
