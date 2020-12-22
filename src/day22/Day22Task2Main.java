package day22;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import util.AdventUtils;
import util.Tuple;

public class Day22Task2Main {
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

			Queue<Integer> winner = game(player1, player2).getRight();

			int result = 0;
			while (winner.size() > 0) {
				Integer card = winner.poll();
				result += (card * (winner.size() + 1));
			}

			AdventUtils.publishResult(22, 2, result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static Tuple<Integer, Queue<Integer>> game(Queue<Integer> player1In, Queue<Integer> player2In) {

		Queue<Integer> player1 = new LinkedList<>(player1In);
		Queue<Integer> player2 = new LinkedList<>(player2In);

		Set<Tuple<Queue<Integer>, Queue<Integer>>> previous = new HashSet<>();

		while (player1.size() > 0 && player2.size() > 0) {

			if (previous.contains(Tuple.of(player1, player2))) {
				return Tuple.of(1, player1);
			}

			previous.add(Tuple.of(new LinkedList<>(player1), new LinkedList<>(player2)));

			Integer card1 = player1.poll();
			Integer card2 = player2.poll();

			boolean player1Wins = false;

			if (previous.size() > 1 && card1 <= player1.size() && card2 <= player2.size()) {
				Tuple<Integer, Queue<Integer>> winner = game(getSubGameDeck(player1, card1),
						getSubGameDeck(player2, card2));
				player1Wins = winner.getLeft() == 1;
			} else {
				player1Wins = card1 > card2;
			}

			if (player1Wins) {
				player1.offer(card1);
				player1.offer(card2);
			} else {
				player2.offer(card2);
				player2.offer(card1);
			}

		}

		if (player1.size() > 0) {
			return Tuple.of(1, player1);
		} else {
			return Tuple.of(2, player2);
		}
	}

	private static Queue<Integer> getSubGameDeck(Queue<Integer> deck, int size) {
		Queue<Integer> subDeck = new LinkedList<>();

		Iterator<Integer> it = deck.iterator();
		while (subDeck.size() < size) {
			subDeck.add(it.next());
		}

		return subDeck;
	}

}
