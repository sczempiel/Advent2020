package day22;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import util.AdventUtils;

public class Day22Task1Main {

	public static void main(String[] args) {
		try {
			List<String> startValues = AdventUtils.getStringInput(22);

			Queue<Integer> player1 = new LinkedList<>();
			Queue<Integer> player2 = new LinkedList<>();

			Queue<Integer> player = player1;
			Pattern pat = Pattern.compile("\\d+");
			for (String line : startValues) {

				if (line.isEmpty()) {
					player = player2;
					continue;
				}

				Matcher matcher = pat.matcher(line);
				if (matcher.matches()) {
					player.offer(Integer.parseInt(line));
				}
			}

			while (player1.size() > 0 && player2.size() > 0) {
				Integer card1 = player1.poll();
				Integer card2 = player2.poll();

				if (card1 > card2) {
					player1.offer(card1);
					player1.offer(card2);
				} else {
					player2.offer(card2);
					player2.offer(card1);
				}
			}

			Queue<Integer> winner;

			if (player1.size() > 0) {
				winner = player1;
			} else {
				winner = player2;
			}

			int result = 0;
			while (winner.size() > 0) {
				Integer card = winner.poll();
				result += (card * (winner.size() + 1));
			}

			AdventUtils.publishResult(22, 1, result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
