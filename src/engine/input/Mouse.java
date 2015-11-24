package engine.input;

public class Mouse {
	private static int xPos, yPos;
	private static boolean[] buttons;
	
	public static void updateMouse(int x, int y, boolean[] b) {
		xPos = x;
		yPos = y;
		buttons = b;
	}
	
	public static int getXPos() {
		return xPos;
	}
	
	public static int getYPos() {
		return yPos;
	}
	
	public static boolean[] getButtons() {
		return buttons;
	}
}
