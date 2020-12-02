package day02;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import util.AdventUtils;

public class Day2Task1Main {

	public static void main(String[] args) {
		try {
			List<String> startValues = AdventUtils.getStringInput(2);
			Pattern pat = Pattern.compile("^(\\d+)-(\\d+) (\\w): (\\w+)$");
			int valid = 0;
			
			for (String line : startValues) {
				Matcher matcher = pat.matcher(line);
				matcher.matches();
				Integer min = Integer.valueOf(matcher.group(1));
				Integer max = Integer.valueOf(matcher.group(2));
				char toFind = matcher.group(3).charAt(0);
				String toTest = matcher.group(4);
				
				int matches = countMatches(toTest, toFind);
				
				if(matches >= min && matches <= max) {
					valid++;
				}
			}
			
			AdventUtils.publishResult(2, 1, valid);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static int countMatches(String test, char toFind) {

		int matches = 0;

		for (char c : test.toCharArray()) {
			if (c == toFind) {
				matches++;
			}
		}
		
		return matches;
	}
}
