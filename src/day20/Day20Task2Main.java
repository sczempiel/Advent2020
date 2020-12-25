package day20;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import day20.Border.Orientation;
import util.AdventUtils;
import util.StringUtils;
import util.Tuple;

// wrong answer of code 2176
// guessed correct 2161 as this are all hits that match the pattern #....##....##....###
public class Day20Task2Main {
	private static final String MIDDLE_SNAKE = "#....##....##....###";
	private static final String BOTTOM_SNAKE = ".#..#..#..#..#..#...";
	private static final Pattern MIDDLE_PATTERN = Pattern.compile(MIDDLE_SNAKE);
	private static final Pattern BOTTOM_PATTERN = Pattern.compile(BOTTOM_SNAKE);

	private static Map<Long, List<Tile>> tilesByEdge = new HashMap<>();
	private static List<Tile> corners = new ArrayList<>();

	public static void main(String[] args) {
		try {
			buildTiles();
			findCorners();

			List<List<Tuple<Tile, Orientation>>> posMap = orderTilesIntoPosition();
			printPosMap(posMap);

			List<String> img = buildImage(posMap);
			printImage(img);

			int snakeCount = countSnakes(img);

			if (snakeCount == 0) {
				snakeCount = countSnakes(flipTB(img));
			}

			if (snakeCount == 0) {
				snakeCount = countSnakes(flipRL(img));
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

				Matcher middleMatcher = MIDDLE_PATTERN.matcher(rotatableImg.get(i));

				while (middleMatcher.find()) {
					int middleStart = middleMatcher.start();
					int middleEnd = middleMatcher.end();

					if (rotatableImg.get(i - 1).charAt(middleEnd - 2) == '#') {

						String belowMiddle = rotatableImg.get(i + 1).substring(middleStart, middleEnd);
						Matcher bottomMatcher = BOTTOM_PATTERN.matcher(belowMiddle);

						if (bottomMatcher.matches()) {
							snakeCount++;
						}

					}

				}
			}

			rotatableImg = rotate(rotatableImg, 1);
			rotas++;
		}

		return snakeCount;
	}

	private static List<String> buildImage(List<List<Tuple<Tile, Orientation>>> posMap) {
		List<String> image = new ArrayList<>();

		for (List<Tuple<Tile, Orientation>> row : posMap) {

			for (Tuple<Tile, Orientation> tile : row) {
				flipRL(tile.getLeft(), tile.getRight());
				flipTB(tile.getLeft(), tile.getRight());
				rotate(tile.getLeft(), tile.getRight());
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

	private static void rotate(Tile tile, Orientation orient) {

		int rotations = 0;

		if (orient == Orientation.FLIP_RL_ROTA_90 || orient == Orientation.NORM_ROTA_90) {
			rotations = 1;
		} else if (orient == Orientation.FLIP_RL_ROTA_180 || orient == Orientation.NORM_ROTA_180) {
			rotations = 2;
		} else if (orient == Orientation.FLIP_RL_ROTA_270 || orient == Orientation.NORM_ROTA_270) {
			rotations = 3;
		}

		tile.setData(rotate(tile.getData(), rotations));
	}

	private static List<String> rotate(List<String> img, int rotations) {

		int rotas = 0;
		List<String> toRotate = img;
		List<String> rotated = img;

		while (rotas < rotations) {

			rotated = new ArrayList<>();

			for (int i = toRotate.get(0).length() - 1; i >= 0; i--) {

				String rotaRow = "";

				for (String row : toRotate) {
					rotaRow += String.valueOf(row.charAt(i));
				}

				rotated.add(rotaRow);

			}

			toRotate = rotated;
			rotas++;
		}

		return rotated;
	}

	private static void flipRL(Tile tile, Orientation orient) {

		if (orient != Orientation.FLIP_RL_NORM && orient != Orientation.FLIP_RL_ROTA_90
				&& orient != Orientation.FLIP_RL_ROTA_180 && orient != Orientation.FLIP_RL_ROTA_270) {
			return;
		}

		tile.setData(flipRL(tile.getData()));
	}

	private static List<String> flipRL(List<String> img) {

		List<String> flipped = new ArrayList<>();

		for (String row : img) {
			flipped.add(StringUtils.reverse(row));
		}

		return flipped;
	}

	private static void flipTB(Tile tile, Orientation orient) {

		if (orient != Orientation.FLIP_TB) {
			return;
		}

		tile.setData(flipTB(tile.getData()));
	}

	private static List<String> flipTB(List<String> img) {

		List<String> flipped = new ArrayList<>();

		for (int i = img.size() - 1; i >= 0; i--) {
			flipped.add(img.get(i));
		}

		return flipped;
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
		List<Tile> tiles = tilesByEdge.get(edge);

		for (Tile oTile : tiles) {
			if (oTile != tile) {
				return oTile;
			}
		}

		return null;
	}

	private static void findCorners() {

		Set<Tile> tiles = tilesByEdge.values().stream().flatMap(List::stream).collect(Collectors.toSet());
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

	private static Tile getTile(String topEdge, String rightEdge, String bottomEdge, String leftEdge) {
		Tile tile = new Tile();

		//////////////////////////////////////////////////////
		Border normal = new Border();
		normal.setOrientation(Orientation.NORMAL);
		normal.setTop(Long.parseLong(topEdge, 2));
		normal.setRight(Long.parseLong(rightEdge, 2));
		normal.setBottom(Long.parseLong(bottomEdge, 2));
		normal.setLeft(Long.parseLong(leftEdge, 2));
		tile.getBorders().put(Orientation.NORMAL, normal);

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

		//////////////////////////////////////////////////////

		Border flippedRL = new Border();
		flippedRL.setOrientation(Orientation.FLIP_RL_NORM);
		flippedRL.setTop(Long.parseLong(StringUtils.reverse(topEdge), 2));
		flippedRL.setRight(Long.parseLong(leftEdge, 2));
		flippedRL.setBottom(Long.parseLong(StringUtils.reverse(bottomEdge), 2));
		flippedRL.setLeft(Long.parseLong(rightEdge, 2));
		tile.getBorders().put(Orientation.FLIP_RL_NORM, flippedRL);

		Border flippedRL90 = new Border();
		flippedRL90.setOrientation(Orientation.FLIP_RL_ROTA_90);
		flippedRL90.setTop(Long.parseLong(leftEdge, 2));
		flippedRL90.setRight(Long.parseLong(bottomEdge, 2));
		flippedRL90.setBottom(Long.parseLong(rightEdge, 2));
		flippedRL90.setLeft(Long.parseLong(topEdge, 2));
		tile.getBorders().put(Orientation.FLIP_RL_ROTA_90, flippedRL90);

		Border flippedRL180 = new Border();
		flippedRL180.setOrientation(Orientation.FLIP_RL_ROTA_180);
		flippedRL180.setTop(Long.parseLong(bottomEdge, 2));
		flippedRL180.setRight(Long.parseLong(StringUtils.reverse(rightEdge), 2));
		flippedRL180.setBottom(Long.parseLong(topEdge, 2));
		flippedRL180.setLeft(Long.parseLong(StringUtils.reverse(leftEdge), 2));
		tile.getBorders().put(Orientation.FLIP_RL_ROTA_180, flippedRL180);

		Border flippedRL270 = new Border();
		flippedRL270.setOrientation(Orientation.FLIP_RL_ROTA_270);
		flippedRL270.setTop(Long.parseLong(StringUtils.reverse(rightEdge), 2));
		flippedRL270.setRight(Long.parseLong(StringUtils.reverse(topEdge), 2));
		flippedRL270.setBottom(Long.parseLong(StringUtils.reverse(leftEdge), 2));
		flippedRL270.setLeft(Long.parseLong(StringUtils.reverse(bottomEdge), 2));
		tile.getBorders().put(Orientation.FLIP_RL_ROTA_270, flippedRL270);

		//////////////////////////////////////////////////////

		Border flippedTB = new Border();
		flippedTB.setOrientation(Orientation.FLIP_TB);
		flippedTB.setTop(Long.parseLong(bottomEdge, 2));
		flippedTB.setRight(Long.parseLong(StringUtils.reverse(rightEdge), 2));
		flippedTB.setBottom(Long.parseLong(topEdge, 2));
		flippedTB.setLeft(Long.parseLong(StringUtils.reverse(leftEdge), 2));
		tile.getBorders().put(Orientation.FLIP_TB, flippedTB);

		return tile;
	}

	private static void addToEdgeMap(Tile tile, Border border) {
		for (Long edge : border.asList()) {
			List<Tile> tiles = tilesByEdge.get(edge);

			if (tiles == null) {
				tiles = new ArrayList<>();
				tilesByEdge.put(edge, tiles);
			}

			tiles.add(tile);
		}
	}

	private static void buildTiles() throws IOException {
		List<String> startValues = AdventUtils.getStringInput(20);

		for (Iterator<String> it = startValues.iterator(); it.hasNext();) {

			Long tileId = Long.parseLong(it.next().replace(":", "").split(" ")[1]);

			String top = null;
			String right = "";
			String bottom = null;
			String left = "";

			String line = null;

			List<String> data = new ArrayList<>();
			while (it.hasNext() && !(line = it.next()).isEmpty()) {
				String binary = line.replace('.', '0').replace('#', '1');
				data.add(line);

				if (top == null) {
					top = binary;
				}
				right += binary.charAt(binary.length() - 1);
				bottom = binary;
				left += binary.charAt(0);
			}

			Tile tile = getTile(top, right, bottom, left);
			tile.getData().addAll(data);
			tile.setId(tileId);

			addToEdgeMap(tile, tile.getBorders().get(Orientation.NORMAL));
			addToEdgeMap(tile, tile.getBorders().get(Orientation.NORM_ROTA_180));
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
