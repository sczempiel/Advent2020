package day04;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import util.AdventUtils;
import util.Tuple;

public class Day4Task2Main {

	private static final String[] REQS = new String[] { "byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid" };

	public static void main(String[] args) {
		try {
			List<String> startValues = AdventUtils.getStringInput(4);

			int valid = 0;

			List<Tuple<String, String>> found = new ArrayList<>();

			for (String line : startValues) {

				if (line.isEmpty()) {

					if (passValid(found)) {
						valid++;
					}

					found = new ArrayList<>();
					continue;

				}

				String[] splitted = line.split(" ");

				for (String prop : splitted) {
					String[] splitProp = prop.split(":");

					found.add(Tuple.of(splitProp[0], splitProp[1]));
				}

			}

			if (passValid(found)) {
				valid++;
			}

			AdventUtils.publishResult(4, 1, valid);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static boolean passValid(List<Tuple<String, String>> found) {
		for (String req : REQS) {
			List<String> keys = found.stream().map(Tuple::getLeft).collect(Collectors.toList());
			if (!keys.contains(req)) {
				return false;
			}
		}

		int year = 0;
		Pattern hairColor = Pattern.compile("#[0-9a-f]{6}");
		Pattern passId = Pattern.compile("\\d{9}");
		Pattern heightPat = Pattern.compile("(\\d+)((in)|(cm))");
		Set<String> eyeColor = new HashSet<>(
				Arrays.asList(new String[] { "amb", "blu", "brn", "gry", "grn", "hzl", "oth" }));

		for (Tuple<String, String> prop : found) {

			switch (prop.getLeft()) {
			case "byr":
				year = Integer.valueOf(prop.getRight());
				if (year < 1920 || year > 2002) {
					return false;
				}
				break;
			case "iyr":
				year = Integer.valueOf(prop.getRight());
				if (year < 2010 || year > 2020) {
					return false;
				}
				break;
			case "eyr":
				year = Integer.valueOf(prop.getRight());
				if (year < 2020 || year > 2030) {
					return false;
				}
				break;
			case "hgt":
				Matcher heightMatch = heightPat.matcher(prop.getRight());
				if (!heightMatch.matches()) {
					return false;
				}

				int height = Integer.valueOf(heightMatch.group(1));
				String unit = heightMatch.group(2);
				if ("cm".equals(unit)) {
					if (height < 150 || height > 193) {
						return false;
					}
				} else if ("in".equals(unit)) {
					if (height < 59 || height > 76) {
						return false;
					}
				} else {
					return false;
				}
				break;
			case "hcl":
				Matcher hairMatch = hairColor.matcher(prop.getRight());
				if (!hairMatch.matches()) {
					return false;
				}
				break;
			case "ecl":
				if (!eyeColor.contains(prop.getRight())) {
					return false;
				}
				break;
			case "pid":
				Matcher passMatch = passId.matcher(prop.getRight());
				if (!passMatch.matches()) {
					return false;
				}
				break;
			}
		}

		return true;
	}
}
