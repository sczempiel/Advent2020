package day20;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import util.AdventUtils;
import util.Tuple;

public class Day20Task2Main {
	private static final String MIDDLE_SNAKE = "#....##....##....###";
	private static final String BOTTOM_SNAKE = ".#..#..#..#..#..#...";
	private static final Pattern MIDDLE_PATTERN = Pattern.compile(MIDDLE_SNAKE);
	private static final Pattern BOTTOM_PATTERN = Pattern.compile(BOTTOM_SNAKE);

	private static Map<Long, Set<Tile>> tilesByEdge = new HashMap<>();
	private static List<Tile> corners = new ArrayList<>();

	public static void main(String[] args) {
		try {
			buildTiles();
			findCorners();

			for (Orientation orient1 : Orientation.values()) {
				for (Orientation orient2 : Orientation.values()) {
					if (orient1 == orient2) {
						continue;
					}
					Border norm = corners.get(0).getBorders().get(orient1);
					Border other = corners.get(0).getBorders().get(orient2);

					if (norm.getTop().equals(other.getTop()) && norm.getBottom().equals(other.getBottom())
							&& norm.getLeft().equals(other.getLeft()) && norm.getRight().equals(other.getRight())) {
						System.out.println(orient1 + " == " + orient2);
					}

				}
			}

			List<List<Tuple<Tile, Orientation>>> posMap = orderTilesIntoPosition();
			printPosMap(posMap);

			List<String> img = buildImage(posMap);

			System.out.println(posMap.stream().flatMap(List::stream).map(Tuple::getRight).distinct()
					.map(String::valueOf).sorted().collect(Collectors.joining(", ")));

			printImage(img);

			int snakeCount = countSnakes(img);

			if (snakeCount == 0) {
				snakeCount = countSnakes(TileHelper.flip(img));
			}

			int waterCount = 0;
			for (String row : img) {
				for (char field : row.toCharArray()) {
					if (field == '#') {
						waterCount++;
					}
				}
			}

			// a snake covers 15 fields
			waterCount -= (15 * snakeCount);

			AdventUtils.publishResult(20, 2, waterCount);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static int countSnakes(List<String> img) {

		List<String> rotatableImg = img;
		int snakeCount = 0;
		int rotas = 0;

		while (snakeCount == 0 && rotas < 4) {

			for (int i = 1; i < rotatableImg.size() - 1; i++) {

				for (int l = 0; l <= rotatableImg.get(i).length() - MIDDLE_SNAKE.length(); l++) {

					int end = l + MIDDLE_SNAKE.length();

					String middle = rotatableImg.get(i).substring(l, end);
					Matcher middleMatcher = MIDDLE_PATTERN.matcher(middle);

					if (middleMatcher.matches()) {

						if (rotatableImg.get(i - 1).charAt(end - 2) == '#') {

							String belowMiddle = rotatableImg.get(i + 1).substring(l, end);
							Matcher bottomMatcher = BOTTOM_PATTERN.matcher(belowMiddle);

							if (bottomMatcher.matches()) {
								snakeCount++;
							}
						}
					}
				}
			}

			rotatableImg = TileHelper.rotate(rotatableImg, Orientation.NORM_ROTA_90);
			rotas++;
		}

		return snakeCount;
	}

	private static List<String> buildImage(List<List<Tuple<Tile, Orientation>>> posMap) {
		List<String> image = new ArrayList<>();

		for (List<Tuple<Tile, Orientation>> row : posMap) {

			for (Tuple<Tile, Orientation> tile : row) {
				TileHelper.flip(tile.getLeft(), tile.getRight());
				TileHelper.rotate(tile.getLeft(), tile.getRight());
			}

			for (int i = 1; i < 9; i++) {
				String imgRow = "";

				for (Tuple<Tile, Orientation> tile : row) {
					imgRow += tile.getLeft().getData().get(i).substring(1, 9);
				}

				image.add(imgRow);

			}

		}

		return image;
	}

	private static List<List<Tuple<Tile, Orientation>>> orderTilesIntoPosition() {

		Tile start = corners.get(0);

		Orientation startOrient = null;

		for (Entry<Orientation, Border> entry : start.getBorders().entrySet()) {
			Border border = entry.getValue();
			if (getOtherTile(start, border.getRight()) != null && getOtherTile(start, border.getBottom()) != null) {
				startOrient = entry.getKey();
				break;
			}
		}

		List<List<Tuple<Tile, Orientation>>> rawMap = new ArrayList<>();
		List<Tuple<Tile, Orientation>> row = new ArrayList<>();
		rawMap.add(row);
		row.add(Tuple.of(start, startOrient));

		Long rightEdge = start.getBorders().get(startOrient).getRight();
		Tile currTile = getOtherTile(start, start.getBorders().get(startOrient).getRight());

		while (currTile != null) {

			while (currTile != null) {

				Orientation orient = null;
				// check the orientation of the current tile
				for (Entry<Orientation, Border> entry : currTile.getBorders().entrySet()) {
					if (rightEdge.equals(entry.getValue().getLeft())) {
						orient = entry.getKey();
						break;
					}
				}
				row.add(Tuple.of(currTile, orient));
				rightEdge = currTile.getBorders().get(orient).getRight();

				// find the next tile to process
				currTile = getOtherTile(currTile, rightEdge);
			}

			Tuple<Tile, Orientation> top = rawMap.get(rawMap.size() - 1).get(0);
			Long bottomEdge = top.getLeft().getBorders().get(top.getRight()).getBottom();

			currTile = getOtherTile(top.getLeft(), bottomEdge);

			if (currTile == null) {
				break;
			}

			row = new ArrayList<>();
			rawMap.add(row);

			Orientation orient = null;
			// check the orientation of the current tile
			for (Entry<Orientation, Border> entry : currTile.getBorders().entrySet()) {
				if (bottomEdge.equals(entry.getValue().getTop())) {
					orient = entry.getKey();
					break;
				}
			}

			row.add(Tuple.of(currTile, orient));
			rightEdge = currTile.getBorders().get(orient).getRight();

			// find the next tile to process
			currTile = getOtherTile(currTile, rightEdge);
		}

		return rawMap;

	}

	private static Tile getOtherTile(Tile tile, Long edge) {
		Set<Tile> tiles = tilesByEdge.get(edge);

		for (Tile oTile : tiles) {
			if (oTile != tile) {
				return oTile;
			}
		}

		return null;
	}

	private static void findCorners() {

		Set<Tile> tiles = tilesByEdge.values().stream().flatMap(Set::stream).collect(Collectors.toSet());
		List<Long> edges = tiles.stream()
				.flatMap(
						t -> t.getBorders().entrySet().stream().flatMap(b -> b.getValue().asList().stream()).distinct())
				.sorted().collect(Collectors.toList());

		for (Tile tile : tiles) {
			int count = countMatchingEdges(tile, edges);

			if (count == 2) {
				corners.add(tile);
			}
		}

		corners.sort(Comparator.comparing(Tile::getId));

	}

	private static int countMatchingEdges(Tile tile, List<Long> edges) {
		List<Long> edgesToCheck = new ArrayList<>(edges);

		int count = 0;
		for (Long edge : tile.getBorders().get(Orientation.NORMAL).asList()) {
			edgesToCheck.remove(edge);
			if (edgesToCheck.contains(edge)) {
				count++;
			}
		}

		return count;

	}

	private static void addToEdgeMap(Tile tile, Border border) {
		for (Long edge : border.asList()) {
			Set<Tile> tiles = tilesByEdge.get(edge);

			if (tiles == null) {
				tiles = new HashSet<>();
				tilesByEdge.put(edge, tiles);
			}

			tiles.add(tile);
		}
	}

	private static void buildTiles() throws IOException {
		List<String> startValues = AdventUtils.getStringInput(20);

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
				addToEdgeMap(tile, tile.getBorders().get(orient));
			}

		}
	}

	private static void printImage(List<String> img) throws IOException {
		StringBuilder sb = new StringBuilder();
		img.forEach(l -> sb.append(l + "\n"));

		AdventUtils.publishExtra(20, 2, sb.toString(), "image");
	}

	private static void printPosMap(List<List<Tuple<Tile, Orientation>>> posMap) throws IOException {
		StringBuilder sb = new StringBuilder();
		for (Iterator<List<Tuple<Tile, Orientation>>> it = posMap.iterator(); it.hasNext();) {

			List<Tuple<Tile, Orientation>> r = it.next();

			for (int i = 0; i < r.size(); i++) {
				if (i == 0) {
					sb.append("+-");
				}
				sb.append("-----" + "----" + "-----" + "-+");
				if (i != r.size() - 1) {
					sb.append("-");
				}
			}

			sb.append("\n");

			for (int i = 0; i < r.size(); i++) {
				if (i == 0) {
					sb.append("| ");
				}
				String top = AdventUtils.printNum(r.get(i).getLeft().getBorders().get(r.get(i).getRight()).getTop(), 4);
				sb.append("     " + top + "      | ");
			}

			sb.append("\n");

			for (int i = 0; i < r.size(); i++) {
				if (i == 0) {
					sb.append("| ");
				}

				String right = AdventUtils.printNum(r.get(i).getLeft().getBorders().get(r.get(i).getRight()).getRight(),
						4);
				String left = AdventUtils.printNum(r.get(i).getLeft().getBorders().get(r.get(i).getRight()).getLeft(),
						4);

				sb.append(left + " " + r.get(i).getLeft().getId() + " " + right + " | ");

			}

			sb.append("\n");

			for (int i = 0; i < r.size(); i++) {
				if (i == 0) {
					sb.append("| ");
				}
				String bottom = AdventUtils
						.printNum(r.get(i).getLeft().getBorders().get(r.get(i).getRight()).getBottom(), 4);
				sb.append("     " + bottom + "      | ");
			}

			sb.append("\n");

			if (!it.hasNext()) {
				for (int i = 0; i < r.size(); i++) {
					if (i == 0) {
						sb.append("+-");
					}
					sb.append("-----" + "----" + "-----" + "-+");
					if (i != r.size() - 1) {
						sb.append("-");
					}
				}
				sb.append("\n");
			}

		}

		AdventUtils.publishExtra(20, 2, sb.toString(), "tileMap");
	}

}
