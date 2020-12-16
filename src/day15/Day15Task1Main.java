package day15;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import util.AdventUtils;

public class Day15Task1Main {

	public static void main(String[] args) {
		try {
			List<Integer> startValues = Arrays.asList(AdventUtils.getStringInput(15).get(0).split(",")).stream()
					.map(Integer::valueOf).collect(Collectors.toList());

			Map<Integer, List<Integer>> lastAppeareences = new HashMap<>();

			int last = -1;

			for (int i = 0; i < startValues.size(); i++) {
				last = startValues.get(i);

				List<Integer> apperences = new ArrayList<>();
				apperences.add(i + 1);

				lastAppeareences.put(last, apperences);
			}

			for (int i = startValues.size() + 1; i <= 2020; i++) {
				List<Integer> lastApp = lastAppeareences.get(last);

				if (lastApp.size() == 1) {
					last = 0;
				} else {
					last = lastApp.get(lastApp.size() - 1) - lastApp.get(lastApp.size() - 2);
				}

				lastApp = lastAppeareences.get(last);

				if (lastApp == null) {
					lastApp = new ArrayList<>();
					lastAppeareences.put(last, lastApp);
				}

				lastApp.add(i);

			}

			AdventUtils.publishResult(15, 1, last);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
