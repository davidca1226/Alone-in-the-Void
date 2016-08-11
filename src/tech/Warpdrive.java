package tech;

import java.awt.Color;

import engine.Utility;
import engine.particle.ParticleController;
import entity.Entity;

public class Warpdrive {
	private int chargeRate, maxCapacity;
	private double maxVelocity, maxAcceleration;
	private double currentMaxAcceleration = 0, currentVelocity = 0;
	private int capacity;
	private Entity mount;
	private boolean warping = true;
	private boolean needsToStop;
	
	public Warpdrive(int chargeRate, int capacity,
			double maxAcceleration, double maxVelocity,
			Entity mount) {
		this.setChargeRate(chargeRate);
		this.setMaxCapacity(capacity);
		this.mount = mount;
		this.maxVelocity = maxVelocity;
		this.maxAcceleration = maxAcceleration + 1;
	}
	
	public void update(boolean charging) {
		if (charging && !warping) {
			capacity += chargeRate;
			if (capacity > maxCapacity)
				capacity = maxCapacity;
		}
	}
	
	public boolean sustainWarp(double totalDist, double desiredDist) {
		currentVelocity = Math.sqrt(mount.getXVelocity() * mount.getXVelocity() +
				mount.getYVelocity() * mount.getYVelocity());
		//System.out.println(totalDist + " . " + currentVelocity + " . " + desiredDist);
		if (totalDist < desiredDist || totalDist - currentVelocity <  desiredDist) {
			this.stopWarp();
			warping = false;
			return warping;
		} else return initiateWarp(); //temporary fix until i refine the difference more
	}
	
	
	public boolean initiateWarp() {
		
		
		if (currentMaxAcceleration == 0)
			currentMaxAcceleration = maxAcceleration - 1;
		
		currentMaxAcceleration *= maxAcceleration;
		
		ParticleController.createParticleField(mount.getXPos(), mount.getYPos(),
				1 * (int) (currentVelocity / 2),
				180, .1 * currentVelocity + 1, mount.getActualTheta(),
				15, Color.decode("#33ccff"), true);
		if (currentVelocity < maxVelocity) {
			//if (capacity > maxCapacity / 10) 
			//	warping = true;
			//if (warping) {
			//if () 

			mount.setXVelocity(mount.getXVelocity() + currentMaxAcceleration * Math.cos(Math.toRadians(mount.getActualTheta())));
			mount.setYVelocity(mount.getYVelocity() + currentMaxAcceleration * Math.sin(Math.toRadians(mount.getActualTheta())));
		}
		return warping;
	}
	
	public void stopWarp() {
		ParticleController.createParticleField(mount.getXPos(), mount.getYPos(),
				60, 360, .05 * currentVelocity, mount.getActualTheta(),
				60, Color.decode("#00ace6"), true);
		ParticleController.createParticleField(mount.getXPos(), mount.getYPos(),
				10, 800, .1 * currentVelocity, mount.getActualTheta(),
				5, Color.white, true);
		mount.setXVelocity(0);
		mount.setYVelocity(0);
	}
	
	public int getMaxCapacity() {
		return maxCapacity;
	}

	public void setMaxCapacity(int capacity) {
		this.maxCapacity = capacity;
	}

	public int getChargeRate() {
		return chargeRate;
	}

	public void setChargeRate(int chargeRate) {
		this.chargeRate = chargeRate;
	}
}
