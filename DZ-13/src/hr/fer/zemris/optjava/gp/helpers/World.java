package hr.fer.zemris.optjava.gp.helpers;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * The class holds information about the world.
 */
public class World {
	private static int height;
	private static int width;
	public static int[][] map;

	/**
	 * Initializes world by reading data from file.
	 * 
	 * @param filename
	 *            file path
	 */
	public static void initialize(String filename) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
		String line = reader.readLine();
		String[] tmp = line.split("x");
		width = Integer.parseInt(tmp[0]);
		height = Integer.parseInt(tmp[1]);
		map = new int[height][width];
		for (int i = 0; i < height; i++) {
			line = reader.readLine();
			tmp = line.split("");
			for (int j = 0; j < width; j++) {
				if (tmp[j].equals("1")) {
					map[i][j] = 1;
				} else {
					map[i][j] = 0;
				}
			}
		}
		reader.close();
	}

	/**
	 * return Copy of the map of the world
	 */
	public static int[][] getMapCopy() {
		int[][] mapCopy = new int[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				mapCopy[i][j] = map[i][j];
			}
		}
		return mapCopy;
	}

	public static int getHeight() {
		return height;
	}

	public static int getWidth() {
		return width;
	}

}
