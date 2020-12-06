package day05;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import util.AdventUtils;

public class Day5Task1Main {

	public static void main(String[] args) {
		try {
			List<String> startValues = AdventUtils.getStringInput(5);

			int maxId = 0;

			for (String instr : startValues) {

				int seatId = findSeatId(instr);

				AdventUtils.publishNewExtraLine(5, 1, instr + " -> " + seatId, "seatIds");

				if (seatId > maxId) {
					maxId = seatId;
				}
			}

			AdventUtils.publishResult(5, 1, maxId);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static int findSeatId(String instr) {
		int row = walk(Arrays.copyOf(instr.toCharArray(), 7), 0, 0, 127);
		int seat = walk(Arrays.copyOfRange(instr.toCharArray(), 7, instr.length()), 0, 0, 7);

		return row * 8 + seat;
	}

	private static int walk(char[] instructions, int pos, int downBound, int upperBound) {

		char instr = instructions[pos];

		int newDownBound = downBound;
		int newUpperBound = upperBound;

		if ('F' == instr || 'L' == instr) {
			newUpperBound = upperBound - ((upperBound - downBound + 1) / 2);
		} else if ('B' == instr || 'R' == instr) {
			newDownBound = downBound + ((upperBound - downBound) / 2) + 1;
		}

		int newPos = pos + 1;

		if (newPos == instructions.length) {
			return newUpperBound;
		}

		return walk(instructions, newPos, newDownBound, newUpperBound);

	}

}
