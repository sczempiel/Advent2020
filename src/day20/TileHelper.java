package day20;

import java.util.ArrayList;
import java.util.List;

public class TileHelper {
	public static Border buildBorder(Orientation orient, List<String> binData) {

		List<String> rotatable = new ArrayList<>(binData);

		if (orient.isFlip()) {
			rotatable = flip(rotatable);
		}

		rotatable = rotate(rotatable, orient);

		Border border = new Border();
		border.setOrientation(orient);
		border.setTop(Long.parseLong(rotatable.get(0), 2));
		border.setRight(Long.parseLong(getEdgeRL(rotatable, rotatable.size() - 1), 2));
		border.setBottom(Long.parseLong(rotatable.get(rotatable.size() - 1), 2));
		border.setLeft(Long.parseLong(getEdgeRL(rotatable, 0), 2));

		return border;
	}

	public static String getEdgeRL(List<String> binData, int index) {
		StringBuilder sb = new StringBuilder();

		for (String row : binData) {
			sb.append(row.charAt(index));
		}

		return sb.toString();
	}

	public static void rotate(Tile tile, Orientation orient) {
		tile.setData(rotate(tile.getData(), orient));
	}

	public static List<String> rotate(List<String> img, Orientation orient) {

		int rotas = 0;
		List<String> toRotate = img;
		List<String> rotated = img;

		while (rotas < orient.getRotations()) {

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

	public static void flip(Tile tile, Orientation orient) {
		if (orient.isFlip()) {
			tile.setData(flip(tile.getData()));
		}
	}

	public static List<String> flip(List<String> img) {

		List<String> flipped = new ArrayList<>();

		for (int i = img.size() - 1; i >= 0; i--) {
			flipped.add(img.get(i));
		}

		return flipped;
	}
}
