package day20;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Tile {

	public static enum Orientation {
		NORMAL, FLIP_TB, FLIP_RL, ROTA
	}

	private Long id;

	// contains a list of edge hashes (top, right, bottom, left)
	private final List<Long> normal = new ArrayList<>();
	private final List<Long> flippedTB = new ArrayList<>();
	private final List<Long> flippedRL = new ArrayList<>();
	private final List<Long> rotated = new ArrayList<>();

	private final Set<Orientation> possibleOrientations = new HashSet<>();

	private final List<String> data = new ArrayList<>();

	public Tile() {
		this.possibleOrientations.add(Orientation.NORMAL);
		this.possibleOrientations.add(Orientation.FLIP_TB);
		this.possibleOrientations.add(Orientation.FLIP_RL);
		this.possibleOrientations.add(Orientation.ROTA);
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTop() {
		return this.getCorrectOrientation().get(0);
	}

	public Long getRight() {
		return this.getCorrectOrientation().get(1);
	}

	public Long getBottom() {
		return this.getCorrectOrientation().get(2);
	}

	public Long getLeft() {
		return this.getCorrectOrientation().get(3);
	}

	public List<Long> getCorrectOrientation() {
		if (this.possibleOrientations.contains(Orientation.NORMAL)) {
			return this.normal;
		}
		if (this.possibleOrientations.contains(Orientation.FLIP_TB)) {
			return this.flippedTB;
		}
		if (this.possibleOrientations.contains(Orientation.FLIP_RL)) {
			return this.flippedRL;
		}
		if (this.possibleOrientations.contains(Orientation.ROTA)) {
			return this.rotated;
		}

		return null;
	}

	public Set<Long> removeOrientation(List<Long> edgeHash) {

		Set<Long> removedPossibilities = new HashSet<>();

		if (this.possibleOrientations.contains(Orientation.NORMAL) && this.normal.containsAll(edgeHash)) {
			removedPossibilities.addAll(this.normal);
			this.possibleOrientations.remove(Orientation.NORMAL);
		}
		if (this.possibleOrientations.contains(Orientation.FLIP_TB) && this.flippedTB.containsAll(edgeHash)) {
			removedPossibilities.addAll(this.flippedTB);
			this.possibleOrientations.remove(Orientation.FLIP_TB);
		}
		if (this.possibleOrientations.contains(Orientation.FLIP_RL) && this.flippedRL.containsAll(edgeHash)) {
			removedPossibilities.addAll(this.flippedRL);
			this.possibleOrientations.remove(Orientation.FLIP_RL);
		}
		if (this.possibleOrientations.contains(Orientation.ROTA) && this.rotated.containsAll(edgeHash)) {
			removedPossibilities.addAll(this.rotated);
			this.possibleOrientations.remove(Orientation.ROTA);
		}

		return removedPossibilities;

	}

	public List<String> getData() {
		return this.data;
	}

	public Set<Orientation> getPossibleOrientations() {
		return this.possibleOrientations;
	}

	public List<Long> getNormal() {
		return this.normal;
	}

	public List<Long> getFlippedTB() {
		return this.flippedTB;
	}

	public List<Long> getFlippedRL() {
		return this.flippedRL;
	}

	public List<Long> getRotated() {
		return this.rotated;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Tile [");
		if (this.id != null) {
			builder.append("id=");
			builder.append(this.id);
		}
		builder.append("]");
		return builder.toString();
	}

}
