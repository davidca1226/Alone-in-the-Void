package engine.grid;

import entity.Entity;

public class GridController {

	final static int gridSize = 50;

	private static boolean init = false;

	static int xAmount;
	static int yAmount;

	// static List<GridTile> tiles = new ArrayList<GridTile>();
	private static GridTile[][] tiles;

	/**
	 * accepts xSize and ySize, returns if they work relative to grid size. if
	 * returns false, try again
	 **/
	public static boolean init(int xSize, int ySize) {

		xAmount = Math.round(xSize / gridSize);
		yAmount = Math.round(ySize / gridSize);

		tiles = new GridTile[xAmount][yAmount];

		for (int x = 0; x < xAmount; x++) {
			for (int y = 0; y < yAmount; y++) {
				tiles[x][y] = new GridTile(x, y, gridSize);
			}
		}

		init = true;

		return true;
	}

	public static void addEntity(Entity e) {
		if (!init)
			return;

		int targetXPos = (int) Math.round(e.getXPos());
		int targetYPos = (int) Math.round(e.getYPos());

		tiles[targetXPos / gridSize][targetYPos / gridSize].addEntity(e);
	}

	public static void updateAll() {
		if (!init)
			return;
		for (int x = 0; x < xAmount; x++) {
			for (int y = 0; y < yAmount; y++) {
				tiles[x][y].update();
			}
		}
	}

	public static Entity getNearestEntity(int targetXPos, int targetYPos) {
		if (!init)
			return null;
		return tiles[targetXPos / gridSize][targetYPos / gridSize]
				.returnClosest(targetXPos, targetYPos);
	}

	public static void removeEntity(Entity e) {

		tiles[e.getXGrid()][e.getYGrid()].removeEntity(e);

	}
}
