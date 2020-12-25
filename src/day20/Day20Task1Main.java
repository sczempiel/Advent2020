package day20;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import day20.Border.Orientation;
import util.AdventUtils;
import util.StringUtils;

public class Day20Task1Main {

	public static void main(String[] args) {
		try {
			Map<Long, Tile> tiles = getTiles();

			List<Long> edges = tiles.values().stream().flatMap(
					t -> t.getBorders().entrySet().stream().flatMap(b -> b.getValue().asList().stream()).distinct())
					.sorted().collect(Collectors.toList());

			Set<Long> corners = new HashSet<>();
			for (Entry<Long, Tile> til : tiles.entrySet()) {
				List<Long> edgesToCheck = new ArrayList<>(edges);

				AtomicInteger count = new AtomicInteger();

				til.getValue().getBorders().entrySet().stream().flatMap(b -> b.getValue().asList().stream().distinct())
						.forEach(e -> {
							edgesToCheck.remove(e);

							if (edgesToCheck.contains(e)) {
								count.incrementAndGet();
							}
						});

				if (count.get() / 2 == 2) {
					corners.add(til.getKey());
				}
			}

			AdventUtils.publishResult(20, 1, corners.stream().reduce((l1, l2) -> l1 * l2).get());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static Tile getTile(String topEdge, String rightEdge, String bottomEdge, String leftEdge) {
		Tile tile = new Tile();

		Border normal = new Border();
		normal.setOrientation(Orientation.NORMAL);
		normal.setTop(Long.parseLong(topEdge, 2));
		normal.setRight(Long.parseLong(rightEdge, 2));
		normal.setBottom(Long.parseLong(bottomEdge, 2));
		normal.setLeft(Long.parseLong(leftEdge, 2));
		tile.getBorders().put(Orientation.NORMAL, normal);

		Border flippedTB = new Border();
		flippedTB.setOrientation(Orientation.FLIP_TB);
		flippedTB.setTop(Long.parseLong(bottomEdge, 2));
		flippedTB.setRight(Long.parseLong(StringUtils.reverse(rightEdge), 2));
		flippedTB.setBottom(Long.parseLong(topEdge, 2));
		flippedTB.setLeft(Long.parseLong(StringUtils.reverse(leftEdge), 2));
		tile.getBorders().put(Orientation.FLIP_TB, flippedTB);

		Border flippedRL = new Border();
		flippedRL.setOrientation(Orientation.FLIP_RL_NORM);
		flippedRL.setTop(Long.parseLong(StringUtils.reverse(topEdge), 2));
		flippedRL.setRight(Long.parseLong(leftEdge, 2));
		flippedRL.setBottom(Long.parseLong(StringUtils.reverse(bottomEdge), 2));
		flippedRL.setLeft(Long.parseLong(rightEdge, 2));
		tile.getBorders().put(Orientation.FLIP_RL_NORM, flippedRL);

		Border rota90 = new Border();
		rota90.setOrientation(Orientation.NORM_ROTA_90);
		rota90.setTop(Long.parseLong(rightEdge, 2));
		rota90.setRight(Long.parseLong(StringUtils.reverse(bottomEdge), 2));
		rota90.setBottom(Long.parseLong(leftEdge, 2));
		rota90.setLeft(Long.parseLong(StringUtils.reverse(topEdge), 2));
		tile.getBorders().put(Orientation.NORM_ROTA_90, rota90);

		Border rota180 = new Border();
		rota180.setOrientation(Orientation.NORM_ROTA_180);
		rota180.setTop(Long.parseLong(StringUtils.reverse(bottomEdge), 2));
		rota180.setRight(Long.parseLong(StringUtils.reverse(leftEdge), 2));
		rota180.setBottom(Long.parseLong(StringUtils.reverse(topEdge), 2));
		rota180.setLeft(Long.parseLong(StringUtils.reverse(rightEdge), 2));
		tile.getBorders().put(Orientation.NORM_ROTA_180, rota180);

		Border rota270 = new Border();
		rota270.setOrientation(Orientation.NORM_ROTA_270);
		rota270.setTop(Long.parseLong(StringUtils.reverse(leftEdge), 2));
		rota270.setRight(Long.parseLong(topEdge, 2));
		rota270.setBottom(Long.parseLong(StringUtils.reverse(rightEdge), 2));
		rota270.setLeft(Long.parseLong(bottomEdge, 2));
		tile.getBorders().put(Orientation.NORM_ROTA_270, rota270);

		return tile;
	}

	private static Map<Long, Tile> getTiles() throws IOException {
		List<String> startValues = AdventUtils.getStringInput(20);

		Map<Long, Tile> tiles = new HashMap<>();

		for (Iterator<String> it = startValues.iterator(); it.hasNext();) {

			Long tileId = Long.parseLong(it.next().replace(":", "").split(" ")[1]);

			String top = null;
			String right = "";
			String bottom = null;
			String left = "";

			String line = null;
			while (it.hasNext() && !(line = it.next()).isEmpty()) {
				String binary = line.replace('.', '0').replace('#', '1');

				if (top == null) {
					top = binary;
				}
				right += binary.charAt(binary.length() - 1);
				bottom = binary;
				left += binary.charAt(0);
			}

			tiles.put(tileId, getTile(top, right, bottom, left));
		}

		return tiles;
	}
}
