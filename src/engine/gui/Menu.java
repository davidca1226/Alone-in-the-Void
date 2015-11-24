package engine.gui;

import java.awt.Color;
import java.awt.Graphics;

public class Menu {

	private int xPos, yPos, xSize, ySizePer;
	private String[] options;
	
	private int selectedOption;
	
	private boolean active;
	
	protected Menu(int xPos, int yPos, int heightPer, int totalWidth, String[] options) {
		active = true;
		this.xPos = xPos;
		this.yPos = yPos;
		this.xSize = totalWidth;
		this.ySizePer = heightPer;
		
		this.options = options;
	}
	
	protected void update() {
		
	}
	
	protected void render(Graphics g) {
		g.setColor(Color.blue);
		for (int i = 0; i < options.length; i++)
		{
			g.setColor(Color.blue);
			g.drawString(options[i], xPos, yPos + 10 + (ySizePer * i));
			g.setColor(Color.DARK_GRAY);
			g.drawRect(xPos, yPos + (ySizePer * i), xSize, ySizePer);
		}
	}
	
	public void deactivate() {
		active = false;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public void selectOption(int option) {
		selectedOption = option;
	}
	
	public int getSelectedOption() {
		int temp = selectedOption;
		selectedOption = 0;
		return temp;
	}
	
	public int getLength() {
		return options.length;
	}
	
	
}
