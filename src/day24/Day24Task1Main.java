package day24;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import util.AdventUtils;

public class Day24Task1Main {

	public static void main(String[] args) {
		try {
			List<String> paths = AdventUtils.getStringInput(24);

			Set<HexagonCoordinate> hexagons = new HashSet<>();

			for (String path : paths) {

				HexagonCoordinate coordinate = new HexagonCoordinate();

				for (int i = 0; i < path.length(); i++) {
					char raw = path.charAt(i);

					String instruction = String.valueOf(raw);
					if (raw == 'n' || raw == 's') {
						i++;
						instruction += String.valueOf(path.charAt(i));
					}

					walk(coordinate, instruction);

				}

				if (hexagons.contains(coordinate)) {
					hexagons.remove(coordinate);
				} else {
					hexagons.add(coordinate);
				}
			}

			AdventUtils.publishResult(24, 1, hexagons.size());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void walk(HexagonCoordinate coordinate, String instruction) {
		switch (instruction) {
		case "e":
			coordinate.setEast(coordinate.getEast() + 1);
			break;
		case "se":
			coordinate.setSouthEast(coordinate.getSouthEast() + 1);
			break;
		case "sw":
			coordinate.setSouthWest(coordinate.getSouthWest() + 1);
			break;
		case "w":
			coordinate.setWest(coordinate.getWest() + 1);
			break;
		case "nw":
			coordinate.setNorthWest(coordinate.getNorthWest() + 1);
			break;
		case "ne":
			coordinate.setNorthEast(coordinate.getNorthEast() + 1);
			break;
		default:
			throw new IllegalArgumentException(instruction);
		}

		reduce(coordinate);
	}

	/**
	 * 
	 * reduce on steps that cancel out
	 * 
	 * @param coordinate
	 */
	private static void reduce(HexagonCoordinate coordinate) {
		if (coordinate.getWest() > 0 && coordinate.getEast() > 0) {
			coordinate.setWest(coordinate.getWest() - 1);
			coordinate.setEast(coordinate.getEast() - 1);

		} else if (coordinate.getSouthWest() > 0 && coordinate.getNorthEast() > 0) {

			coordinate.setSouthWest(coordinate.getSouthWest() - 1);
			coordinate.setNorthEast(coordinate.getNorthEast() - 1);

		} else if (coordinate.getSouthEast() > 0 && coordinate.getNorthWest() > 0) {

			coordinate.setSouthEast(coordinate.getSouthEast() - 1);
			coordinate.setNorthWest(coordinate.getNorthWest() - 1);

		} else if (coordinate.getNorthWest() > 0 && coordinate.getSouthWest() > 0 && coordinate.getEast() > 0) {

			coordinate.setEast(coordinate.getEast() - 1);
			coordinate.setNorthWest(coordinate.getNorthWest() - 1);
			coordinate.setSouthWest(coordinate.getSouthWest() - 1);

		} else if (coordinate.getNorthEast() > 0 && coordinate.getSouthEast() > 0 && coordinate.getWest() > 0) {

			coordinate.setWest(coordinate.getWest() - 1);
			coordinate.setNorthEast(coordinate.getNorthEast() - 1);
			coordinate.setSouthEast(coordinate.getSouthEast() - 1);

		} else if (coordinate.getSouthEast() > 0 && coordinate.getWest() > 0) {

			coordinate.setWest(coordinate.getWest() - 1);
			coordinate.setSouthWest(coordinate.getSouthWest() + 1);
			coordinate.setSouthEast(coordinate.getSouthEast() - 1);

		} else if (coordinate.getSouthWest() > 0 && coordinate.getEast() > 0) {

			coordinate.setEast(coordinate.getEast() - 1);
			coordinate.setSouthWest(coordinate.getSouthWest() - 1);
			coordinate.setSouthEast(coordinate.getSouthEast() + 1);

		} else if (coordinate.getNorthEast() > 0 && coordinate.getWest() > 0) {

			coordinate.setWest(coordinate.getWest() - 1);
			coordinate.setNorthWest(coordinate.getNorthWest() + 1);
			coordinate.setNorthEast(coordinate.getNorthEast() - 1);

		} else if (coordinate.getNorthWest() > 0 && coordinate.getEast() > 0) {

			coordinate.setEast(coordinate.getEast() - 1);
			coordinate.setNorthWest(coordinate.getNorthWest() - 1);
			coordinate.setNorthEast(coordinate.getNorthEast() + 1);

		} else if (coordinate.getSouthEast() > 0 && coordinate.getNorthEast() > 0) {

			coordinate.setEast(coordinate.getEast() + 1);
			coordinate.setNorthEast(coordinate.getNorthEast() - 1);
			coordinate.setSouthEast(coordinate.getSouthEast() - 1);

		} else if (coordinate.getNorthWest() > 0 && coordinate.getSouthWest() > 0) {

			coordinate.setWest(coordinate.getWest() + 1);
			coordinate.setNorthWest(coordinate.getNorthWest() - 1);
			coordinate.setSouthWest(coordinate.getSouthWest() - 1);

		}
	}

}
