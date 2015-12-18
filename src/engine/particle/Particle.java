package engine.particle;

import java.awt.Color;
import java.awt.Graphics;

public class Particle {
	
	double xPos, yPos;
	double xVelocity, yVelocity;
	int duration, durationCounter, size;
	int darkenInterval, darkenIterationsAmount = 50; //minus two, for first and last. particle does not spawn darkened.
	Color color = Color.YELLOW;                      //(cont.) should be twice amount due to randomness
	
	double movementSpeed, movementAngle;
	boolean moving = false;
	
	public Particle(double xPos, double yPos, int size, int duration, 
			double movementSpeed, double movementAngle) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.duration = duration;
		this.size = size;
		this.movementSpeed = movementSpeed;
		this.movementAngle = movementAngle;
		if (movementSpeed != 0)
			moving = true;
		durationCounter = 0;
		darkenInterval = duration / darkenIterationsAmount;
	}
	 
	public void update() {
		durationCounter++;
		if (durationCounter >= darkenInterval) {
			if (Math.random() < .5) {
				color = color.darker();
			}
			darkenInterval += duration/darkenIterationsAmount;
		}
		
		if (!moving)
			return;
		
		this.xVelocity = this.movementSpeed
				* Math.cos(Math.toRadians(this.movementAngle));
		this.yVelocity = this.movementSpeed
				* Math.sin(Math.toRadians(this.movementAngle));
		this.movementSpeed *= .98;
		this.xPos += this.xVelocity;
		this.yPos += this.yVelocity;
		
	}
	
	public boolean shouldBeRemoved() {
		if (durationCounter >= duration)
				return true;
		return false;
	}
	
	public void render(Graphics g, int xScreenOrigin, int yScreenOrigin,
			int xScreenPosition, int yScreenPosition, int screenSize) {
		g.setColor(this.color);
		if (xPos >= xScreenPosition && xPos <= xScreenPosition + screenSize &&
				yPos >= yScreenPosition && yPos <= yScreenPosition + screenSize) 
			g.drawRect(xScreenOrigin + (int) xPos - xScreenPosition,
					yScreenOrigin + (int) yPos - yScreenPosition,
					size, size);
	}
	
	public double getXPos() {
		return xPos;
	}
	
	public double getYPos() {
		return yPos;
	}

}
