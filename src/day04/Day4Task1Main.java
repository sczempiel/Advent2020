package day04;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import util.AdventUtils;

public class Day4Task1Main {

	private static final String[] REQS = new String[] { "byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid" };

	public static void main(String[] args) {
		try {
			List<String> startValues = AdventUtils.getStringInput(4);

			int valid = 0;

			List<String> found = new ArrayList<String>();

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

					found.add(splitProp[0]);
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

	private static boolean passValid(List<String> found) {
		for (String req : REQS) {
			if (!found.contains(req)) {
				return false;
			}
		}

		return true;
	}
}
