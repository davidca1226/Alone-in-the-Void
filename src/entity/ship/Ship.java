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
	protected double xVelocity = 0;
	protected double yVelocity = 0;
	protected double xPos;
	protected double yPos;
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

	double xLimit;
	double yLimit;

	protected Object lock;

	public void setTarget(Entity e) {
		this.target = e;
	}

	public double getDPS() {
		// TODO make this actually work
		return 1;
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
		
		
		this.xTargetDist = (this.targetXPos - this.xPos);
		this.yTargetDist = (this.targetYPos - this.yPos);
		this.totalDist = Math.sqrt(xTargetDist * xTargetDist + yTargetDist * yTargetDist);
		
		this.speed = Math.sqrt(xVelocity * xVelocity + yVelocity * yVelocity);
		
		if (this.totalDist > desiredTargetDistance * 4)
			needToWarp = true;
		else
			needToWarp = false;
		
		
		if (this.totalDist > this.desiredTargetDistance) 
			this.idealTheta = (Math.atan2(this.yTargetDist, this.xTargetDist) * 180)
				/ Math.PI;
		else if (this.speed > this.maxSpeed / 10) 
			this.idealTheta = Utility.standardizeAngle(((Math.atan2(this.yTargetDist, this.xTargetDist) * 180)
					/ Math.PI) + 180);
			
		
		
		this.rotateTo(this.idealTheta);

		if ((needToWarp || warping) && idealTheta - actualTheta < .1 && 
				idealTheta - actualTheta > -.1 && warpdrive != null) 
		{
			if (this.speed <.1 || warping) 
			{
				warping = true;
				if (needToWarp) {
					warpdrive.initiateWarp();	
				} else {
					System.out.println("ASDFAGFA");
					warpdrive.stopWarp();
					warping = false;
				}
				
			} else {
				this.xVelocity -= this.maxAcceleration 
						* Math.signum(this.xVelocity);
				this.yVelocity -= this.maxAcceleration
						* Math.signum(this.yVelocity);
			}
		} else {
			this.xVelocity += this.maxAcceleration
					* Math.cos(Math.toRadians(this.actualTheta));
			this.yVelocity += this.maxAcceleration
					* Math.sin(Math.toRadians(this.actualTheta));
		}
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
			rotationAmount = this.maxRotation;
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
