package engine.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import engine.Utility;
import engine.input.Mouse;
import engine.particle.ParticleController;

public class MenuController {

	private static int xPos, yPos;
	
	private static int mouseXPos, mouseYPos;
	
	private static  boolean[] buttons;
	
	private static boolean init = false;
	
	static Menu currentMenu;
	
	private static int defaultHeight = 15;
	private static int defaultWidth = 80;
	private static int totalHeight;
	private static int totalWidth;
	
	public static void init (int xMenuPos, int yMenuPos, int xMenuSize) {
		
		if (init) return;
		
		xPos = xMenuPos;
		yPos = yMenuPos;
		totalWidth = xMenuSize;
		
		init = true;
	}
	
	public static Menu addMenu(String[] options) {
		
		if (currentMenu != null)
			removeCurrentMenu();
		
		currentMenu = new Menu(xPos, yPos, defaultHeight, totalWidth, options);
		
		totalHeight = currentMenu.getLength() * defaultHeight;
		
		return currentMenu;
		 
	}
	
	public static void update() {
		updateMouse();
		
		if (currentMenu == null) return;
		currentMenu.update();
		if (Utility.isWithinRect(mouseXPos, mouseYPos, xPos, yPos, totalWidth, totalHeight))
			if (buttons[1]) {
				currentMenu.selectOption((mouseYPos - yPos) / defaultHeight + 1);
			}
			
	}
	
	public static void updateMouse() {
		mouseXPos = Mouse.getXPos();
		mouseYPos = Mouse.getYPos();
		buttons = Mouse.getButtons();
	}
	
	public static void render(Graphics g) {
		
		if (currentMenu != null)
			currentMenu.render(g);
		g.setColor(Color.black);
		g.drawRect(xPos, yPos, totalWidth, totalHeight);
		
	}
	
	public static void removeMenu(Menu menu) {
		if (menu == currentMenu)
			currentMenu = null;
	}
	
	private static void removeCurrentMenu() {
		currentMenu.deactivate();
		currentMenu = null;
	}
	
	
}
