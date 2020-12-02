package day02;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import util.AdventUtils;

public class Day2Task2Main {

	public static void main(String[] args) {

		try {
			List<String> startValues = AdventUtils.getStringInput(2);
			Pattern pat = Pattern.compile("^(\\d+)-(\\d+) (\\w): (\\w+)$");
			int valid = 0;

			for (String line : startValues) {
				Matcher matcher = pat.matcher(line);

				matcher.matches();

				Integer pos1 = Integer.valueOf(matcher.group(1)) - 1;
				Integer pos2 = Integer.valueOf(matcher.group(2)) - 1;
				char toFind = matcher.group(3).charAt(0);
				char[] toTest = matcher.group(4).toCharArray();

				if ((toTest[pos1] == toFind && toTest[pos2] != toFind)
						|| (toTest[pos1] != toFind && toTest[pos2] == toFind)) {
					valid++;
				}
			}

			AdventUtils.publishResult(2, 2, valid);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
