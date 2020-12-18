package day18;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import util.AdventUtils;

public class Day18Task2Main {

	public static void main(String[] args) {
		try {
			List<String> startValues = AdventUtils.getStringInput(18);

			long result = 0;

			for (String line : startValues) {
				String spaceLess = line.replaceAll(" ", "");
				result += doMath(spaceLess);
			}
			AdventUtils.publishResult(18, 2, result);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static long doMath(String line) {

		List<String> operations = new ArrayList<>();

		int i = 0;
		while (i < line.length()) {
			char c = line.charAt(i);

			if (c == '(') {
				int endPara = getParanthesisEnd(line, i);
				String sub = line.substring(i + 1, endPara);

				operations.add(String.valueOf(doMath(sub)));
				i = endPara + 1;
			} else if (c == '+' || c == '*') {
				operations.add(String.valueOf(c));
				i++;
			} else {
				String num = "";
				while (c != '+' && c != '*' && c != '(') {
					num += c;
					i++;

					if (i >= line.length()) {
						break;
					}

					c = line.charAt(i);
				}

				operations.add(num);

			}

		}
		int index;
		while ((index = operations.indexOf("+")) != -1) {

			Long num1 = Long.valueOf(operations.remove(index + 1));
			Long num2 = Long.valueOf(operations.remove(index - 1));

			operations.set(index - 1, String.valueOf(num1 + num2));

		}

		while ((index = operations.indexOf("*")) != -1) {

			Long num1 = Long.valueOf(operations.remove(index + 1));
			Long num2 = Long.valueOf(operations.remove(index - 1));

			operations.set(index - 1, String.valueOf(num1 * num2));

		}

		return Long.valueOf(operations.get(0));

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
