package engine.gui;

import java.awt.Color;
import java.awt.Graphics;
import weapons.Weapon;
import engine.Utility;
import entity.station.Station;

public class StationView extends GuiAbstract{
	
	private int segmentSize, segments, margin;
	
	private int[][] layout;
	
	private StationTile[][] tiles;
	private Weapon[] weapons;
	
	private Station station;
	
	public StationView(Station station, int xPos, int yPos, int squareSize) {
		margin = 35;
		this.station = station;
		xOrigin = xPos;
		yOrigin = yPos;
		this.squareSize = squareSize;
		layout = station.getLayout();
		segmentSize = (squareSize - margin * 2) / layout.length;
		segments = layout.length;
		
		tiles = new StationTile[segments][segments];
		for (int x = 0; x < segments; x++) {
			for (int y = 0; y < segments; y++) {
				tiles[x][y] = new StationTile(station,
						xOrigin + margin +(x * segmentSize),
						yOrigin + margin +(y * segmentSize),
						segmentSize,
						layout[y][x]);
			}	
		}
		weapons = station.getWeapons();
		int index = 0;
		for (int x = 0; x < segments; x++) {
			for (int y = 0; y < segments; y++) {
				if(tiles[x][y].getClassification() == 1) {
					tiles[x][y].setWeapon(weapons[index]);
					index++;
				}
			}	
		}
	}
	
	public void update() {
		updateMouse();
		
		for (int x = 0; x < segments; x++) {
			for (int y = 0; y < segments; y++) {
				tiles[x][y].update();
			}
		}
		
		weapons = station.getWeapons();
		int index = 0;
		for (int x = 0; x < segments; x++) {
			for (int y = 0; y < segments; y++) {
				if(tiles[x][y].getClassification() == 1) {
					tiles[x][y].setWeapon(weapons[index]);
					tiles[x][y].setIndex(index);
					index++;
				}
			}	
		}
		
		if (Utility.isWithinRect(mouseXPos, mouseYPos, xOrigin + margin, yOrigin + margin,
				tiles.length * segmentSize, tiles[0].length * segmentSize)) {
			tiles[((mouseXPos - xOrigin) - margin) / segmentSize]
					[((mouseYPos - yOrigin) - margin) / segmentSize].hasMouseFocus();
			if (buttons[1]) tiles[((mouseXPos - xOrigin) - margin) / segmentSize]
					[((mouseYPos - yOrigin) - margin) / segmentSize].leftButtonClick();
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(xOrigin, yOrigin, squareSize, squareSize);
		
		g.setColor(Color.red);
		
		for (int x = 0; x < segments; x++) {
			for (int y = 0; y < segments; y++) {
				tiles[x][y].render(g);
			}
		}
	}
}
