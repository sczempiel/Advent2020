package day16;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import util.Tuple;

public class Field {

	private String name;
	private List<Tuple<Integer, Integer>> boundries = new ArrayList<>();

	public static Field fromLine(String line) {
		Matcher matcher = Pattern.compile("([\\w ]+): (\\d+)-(\\d+) or (\\d+)-(\\d+)").matcher(line);

		if (!matcher.matches()) {
			return null;
		}

		Field field = new Field();

		field.name = matcher.group(1);
		field.boundries.add(Tuple.of(Integer.valueOf(matcher.group(2)), Integer.valueOf(matcher.group(3))));
		field.boundries.add(Tuple.of(Integer.valueOf(matcher.group(4)), Integer.valueOf(matcher.group(5))));

		return field;
	}

	public boolean matches(Integer value) {
		for (Tuple<Integer, Integer> boundry : this.boundries) {
			if (value >= boundry.getLeft() && value <= boundry.getRight()) {
				return true;
			}
		}

		return false;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Tuple<Integer, Integer>> getBoundries() {
		return this.boundries;
	}

	public void setBoundries(List<Tuple<Integer, Integer>> boundries) {
		this.boundries = boundries;
	}
}
