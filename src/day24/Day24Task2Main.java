package day24;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import util.AdventUtils;

public class Day24Task2Main {

	public static void main(String[] args) {
		try {
			Set<HexagonCoordinate> hexagons = buildDay0();

			for (int i = 1; i <= 100; i++) {
				Set<HexagonCoordinate> dayHex = new HashSet<>();

				for (HexagonCoordinate coord : hexagons) {

					flipyFlop(coord, dayHex, hexagons);

					for (HexagonCoordinate adj : getAdjustent(coord)) {
						flipyFlop(adj, dayHex, hexagons);
					}

				}

				hexagons = dayHex;
			}

			AdventUtils.publishResult(24, 2, hexagons.size());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void flipyFlop(HexagonCoordinate coord, Set<HexagonCoordinate> dayHex,
			Set<HexagonCoordinate> hexagons) {

		Set<HexagonCoordinate> adjustent = getAdjustent(coord);
		int count = countBlackSideUp(adjustent, hexagons);

		if (count == 2 || (hexagons.contains(coord) && count == 1)) {
			dayHex.add(coord);
		}
	}

	private static int countBlackSideUp(Set<HexagonCoordinate> coordinates, Set<HexagonCoordinate> hexagons) {

		int count = 0;

		for (HexagonCoordinate coord : coordinates) {

			if (hexagons.contains(coord)) {
				count++;
			}
		}

		return count;
	}

	private static Set<HexagonCoordinate> getAdjustent(HexagonCoordinate coord) {
		Set<HexagonCoordinate> adjustent = new HashSet<>();

		HexagonCoordinate east = coord.copy();
		east.setEast(east.getEast() + 1);
		reduce(east);
		adjustent.add(east);

		HexagonCoordinate southEast = coord.copy();
		southEast.setSouthEast(southEast.getSouthEast() + 1);
		reduce(southEast);
		adjustent.add(southEast);

		HexagonCoordinate southWest = coord.copy();
		southWest.setSouthWest(southWest.getSouthWest() + 1);
		reduce(southWest);
		adjustent.add(southWest);

		HexagonCoordinate west = coord.copy();
		west.setWest(west.getWest() + 1);
		reduce(west);
		adjustent.add(west);

		HexagonCoordinate northEast = coord.copy();
		northEast.setNorthEast(northEast.getNorthEast() + 1);
		reduce(northEast);
		adjustent.add(northEast);

		HexagonCoordinate northWest = coord.copy();
		northWest.setNorthWest(northWest.getNorthWest() + 1);
		reduce(northWest);
		adjustent.add(northWest);

		return adjustent;
	}

	private static Set<HexagonCoordinate> buildDay0() throws IOException {
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

		return hexagons;
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
