package day23;

import java.io.IOException;

import util.AdventUtils;

public class Day23Task1Main {

	public static void main(String[] args) {
		try {
			String startValue = AdventUtils.getStringInput(23).get(0);

			NodeMap<Integer, Integer> cups = new NodeMap<>(true);

			int maxValue = 0;
			for (char c : startValue.toCharArray()) {
				int num = Integer.parseInt(String.valueOf(c));
				cups.add(num);

				if (num > maxValue) {
					maxValue = num;
				}
			}

			Node<Integer, Integer> currNode = cups.get(Integer.parseInt(String.valueOf(startValue.charAt(0))));

			for (int i = 1; i <= 100; i++) {

				Node<Integer, Integer> removedCup1 = currNode.getRight();
				Node<Integer, Integer> removedCup2 = removedCup1.getRight();
				Node<Integer, Integer> removedCup3 = removedCup2.getRight();

				cups.remove(removedCup1);
				cups.remove(removedCup2);
				cups.remove(removedCup3);

				Node<Integer, Integer> targetNode = null;
				int targetNum = currNode.getKey();

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

			StringBuilder sb = new StringBuilder();
			Node<Integer, Integer> node = cups.get(1).getRight();

			while (node.getKey() != 1) {
				sb.append(node.getKey());
				node = node.getRight();
			}

			AdventUtils.publishResult(23, 1, sb.toString());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
