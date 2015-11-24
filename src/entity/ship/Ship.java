package entity.ship;

import java.awt.Color;

import tech.Shield;
import weapons.ammo.Missile;
import entity.Entity;

public abstract class Ship implements Entity {

	protected Shield shield;
	protected Missile incomingMissile;

	protected Entity target;

	protected boolean menuRender;

	// Stats
	int shieldRadius;
	int shieldRate;
	int maxShield;
	int maxHealth;
	double shieldBoost;

	int enginePower;
	double maxSpeed;
	double maxAcceleration;
	int health;
	
	protected int age; //age in ticks
	
	boolean evasive = false;
	boolean shouldRemove = false;
	boolean hasMouseFocus = false;

	Color color;

	int scale;

	//

	double speed = 0;
	double idealTheta;
	double maxRotation;
	double actualTheta;
	double xVelocity = 0;
	double yVelocity = 0;
	double xPos;
	double yPos;
	int xIndex;
	int yIndex;
	double rotationAmount = 0;

	double xTargetDist;
	double yTargetDist;

	double targetXPos;
	double targetYPos;

	double xLimit;
	double yLimit;

	protected Object lock;

	public void setTarget(Entity e) {
		this.target = e;
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

	public double getDPS() {
		// TODO make this actually work
		return 1;
	}

	public void dealDamage(int damage) {
		int remainingDamage = this.shield.damage(damage);
		this.health -= remainingDamage;
	}

	public void missileLock(Missile m) {
		this.incomingMissile = m;
		this.evasive = true;
	}

	public void setGridLocation(int xIndex, int yIndex) {
		this.xIndex = xIndex;
		this.yIndex = yIndex;
	}
	
	public void setMoveTarget(Entity target) {

		if (target == null)
			return;	
		//if (this.evasive) {
		//	this.targetXPos = (2 * this.xPos) - this.incomingMissile.getXPos();
		//	this.targetYPos = (2 * this.yPos) - this.incomingMissile.getYPos();
		//
		//	return;
		//}
		
		

		this.targetXPos = target.getXPos();
		this.targetYPos = target.getYPos();
	}
	
	protected void move() {
		
		this.xTargetDist = (this.xPos - this.targetXPos);
		this.yTargetDist = (this.yPos - this.targetYPos);
		
		
		if (this.speed <= this.maxSpeed) {
			this.speed += this.maxAcceleration;
		}
		if ((Math.abs(this.xTargetDist) < this.scale)
				&& (Math.abs(this.yTargetDist) < this.scale)) {
			this.speed = 0;
		}
		this.idealTheta = (Math.atan2(this.yTargetDist, this.xTargetDist) * 180)
				/ Math.PI;
		this.idealTheta += 180;

		//if (this.evasive) {
		//	this.idealTheta += 20;
		//}
		
		this.rotateTo(this.idealTheta);

		this.xVelocity += this.maxAcceleration
				* Math.cos(Math.toRadians(this.actualTheta));
		this.yVelocity += this.maxAcceleration
				* Math.sin(Math.toRadians(this.actualTheta));
		this.xVelocity *= .98;
		this.yVelocity *= .98;
		this.xPos += this.xVelocity;
		this.yPos += this.yVelocity;
	}


	protected void rotateTo(double targetRotation) {
		if (targetRotation >= 360) {
			targetRotation -= 360;
		}
		if (targetRotation <= 0) {
			targetRotation += 360;
		}

		if (Math.abs(targetRotation - this.actualTheta) > this.maxRotation) {
			this.rotationAmount = this.maxRotation;
		} else {
			this.rotationAmount = Math.abs(targetRotation - this.actualTheta);
		}
		if (Math.abs(this.actualTheta - this.idealTheta) < .05)
			return;

		if ((((this.actualTheta - targetRotation) + 360) % 360) > 180) {
			this.actualTheta += this.rotationAmount;
		} else {
			this.actualTheta -= this.rotationAmount;
		}

		if (this.actualTheta >= 360) {
			this.actualTheta -= 360;
		}
		if (this.actualTheta <= 0) {
			this.actualTheta += 360;
		}
	}

	
	
	public int getXGrid() {
		return this.xIndex;
	}

	public int getYGrid() {
		return this.yIndex;
	}
	
	public void hasMouseFocus(boolean b) {
		hasMouseFocus = b;
	}
}
