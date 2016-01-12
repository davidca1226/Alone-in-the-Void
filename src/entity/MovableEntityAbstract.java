package entity;

import tech.Shield;
import weapons.ammo.Missile;
import weapons.ammo.Projectile;

public class MovableEntityAbstract {
	
	protected Shield shield;
	protected Missile incomingMissile;
	protected int health;
	protected int scale;
	protected int maxHealth;
	protected double speed = 0;
	protected double idealTheta;
	protected double maxRotation;
	protected double actualTheta;
	protected double xVelocity = 0;
	protected double yVelocity = 0;
	protected double xPos;
	protected double yPos;
	int xIndex;
	int yIndex;
	protected double rotationAmount = 0;
	
	protected boolean hasMouseFocus = false;
	
	
	public int getXGrid() {
		return this.xIndex;
	}

	public int getYGrid() {
		return this.yIndex;
	}
	
	public void hasMouseFocus(boolean b) {
		hasMouseFocus = b;
	}

	public void missileLock(Missile m) {
		this.incomingMissile = m;
	}
	
	public void setXVelocity(double xVelocity) {
		this.xVelocity = xVelocity;
	}
	
	public double getXVelocity() {
		return this.xVelocity;
	}
	
	public void setYVelocity(double yVelocity) {
		this.yVelocity = yVelocity;
	}
	
	public double getYVelocity() {
		return this.yVelocity;
	}
	
	public double getActualTheta() {
		return this.actualTheta;
	}

	public void setGridLocation(int xIndex, int yIndex) {
		this.xIndex = xIndex;
		this.yIndex = yIndex;
	}
	
	
	public void dealDamage(int damage) {
		int remainingDamage = this.shield.damage(damage);
		this.health -= remainingDamage;
	}
	
	public boolean isDestroyed() {
		if (this.health <= 0)
			return true;
		else
			return false;
	}
	
	public double getXPos() {
		return this.xPos;
	}

	public double getYPos() {
		return this.yPos;
	}

	public int getHealth() {
		return this.health;
	}

	public int getScale() {
		return this.scale;
	}

	public int getMaxHealth() {
		return this.maxHealth;
	}
	
	public void dealImpactDamage(Projectile projectile) {
		
	}
	
}
