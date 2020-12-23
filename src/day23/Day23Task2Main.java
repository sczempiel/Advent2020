package day23;

import java.io.IOException;

import util.AdventUtils;

public class Day23Task2Main {

	public static void main(String[] args) {
		try {
			String startValue = AdventUtils.getStringInput(23).get(0);

			NodeMap<Long, Long> cups = new NodeMap<>(true);

			long maxValue = 0;

			for (char c : startValue.toCharArray()) {
				long num = Long.parseLong(String.valueOf(c));
				cups.add(num);

				if (num > maxValue) {
					maxValue = num;
				}
			}

			while (maxValue < 1000000) {
				maxValue++;
				cups.add(maxValue);
			}

			Node<Long, Long> currNode = cups.get(Long.parseLong(String.valueOf(startValue.charAt(0))));

			for (int i = 1; i <= 10000000; i++) {

				Node<Long, Long> removedCup1 = currNode.getRight();
				Node<Long, Long> removedCup2 = removedCup1.getRight();
				Node<Long, Long> removedCup3 = removedCup2.getRight();

				cups.remove(removedCup1);
				cups.remove(removedCup2);
				cups.remove(removedCup3);

				Node<Long, Long> targetNode = null;
				long targetNum = currNode.getKey();

				while (targetNode == null) {
					targetNum--;

					if (targetNum < 1) {
						targetNum = maxValue;
					}

					targetNode = cups.get(targetNum);
				}

				cups.add(removedCup1, targetNode);
				cups.add(removedCup2, removedCup1);
				cups.add(removedCup3, removedCup2);

				currNode = currNode.getRight();

			}

			Node<Long, Long> node = cups.get(1l);

			AdventUtils.publishResult(23, 2, node.getRight().getKey() * node.getRight().getRight().getKey());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
