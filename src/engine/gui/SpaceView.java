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
		System.out.println(yPosition);
		updateMouse();
		ParticleController.update();
	}
	
	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(xOrigin, yOrigin, squareSize, squareSize);
		
		List<Entity> tempList = MainController.getFighterList();
		for (int i = 0; i < tempList.size(); i++) {
				tempList.get(i).render(g, xPosition, yPosition, xOrigin, yOrigin, squareSize);
		}
		
		List<Particle> particleList = ParticleController.getParticles();
		if (particleList != null){
			for (int i = 0; i < particleList.size(); i++) {
				if (particleList.get(i).getXPos() + xPosition > xOrigin &&
						particleList.get(i).getXPos() + xPosition < xOrigin + squareSize &&
						particleList.get(i).getYPos() + yPosition > yOrigin &&
						particleList.get(i).getYPos() + yPosition < yOrigin + squareSize)
					particleList.get(i).render(g, xPosition, yPosition);
			}
		}
		
		
		
		if (station != null)
				station.render(g, xPosition, yPosition, xOrigin, yOrigin, squareSize);
		
	}

	public void moveX(int xAmount, int yAmount) {
		xPosition += xAmount;
		yPosition += yAmount;
		System.out.println(xPosition);
		System.out.println(yPosition);
	}
	
	public void setStation (Station station) {
		this.station = station;
	}
}
