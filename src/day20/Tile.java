package day20;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tile {

	private Long id;
	private Map<Orientation, Border> borders = new HashMap<>();
	private List<String> data = new ArrayList<>();

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Map<Orientation, Border> getBorders() {
		return this.borders;
	}

	public void setBorders(Map<Orientation, Border> borders) {
		this.borders = borders;
	}

	public List<String> getData() {
		return this.data;
	}

	public void setData(List<String> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Tile [");
		if (this.id != null) {
			builder.append("id=");
			builder.append(this.id);
			builder.append(", ");
		}
		if (this.borders != null) {
			builder.append("borders=");
			builder.append(this.borders);
		}
		builder.append("]");
		return builder.toString();
	}

}
