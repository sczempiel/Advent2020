package day25;

import java.io.IOException;
import java.util.List;

import util.AdventUtils;

// high 3926736
public class Day25Task1Main {

	public static void main(String[] args) {
		try {
			List<Integer> startValues = AdventUtils.getIntegerInput(25);

			Integer cardPK = startValues.get(0);
			Integer dookPK = startValues.get(1);

			Integer cardLoopSize = getLoopSize(cardPK);

			AdventUtils.publishResult(25, 1, getEncryptionKey(cardLoopSize, dookPK));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static long getEncryptionKey(int loopSize, int pk) {
		int loops = 0;
		long value = 1;

		while (loops < loopSize) {
			value *= pk;
			value %= 20201227;
			loops++;
		}

		return value;
	}

	private static Integer getLoopSize(int pK) {
		int subject = 7;
		int loopSize = 0;
		int value = 1;

		while (value != pK) {
			value *= subject;
			value %= 20201227;
			loopSize++;
		}

		if (value != pK) {
			return null;
		}
		return loopSize;
	}

}
