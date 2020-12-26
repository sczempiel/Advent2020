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

import util.AdventUtils;

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

	private static Map<Long, Tile> getTiles() throws IOException {
		List<String> startValues = AdventUtils.getStringInput(20);

		Map<Long, Tile> tiles = new HashMap<>();

		for (Iterator<String> it = startValues.iterator(); it.hasNext();) {

			Long tileId = Long.parseLong(it.next().replace(":", "").split(" ")[1]);

			String line = null;
			List<String> data = new ArrayList<>();
			List<String> binData = new ArrayList<>();

			while (it.hasNext() && !(line = it.next()).isEmpty()) {
				data.add(line);

				String binary = line.replace('.', '0').replace('#', '1');
				binData.add(binary);
			}

			Tile tile = new Tile();
			tile.setData(data);
			tile.setId(tileId);

			for (Orientation orient : Orientation.values()) {
				tile.getBorders().put(orient, TileHelper.buildBorder(orient, binData));
			}

			tiles.put(tileId, tile);
		}

		return tiles;
	}
}
