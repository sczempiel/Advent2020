package day18;

import java.io.IOException;
import java.util.List;

import util.AdventUtils;

public class Day18Task1Main {

	public static void main(String[] args) {
		try {
			List<String> startValues = AdventUtils.getStringInput(18);

			long result = 0;

			for (String line : startValues) {
				String spaceLess = line.replaceAll(" ", "");
				result += doMath(spaceLess);
			}
			AdventUtils.publishResult(18, 1, result);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static long doMath(String line) {
		Long num1 = null;
		Character operator = null;
		Long num2 = null;

		int i = 0;
		while (i < line.length()) {
			char c = line.charAt(i);

			Long num = null;

			if (c == '(') {
				int endPara = getParanthesisEnd(line, i);
				String sub = line.substring(i + 1, endPara);

				num = doMath(sub);
				i = endPara + 1;
			} else if (c == '+' || c == '*') {
				operator = c;
				i++;
			} else {
				String rawNum = "";
				while (c != '+' && c != '*' && c != '(') {
					rawNum += c;
					i++;

					if (i >= line.length()) {
						break;
					}

					c = line.charAt(i);
				}

				num = Long.valueOf(rawNum);

			}

			if (num != null) {
				if (num1 == null) {
					num1 = num;
				} else {
					num2 = num;
				}
			}

			if (num1 != null && num2 != null) {
				if (operator == '+') {
					num1 += num2;
				} else {
					num1 *= num2;
				}
				num2 = null;
			}

		}

		return num1;

	}

	private static int getParanthesisEnd(String line, int start) {

		int paraCount = 0;
		int end;

		for (end = start; end < line.length(); end++) {
			char c = line.charAt(end);

			if (c == '(') {
				paraCount++;
			} else if (c == ')') {
				paraCount--;
			}

			if (paraCount == 0) {
				break;
			}
		}

		return end;

	}

}
