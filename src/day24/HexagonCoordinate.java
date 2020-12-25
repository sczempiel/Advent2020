package day24;

public class HexagonCoordinate {

	private int east;
	private int southEast;
	private int southWest;
	private int west;
	private int northWest;
	private int northEast;

	public HexagonCoordinate copy() {
		HexagonCoordinate hexCoord = new HexagonCoordinate();

		hexCoord.east = this.east;
		hexCoord.southEast = this.southEast;
		hexCoord.southWest = this.southWest;
		hexCoord.west = this.west;
		hexCoord.northWest = this.northWest;
		hexCoord.northEast = this.northEast;

		return hexCoord;
	}

	public int getEast() {
		return this.east;
	}

	public void setEast(int east) {
		this.east = east;
	}

	public int getSouthEast() {
		return this.southEast;
	}

	public void setSouthEast(int southEast) {
		this.southEast = southEast;
	}

	public int getSouthWest() {
		return this.southWest;
	}

	public void setSouthWest(int southWest) {
		this.southWest = southWest;
	}

	public int getWest() {
		return this.west;
	}

	public void setWest(int west) {
		this.west = west;
	}

	public int getNorthWest() {
		return this.northWest;
	}

	public void setNorthWest(int northWest) {
		this.northWest = northWest;
	}

	public int getNorthEast() {
		return this.northEast;
	}

	public void setNorthEast(int northEast) {
		this.northEast = northEast;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("HexagonCoordinate [");
		if (this.east != 0) {
			builder.append("east=");
			builder.append(this.east);
			builder.append(", ");
		}
		if (this.southEast != 0) {
			builder.append("southEast=");
			builder.append(this.southEast);
			builder.append(", ");
		}
		if (this.southWest != 0) {
			builder.append("southWest=");
			builder.append(this.southWest);
			builder.append(", ");
		}
		if (this.west != 0) {
			builder.append("west=");
			builder.append(this.west);
			builder.append(", ");
		}
		if (this.northWest != 0) {
			builder.append("northWest=");
			builder.append(this.northWest);
			builder.append(", ");
		}
		if (this.northEast != 0) {
			builder.append("northEast=");
			builder.append(this.northEast);
		}
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.east;
		result = prime * result + this.northEast;
		result = prime * result + this.northWest;
		result = prime * result + this.southEast;
		result = prime * result + this.southWest;
		result = prime * result + this.west;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		HexagonCoordinate other = (HexagonCoordinate) obj;
		if (this.east != other.east)
			return false;
		if (this.northEast != other.northEast)
			return false;
		if (this.northWest != other.northWest)
			return false;
		if (this.southEast != other.southEast)
			return false;
		if (this.southWest != other.southWest)
			return false;
		if (this.west != other.west)
			return false;
		return true;
	}

}
