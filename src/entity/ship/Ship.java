package entity.ship;

import java.awt.Color;

import tech.Shield;
import tech.Warpdrive;
import weapons.ammo.Missile;
import entity.Entity;
import entity.MovableEntityAbstract;

public abstract class Ship extends MovableEntityAbstract implements Entity {

	protected Entity target;

	protected boolean menuRender;
	
	protected boolean needToWarp = false;
	// Stats
	int shieldRadius;
	int shieldRate;
	int maxShield;
	double shieldBoost;

	int enginePower;
	double maxSpeed;
	double maxAcceleration;
	
	protected int age; //age in ticks
	
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
		
		if (this.totalDist > 300)
			needToWarp = true;
		else
			needToWarp = false;
		
		
		this.idealTheta = (Math.atan2(this.yTargetDist, this.xTargetDist) * 180)
				/ Math.PI;
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

	

}
