package engine.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import engine.controller.MainController;
import engine.grid.GridController;
import engine.particle.Particle;
import engine.particle.ParticleController;
import entity.Entity;
import entity.station.Station;

public class SpaceView extends GuiAbstract{
	
	private int xPosition, yPosition;
	
	private Station station;
	
	private int xMoveVelocity, yMoveVelocity;
	private int xMoveTally, yMoveTally;
	
	public SpaceView(int xOrigin, int yOrigin, int squareSize) {
		this.xOrigin = xOrigin;
		this.yOrigin = yOrigin;
		this.squareSize = squareSize;
		
		xPosition = 0;
		yPosition = 0;

	}
	
	public void update() {
		updateMouse();
		ParticleController.update();
	}
	
	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(xOrigin, yOrigin, squareSize, squareSize);
		
		List<Entity> tempList = MainController.getFighterList();
		for (int i = 0; i < tempList.size(); i++) {
				tempList.get(i).render(g, xOrigin, yOrigin, xPosition, yPosition, squareSize);
		}
		
		List<Particle> particleList = ParticleController.getParticles();
		if (particleList != null){
			for (int i = 0; i < particleList.size(); i++) {
					particleList.get(i).render(g, xOrigin, yOrigin, xPosition, yPosition, squareSize);
			}
		}
		
		if (station != null)
				station.render(g, xOrigin, yOrigin, xPosition, yPosition, squareSize);
	}

	public void moveX(int xAmount, int yAmount) {
		xPosition += xAmount;
		yPosition += yAmount;		
	}
	
	public void setStation (Station station) {
		this.station = station;
	}

	public void resetPosition() {
		xPosition = (int) station.getXPos();
		yPosition = (int) station.getYPos();
	}
}
