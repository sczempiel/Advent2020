package day23;

import java.io.IOException;

import util.AdventUtils;
import util.map.node.NodeMap;

public class Day23Task1Main {

	public static void main(String[] args) {
		try {
			String startValue = AdventUtils.getStringInput(23).get(0);

			NodeMap<Long, Long> cups = new NodeMap<>(true);

			long maxValue = 0;
			for (char c : startValue.toCharArray()) {
				long num = Long.parseLong(String.valueOf(c));
				cups.put(num, null);

				if (num > maxValue) {
					maxValue = num;
				}
			}

			Long currNum = Long.parseLong(String.valueOf(startValue.charAt(0)));

			for (int i = 1; i <= 100; i++) {

				Long removedCup1 = cups.getRight(currNum);
				Long removedCup2 = cups.getRight(removedCup1);
				Long removedCup3 = cups.getRight(removedCup2);

				cups.remove(removedCup1);
				cups.remove(removedCup2);
				cups.remove(removedCup3);

				Long targetNode = null;
				long targetNum = currNum;

				while (targetNode == null) {
					targetNum--;

					if (targetNum < 1) {
						targetNum = maxValue;
					}

					if (cups.containsKey(targetNum)) {
						targetNode = targetNum;
					}
				}

				cups.put(removedCup1, null, targetNode);
				cups.put(removedCup2, null, removedCup1);
				cups.put(removedCup3, null, removedCup2);

				currNum = cups.getRight(currNum);

			}

			StringBuilder sb = new StringBuilder();
			Long node = cups.getRight(1l);

			while (node != 1) {
				sb.append(node);
				node = cups.getRight(node);
			}

			AdventUtils.publishResult(23, 1, sb.toString());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
