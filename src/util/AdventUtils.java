package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;
import java.util.Collections;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AdventUtils {
	private static final String EXTRA_FILE_FORMAT = "extra_task%d_%s.txt";
	private static final String RESULT_FILE_FORMAT = "result_task%d.txt";
	private static final String INPUT_FILE_FORMAT = "day%s/input.txt";

	private static Map<Integer, List<String>> inputs = new HashMap<>();

	private AdventUtils() {

	}

	public static void publishResult(int day, int task, int result) throws IOException {
		publishResult(day, task, String.valueOf(result));
	}

	public static void publishResult(int day, int task, long result) throws IOException {
		publishResult(day, task, String.valueOf(result));
	}

	public static void publishResult(int day, int task, String result) throws IOException {
		System.out.println(result);
		writeResult(day, task, result);
	}

	public static void writeResult(int day, int task, String result) throws IOException {
		writeFile(result, getResultFilePath(day, task));
	}

	public static void publishExtra(int day, int task, String result, String extraName) throws IOException {
		System.out.println(result);
		writeExtra(day, task, result, extraName);
	}

	public static void writeExtra(int day, int task, String result, String extraName) throws IOException {
		writeFile(result, getExtraFilePath(day, task, extraName));
	}

	public static void eraseExtraFile(int day, int task, String extraName) throws IOException {
		writeExtra(day, task, "", extraName);
	}

	public static void writeNewExtraLine(int day, int task, String result, String extraName) throws IOException {
		Writer w = null;
		try {
			w = new BufferedWriter(new FileWriter(new File(getExtraFilePath(day, task, extraName)), true));
			w.write(result);
			w.write("\n");
		} finally {
			if (w != null) {
				w.close();
			}
		}
	}

	public static void publishNewExtraLine(int day, int task, String result, String extraName) throws IOException {
		System.out.println(result);
		writeNewExtraLine(day, task, result, extraName);
	}

	private static void writeFile(String result, String filePath) throws IOException {
		OutputStream out = null;
		Writer w = null;

		try {
			out = new FileOutputStream(filePath);
			w = new OutputStreamWriter(out);
			w.write(result);
		} finally {
			if (w != null) {
				w.close();
			}
		}
	}

	private static String getResultFilePath(int day) {
		URL url = AdventUtils.class.getResource("../" + AdventUtils.getInputFileName(day));
		String path = url.getPath().replaceAll("/bin/", "/src/");
		return path.substring(0, path.lastIndexOf("/") + 1);
	}

	private static String getResultFilePath(int day, int task) {
		String path = getResultFilePath(day);

		StringBuilder sb = new StringBuilder();
		Formatter formatter = new Formatter(sb);
		formatter.format(RESULT_FILE_FORMAT, task);
		formatter.close();
		return path + sb.toString();
	}

	private static String getExtraFilePath(int day, int task, String extraName) {
		String path = getResultFilePath(day);

		StringBuilder sb = new StringBuilder();
		Formatter formatter = new Formatter(sb);
		formatter.format(EXTRA_FILE_FORMAT, task, extraName);
		formatter.close();
		return path + sb.toString();
	}

	public static BufferedReader getBufferedReader(int day) throws IOException {
		InputStream in = null;
		Reader r = null;

		URL url = AdventUtils.class.getResource("../" + AdventUtils.getInputFileName(day));
		in = new FileInputStream(url.getPath().replaceAll("/bin/", "/src/"));
		r = new InputStreamReader(in);
		return new BufferedReader(r);
	}

	public static List<Integer> getIntegerInput(int day) throws IOException {
		List<Integer> input = getStringInput(day).stream().map(line -> Integer.valueOf(line))
				.collect(Collectors.toList());
		input = Collections.unmodifiableList(input);
		return input;
	}

	public static List<Long> getLongInput(int day) throws IOException {
		List<Long> input = getStringInput(day).stream().map(line -> Long.valueOf(line)).collect(Collectors.toList());
		input = Collections.unmodifiableList(input);
		return input;
	}

	public static List<String> getStringInput(int day) throws IOException {
		List<String> input = inputs.get(day);
		if (input == null) {
			BufferedReader br = null;
			try {
				br = getBufferedReader(day);
				List<String> result = br.lines().collect(Collectors.toList());
				input = Collections.unmodifiableList(result);
				inputs.put(day, input);
			} finally {
				if (br != null) {
					br.close();
				}
			}
		}
		return input;
	}

	private static String getInputFileName(int day) {
		StringBuilder sb = new StringBuilder();
		Formatter formatter = new Formatter(sb);
		formatter.format(INPUT_FILE_FORMAT, printNum(day, 2, '0'));
		formatter.close();
		return sb.toString();
	}

	public static String printNum(Number num, int length, char toAdd) {
		String formated = String.valueOf(num);

		while (formated.length() < length) {
			formated = toAdd + formated;
		}

		return formated;
	}

	public static String printNum(Number num, int length) {
		return printNum(num, length, ' ');
	}

	public static String gridToString(char[][] grid) throws IOException {
		StringBuilder sb = new StringBuilder();

		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[y].length; x++) {
				sb.append(grid[y][x]);
			}
			if (y < grid.length - 1) {
				sb.append("\n");
			}
		}
		return sb.toString();
	}

	public static void sysoGrid(char[][] grid) throws IOException {
		System.out.println(gridToString(grid));
	}

	public static void printGrid(int day, int task, char[][] grid, boolean publish) throws IOException {
		String printable = gridToString(grid);
		if (publish) {
			AdventUtils.publishExtra(day, task, printable, "grid");
		} else {
			AdventUtils.writeExtra(day, task, printable, "grid");
		}
	}

	public static <T> String printMap(Map<Tuple<Integer, Integer>, T> map, Function<T, String> printValue) {
		Integer smallestY = null;
		Integer biggestY = null;
		Integer smallestX = null;
		Integer biggestX = null;

		for (Tuple<Integer, Integer> pos : map.keySet()) {

			int y = pos.getLeft();
			int x = pos.getRight();

			if (smallestY == null || y < smallestY) {
				smallestY = y;
			}

			if (biggestY == null || y > biggestY) {
				biggestY = y;
			}

			if (smallestX == null || x < smallestX) {
				smallestX = x;
			}

			if (biggestX == null || x > biggestX) {
				biggestX = x;
			}
		}

		StringBuilder sb = new StringBuilder();

		for (int y = smallestY; y <= biggestY; y++) {
			for (int x = smallestX; x <= biggestX; x++) {
				sb.append(printValue.apply(map.get(Tuple.of(y, x))));
			}

			if (y < biggestY) {
				sb.append("\n");
			}
		}

		return sb.toString();
	}

	public static String getPrettyTimeElapsed(long start, long now) {
		long elapsed = now - start;

		long hours = (elapsed / 1000) / 60 / 60;
		long minutes = (elapsed / 1000) / 60;
		long seconds = (elapsed / 1000) % 60;
		long milli = elapsed % 1000;

		StringBuilder sb = new StringBuilder();

		if (hours != 0) {
			sb.append(hours);
			sb.append("h ");
		}
		if (minutes != 0) {
			sb.append(minutes);
			sb.append("m ");
		}
		if (seconds != 0) {
			sb.append(seconds);
			sb.append("s ");
		}
		if (milli != 0) {
			sb.append(milli);
			sb.append("ms ");
		}

		return sb.toString();

	}

}
