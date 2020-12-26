package day20;

import java.util.ArrayList;
import java.util.List;

public class Border {

	public static enum Direction {
		TOP, RIGHT, BOTTOM, LEFT
	}

	private Orientation orientation;

	private Long top;
	private Long right;
	private Long bottom;
	private Long left;

	public List<Long> asList() {
		List<Long> edges = new ArrayList<>();

		edges.add(this.top);
		edges.add(this.right);
		edges.add(this.bottom);
		edges.add(this.left);

		return edges;
	}

	public Orientation getOrientation() {
		return this.orientation;
	}

	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}

	public Long getTop() {
		return this.top;
	}

	public void setTop(Long top) {
		this.top = top;
	}

	public Long getRight() {
		return this.right;
	}

	public void setRight(Long right) {
		this.right = right;
	}

	public Long getBottom() {
		return this.bottom;
	}

	public void setBottom(Long bottom) {
		this.bottom = bottom;
	}

	public Long getLeft() {
		return this.left;
	}

	public void setLeft(Long left) {
		this.left = left;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Border [");
		if (this.orientation != null) {
			builder.append("orientation=");
			builder.append(this.orientation);
			builder.append(", ");
		}
		if (this.top != null) {
			builder.append("top=");
			builder.append(this.top);
			builder.append(", ");
		}
		if (this.right != null) {
			builder.append("right=");
			builder.append(this.right);
			builder.append(", ");
		}
		if (this.bottom != null) {
			builder.append("bottom=");
			builder.append(this.bottom);
			builder.append(", ");
		}
		if (this.left != null) {
			builder.append("left=");
			builder.append(this.left);
		}
		builder.append("]");
		return builder.toString();
	}

}
