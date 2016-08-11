package entity.ship;

import java.awt.Color;

import tech.Shield;
import tech.Warpdrive;
import weapons.ammo.Missile;
import weapons.ammo.Projectile;
import engine.Utility;
import entity.Entity;

public abstract class Ship implements Entity {

	protected boolean menuRender;
	
	protected boolean needToWarp = false;
	// Stats
	int shieldRadius;
	int shieldRate;
	
	protected Entity target;
	
	protected Shield shield;
	protected Missile incomingMissile;
	protected int health;
	protected int scale;
	protected int maxHealth;
	protected double speed = 0;
	protected int enginePower;
	protected double maxSpeed;
	protected double maxAcceleration;
	protected double idealTheta;
	protected double maxRotation;
	protected double actualTheta;
	protected double xVelocity = 1;
	protected double yVelocity = 1;
	protected double xPos;
	protected double yPos;
	protected double lastDist;
	protected double decelDistance;
	protected boolean accelerating;
	int xIndex;
	int yIndex;
	protected double rotationAmount = 0;
	
	int maxShield;
	double shieldBoost;


	
	protected int age; //age in ticks
	
	protected int desiredTargetDistance;
	
	boolean evasive = false;
	boolean warping = false;
	boolean shouldRemove = false;
	boolean hasMouseFocus = false;

	Color color;

	protected Warpdrive warpdrive;

	double xTargetDist;
	double yTargetDist;
	double totalDist;

	double targetXPos;
	double targetYPos;

	protected Object lock;

	public void setTarget(Entity e) {
		this.target = e;
		setMoveTarget(target);
	}

	public double getDPS() {
		// TODO make this actually work
		return 1;
	}
	
	public void setMoveTarget(Entity target) {
		
		if (target == null)
			return;	
		this.targetXPos = target.getXPos();
		this.targetYPos = target.getYPos();
	}
	
	protected void move() {
		
		
		this.xTargetDist = (this.targetXPos - this.xPos);
		this.yTargetDist = (this.targetYPos - this.yPos);
		this.totalDist = Math.sqrt(xTargetDist * xTargetDist + yTargetDist * yTargetDist);
		this.speed = Math.sqrt(xVelocity * xVelocity + yVelocity * yVelocity);
		this.decelDistance = Math.pow(speed, 2) / (2 * maxAcceleration);
		
		this.idealTheta = (Math.atan2(this.yTargetDist, this.xTargetDist) * 180)
				/ Math.PI;
		if (warping) {
			this.rotateTo(idealTheta);
			this.warping = this.warpdrive.sustainWarp(this.totalDist, this.desiredTargetDistance);
			return;
		}
		if (this.totalDist > desiredTargetDistance * 10) {
			needToWarp = true;
		} else {
			needToWarp = false;
		}
		
		if (needToWarp) {
			
			if (this.speed > .001) {
				this.decelerate();
				return;
			}
			if (this.speed <= .001) {
				this.rotateTo(idealTheta);
				needToWarp = false;
				warping = true;
				this.warpdrive.initiateWarp();
				return;
			}
		}
		
		if (Math.abs(this.desiredTargetDistance - (this.totalDist)) > this.scale / 2 && 
				(!warping || needToWarp)) { //if not happy with current pos
			if (this.decelDistance <= Math.abs(this.totalDist - this.desiredTargetDistance)) {
				this.decelerate();
			} else {
				this.rotateTo(idealTheta);
				this.xVelocity += this.maxAcceleration
						* Math.cos(Math.toRadians(this.actualTheta));
				this.yVelocity += this.maxAcceleration
						* Math.sin(Math.toRadians(this.actualTheta));
			}
			
		}
		
		
	}
	
	protected void changePos() {
		this.xPos += this.xVelocity;
		this.yPos += this.yVelocity;
	}

	protected void decelerate() {
		double theta = Utility.standardizeAngle(((Math.atan2(this.yVelocity, this.xVelocity) * 180)
				/ Math.PI) - 180);
		if (this.xVelocity < this.maxAcceleration * Math.cos(Math.toRadians(theta)) || this.xVelocity == 0) {
			this.xVelocity = 0;
		} else {
			this.xVelocity += this.maxAcceleration
					* Math.cos(Math.toRadians(theta));
		}
		if (this.yVelocity < this.maxAcceleration * Math.sin(Math.toRadians(theta)) || this.yVelocity == 0) {
			this.yVelocity = 0;
		} else { 
			this.yVelocity += this.maxAcceleration
					* Math.sin(Math.toRadians(theta));
		}
		this.xPos += this.xVelocity;
		this.yPos += this.yVelocity;
	}

	protected void rotateTo(double targetRotation) {
		this.actualTheta = targetRotation;
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
