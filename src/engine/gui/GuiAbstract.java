package engine.gui;

import engine.input.Mouse;

public class GuiAbstract {
	
	protected int xOrigin, yOrigin, squareSize;
	protected int mouseXPos, mouseYPos;
	protected boolean buttons[];
	
	protected void updateMouse() {
		mouseXPos = Mouse.getXPos();
		mouseYPos = Mouse.getYPos();
		buttons = Mouse.getButtons();
	}
	
	public int getXOrigin() {
		return xOrigin;
	}
	
	public int getYOrigin() {
		return yOrigin;
	}
	
	public int getSquareSize() {
		return squareSize;
	}
}
