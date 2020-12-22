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
import java.util.stream.Stream;

import util.AdventUtils;
import util.StringUtils;

public class Day20Task1Main {

	public static void main(String[] args) {
		try {
			Map<Long, Tile> tiles = getTiles();

			List<Long> edges = tiles.values().stream()
					.flatMap(t -> Stream.concat(t.getNormal().stream(), t.getRotated().stream())).sorted()
					.collect(Collectors.toList());

			Set<Long> corners = new HashSet<>();
			for (Entry<Long, Tile> til : tiles.entrySet()) {
				List<Long> edgesToCheck = new ArrayList<>(edges);

				AtomicInteger count = new AtomicInteger();

				Stream.concat(til.getValue().getNormal().stream(), til.getValue().getRotated().stream()).forEach(e -> {
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

		tile.getNormal().add(Long.parseLong(topEdge, 2));
		tile.getNormal().add(Long.parseLong(rightEdge, 2));
		tile.getNormal().add(Long.parseLong(bottomEdge, 2));
		tile.getNormal().add(Long.parseLong(leftEdge, 2));

		tile.getFlippedTB().add(Long.parseLong(bottomEdge, 2));
		tile.getFlippedTB().add(Long.parseLong(StringUtils.reverse(rightEdge), 2));
		tile.getFlippedTB().add(Long.parseLong(topEdge, 2));
		tile.getFlippedTB().add(Long.parseLong(StringUtils.reverse(leftEdge), 2));

		tile.getFlippedRL().add(Long.parseLong(StringUtils.reverse(topEdge), 2));
		tile.getFlippedRL().add(Long.parseLong(leftEdge, 2));
		tile.getFlippedRL().add(Long.parseLong(StringUtils.reverse(bottomEdge), 2));
		tile.getFlippedRL().add(Long.parseLong(rightEdge, 2));

		tile.getRotated().add(Long.parseLong(StringUtils.reverse(bottomEdge), 2));
		tile.getRotated().add(Long.parseLong(StringUtils.reverse(leftEdge), 2));
		tile.getRotated().add(Long.parseLong(StringUtils.reverse(topEdge), 2));
		tile.getRotated().add(Long.parseLong(StringUtils.reverse(rightEdge), 2));

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
