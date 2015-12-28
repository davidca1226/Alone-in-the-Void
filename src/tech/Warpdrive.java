package tech;

import java.awt.Color;

import engine.Utility;
import engine.particle.ParticleController;
import entity.Entity;

public class Warpdrive {
	private int chargeRate, maxCapacity;
	private double maxVelocity, maxAcceleration;
	private int capacity;
	private Entity mount;
	private boolean warping;
	
	public Warpdrive(int chargeRate, int capacity,
			double maxAcceleration, double maxVelocity,
			Entity mount) {
		this.setChargeRate(chargeRate);
		this.setMaxCapacity(capacity);
		this.mount = mount;
		this.maxVelocity = maxVelocity;
		this.maxAcceleration = maxAcceleration;
	}
	
	public void update(boolean charging) {
		if (charging && !warping) {
			capacity += chargeRate;
			if (capacity > maxCapacity)
				capacity = maxCapacity;
		}
	}
	
	public boolean initiateWarp() {
		double currentVelocity = Math.sqrt(mount.getXVelocity() * mount.getXVelocity() +
				mount.getYVelocity() * mount.getYVelocity());
		ParticleController.createParticleField(mount.getXPos(), mount.getYPos(),
				1, 180, .25 * currentVelocity, mount.getActualTheta(),
				2, Color.decode("#33ccff"), true);
		if (currentVelocity < maxVelocity) {
			//if (capacity > maxCapacity / 10) 
			//	warping = true;
			//if (warping) {
			//if () 

			mount.setXVelocity(mount.getXVelocity() + Math.cos(Math.toRadians(mount.getActualTheta())));
			mount.setYVelocity(mount.getYVelocity() + Math.sin(Math.toRadians(mount.getActualTheta())));
			return true;
		}
		else return false;
	}
	
	public void stopWarp() {
		mount.setXVelocity(0);
		mount.setYVelocity(0);
		ParticleController.createParticleField(mount.getXPos(), mount.getYPos(),
				30, 360, 1, mount.getActualTheta(),
				20, Color.decode("#00ace6"), true);
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
