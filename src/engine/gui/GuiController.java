package engine.gui;

import java.awt.Color;
import java.awt.Graphics;

import entity.station.Station;

public class GuiController{
	
	private SpaceView spaceView;
	private StationView stationView;
	
	private int width, height;
	
	/**Should only be called once.**/
	public void init(Station station, int width, int height) {
		
		this.width = width;
		this.height = height;
		
		int spaceSquareSize = height - 100;
		int stationSquareSize = width - (150 + spaceSquareSize);
		
		spaceView = new SpaceView(50, 50, spaceSquareSize);
		stationView = new StationView(station, 100 + spaceSquareSize, 50, stationSquareSize);
		MenuController.init(100 + spaceSquareSize, stationSquareSize + 100, stationSquareSize);
		
		spaceView.setStation(station);
	}
	
	public void render(Graphics g) {
		g.setColor(Color.GRAY);
		if (spaceView == null || stationView == null) return;
		g.fillRect(0, 0, width, height);
		spaceView.render(g);
		stationView.render(g);
		
	}
	
	public void update() {
		spaceView.update();
		stationView.update();
	}

	
	public void moveSpaceView(int xAmount, int yAmount) {
		spaceView.moveX(xAmount, yAmount);
	}
	
}
