package engine.gui;

import java.awt.Color;
import java.awt.Graphics;

import entity.station.Station;
import weapons.Laser;
import weapons.MissileLauncher;
import weapons.Weapon;

public class StationTile {
	
	private int xPos, yPos, squareSize, position;
	private int classification;
	private int index;
	
	private boolean mouseFocus, doClickAction;
	private int focusReset, clickReset;
	
	private Weapon weapon;
	private Station station;
	
	private Menu menu;
	
	public StationTile(Station station, int xPos, int yPos, int squareSize, int classification) {
		this.station = station;
		this.xPos = xPos;
		this.yPos = yPos; 
		this.squareSize = squareSize;
		this.classification = classification;
	}

	public void update() {
		
		if (doClickAction)
			doClickAction();
		
		if (menu != null)
			if (!menu.isActive())
				menu = null;
			
		focusReset++;
		clickReset++;
		if (focusReset >= 1)
			mouseFocus = false;
		if(clickReset >= 1) {	
			clickReset = 0;
			doClickAction = false;
		}
		if (menu != null) {
			if (classification == 1)
				switch (menu.getSelectedOption()) {
				case 1: station.addWeapon(new Laser(station, 10, 10, 200 ), index);
				break;
				case 2: station.addWeapon(new MissileLauncher(station, 16, 10, 100), index); 
				break;
				}
			
				
		}
			
	}
	
	public void doClickAction() {
		if (menu==null)
			if (Math.random() > .5)
				switch (classification) {
				case 0: return;
				case 1: this.menu = MenuController.addMenu(new String[] {"add laser", "add missile", "n/a", "n/a", "n/a"});
				return;
				case 2: this.menu = MenuController.addMenu(new String[] {"n/a", "n/a", "n/a", "n/a", "n/a"});
				return;
				}
	}
	
	public void hasMouseFocus() {
		focusReset = 0;
		mouseFocus = true;
	}
	
	public void leftButtonClick() {
		doClickAction = true;
	}
	
	public void render(Graphics g) {
		switch (classification) {
		case 0: return;
		case 1: g.setColor(Color.DARK_GRAY);
		break;
		case 2: g.setColor(Color.LIGHT_GRAY);
		break;
		}
		
		if (doClickAction) g.setColor(Color.orange);
		g.fillRect(xPos, yPos, squareSize, squareSize);
		
		g.setColor(Color.orange);
		if(weapon != null) 
			g.drawString(weapon.getAbbreviation(), xPos, yPos + 10);
		
		g.setColor(Color.black);
		g.drawRect(xPos, yPos, squareSize, squareSize);
		
		if (this.menu != null) {
			g.setColor(Color.blue);
			g.drawRect(xPos + 1, yPos + 1, squareSize - 2, squareSize - 2);
			g.drawRect(xPos + 2, yPos + 2, squareSize - 4, squareSize - 4);
		}
	}
	
	public int getClassification() {
		return classification;
	}
	
	public void setWeapon(Weapon w) {
		weapon = w;
	}
	
	public void setIndex(int i) {
		index = i;
	}
}
