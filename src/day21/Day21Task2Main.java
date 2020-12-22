package day21;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import util.AdventUtils;
import util.Tuple;

// wrong jxx,dklgl,zzt,qdlpbt,kqv,pmvfzk,tsnkknk,tlgrhdh

public class Day21Task2Main {

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

			List<Tuple<String, String>> allergens = new ArrayList<>();

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
						allergens.add(Tuple.of(allergen, dish.getLeft().get(0)));
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
							allergens.add(Tuple.of(allergen, ingredient));
						}
						anythingRemoved = true;
						break dish;
					}
				}
			}

			String result = allergens.stream().sorted(Comparator.comparing(Tuple::getLeft)).map(Tuple::getRight)
					.collect(Collectors.joining(","));

			AdventUtils.publishResult(21, 2, result);

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
