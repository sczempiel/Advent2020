package day21;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import util.AdventUtils;
import util.Tuple;

public class Day21Task1Main {

	private static List<Tuple<List<String>, List<String>>> dishes = new ArrayList<>();

	public static void main(String[] args) {
		try {
			List<String> startValues = AdventUtils.getStringInput(21);

			for (String line : startValues) {
				String[] split = line.replaceAll("\\)", "").split(" \\(contains ");

				List<String> ingredients = new ArrayList<>(Arrays.asList(split[0].split(" ")));
				List<String> allergens = new ArrayList<>(Arrays.asList(split[1].split(", ")));

				dishes.add(Tuple.of(ingredients, allergens));
			}

			boolean anythingRemoved = true;
			while (anythingRemoved) {
				anythingRemoved = false;

				dish: for (Tuple<List<String>, List<String>> dish : dishes) {

					if (dish.getRight().size() != 1) {
						continue;
					}

					String allergen = dish.getRight().get(0);

					if (dish.getLeft().size() == 1) {
						anythingRemoved = true;
						removeDish(dish.getLeft().get(0), allergen);
						break dish;
					}

					List<String> ingredientsToRemove = new ArrayList<>(dish.getLeft());

					for (Tuple<List<String>, List<String>> checkAgainst : dishes) {

						if (dish == checkAgainst || !checkAgainst.getRight().contains(allergen)) {
							continue;
						}
						ingredientsToRemove.retainAll(checkAgainst.getLeft());
					}

					if (ingredientsToRemove.size() == 1) {
						for (String ingredient : ingredientsToRemove) {
							removeDish(ingredient, allergen);
						}
						anythingRemoved = true;
						break dish;
					}

				}
			}

			int count = 0;

			for (Tuple<List<String>, List<String>> dish : dishes) {
				count += dish.getLeft().size();
			}
			AdventUtils.publishResult(21, 1, count);

		} catch (

		IOException e) {
			e.printStackTrace();
		}
	}

	public static void removeDish(String dish, String allergen) {
		for (Tuple<List<String>, List<String>> removeFrom : dishes) {
			if (removeFrom.getLeft().contains(dish)) {
				removeFrom.getLeft().remove(dish);
				removeFrom.getRight().remove(allergen);
			}
		}
	}

}
