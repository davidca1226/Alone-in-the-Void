package weapons.ammo;

import java.awt.Color;
import java.awt.Graphics;

import engine.Utility;
import engine.particle.ParticleController;
import entity.Entity;

public class Missile implements Projectile{
	
	Entity target;
	
	int damage;
	double maxAcceleration = .02;
	double maxRotation = 3;
	int scale = 2;
	int fuel = 3000000;
	int health, maxHealth = 10;
	
	double xTargetDist;
	double yTargetDist;
	
	double targetXCenter;
	double targetYCenter;
	
	double speed = 0;
	double cruisingSpeed = .5;
	double idealTheta, idealThetaCorrected;
	double actualTheta;
	double velocityAngle; //direction it's going
	double rotationAmount= 3;
	double xVelocity = 0;
	double yVelocity = 0;
	double xPos;
	double yPos;
	boolean active = true;
	boolean targetActive = false, drifting = false;
	int driftingTimer = 0; //limited drift time
	
	boolean hasMouseFocus = false;
	
	public Missile(Entity target, double xPos, double yPos, int damage) {
		this.target = target;
		if (target != null)
			targetActive = true;
		this.xPos = xPos;
		this.yPos = yPos;
		this.damage = damage;
		
		this.xVelocity = 2 * Math.random() - 1;
		this.yVelocity = 2 * Math.random() - 1;
		health = maxHealth;
		
		target.missileLock(this);
	}
	
	public void update() {
		if (!active) return;
		if (health <= 0) {
			destroyed();
		}
		if (target.getHealth() <= 0) targetDestroyed();
		
		targetXCenter = target.getXPos() + (target.getScale() / 2);
		targetYCenter = target.getYPos() + (target.getScale() / 2);
		if ((Utility.isOnCourse(xPos, yPos, target.getXPos(), target.getYPos(), xVelocity, yVelocity) && speed >= cruisingSpeed)
				|| fuel <= 0 || !targetActive) {
			drifting = true;
		} else {
			drifting = false;
		}

		move();
			
		
		if (drifting) {
		driftingTimer++;
		if (driftingTimer > 600 && !targetActive)
			destroyed();
		}
		
		if (Utility.calculateCollision(target.getScale(), xPos, yPos,
				targetXCenter, targetYCenter) && targetActive) impact();
	}
	
	
	
	public void targetDestroyed() {
		targetActive = false;
	}
	
	public void destroyed() {
		ParticleController.createParticleField(xPos, yPos, 15,
				this.speed * .25, this.actualTheta , 15);
		active = false;
	}
	
	public void impact() {
		ParticleController.createParticleField(xPos, yPos, 15,
				this.speed * .25, this.actualTheta , 15);
		target.dealDamage(damage);
		active = false;
	}
	
	public void move() {
		if (!drifting && fuel >= 0)
			fuel--;
		
		if (!drifting) {
			if (!(fuel <= 0)) {
				ParticleController.createNewParticle(xPos, yPos, 1, 25, this.speed * .25, this.actualTheta - 180);
				xTargetDist = (targetXCenter - xPos);
				yTargetDist = (targetYCenter - yPos);
		
				idealTheta = Utility.standardizeAngle(Math.toDegrees(Math.atan2(yTargetDist, xTargetDist)));
				velocityAngle = Utility.standardizeAngle(Math.toDegrees(Math.atan2(yVelocity, xVelocity)));
				idealThetaCorrected = Utility.standardizeAngle((velocityAngle) - 2 * (velocityAngle - idealTheta));
				if (Math.abs(idealThetaCorrected - idealTheta) > 90)
					idealThetaCorrected = Utility.standardizeAngle(velocityAngle + 180);
				
				rotateTo(idealThetaCorrected);
				if (Math.abs(idealThetaCorrected - actualTheta) % 360 < 8 ) {
					
					xVelocity += maxAcceleration * Math.cos(Math.toRadians(actualTheta));
					yVelocity += maxAcceleration * Math.sin(Math.toRadians(actualTheta));
				}
			}
		}
	    xPos += xVelocity;
	    yPos += yVelocity;
	    
	    speed = Math.sqrt(xVelocity * xVelocity + yVelocity * yVelocity);
	}
	
	private void rotateTo(double targetRotation) {
		targetRotation = Utility.standardizeAngle(targetRotation);
		actualTheta = Utility.standardizeAngle(actualTheta);
		
		if(actualTheta < targetRotation) {
			if(Math.abs(actualTheta - targetRotation)<180) {
				actualTheta += rotationAmount;
			}
			else {
				actualTheta -= rotationAmount;
			}
		
		} else {
			if(Math.abs(actualTheta - targetRotation)<180) {
				actualTheta -= rotationAmount;
			}
			else {
				actualTheta += rotationAmount;
			}
		}
	}
	
	public void render(Graphics g, int xScreenOrigin, int yScreenOrigin,
			int xScreenPosition, int yScreenPosition, int screenSize) {
		if (!active) return;
		g.setColor(Color.blue);
		if (xPos >= xScreenPosition && xPos <= xScreenPosition + screenSize &&
				yPos >= yScreenPosition && yPos <= yScreenPosition + screenSize) 
			g.drawRect(xScreenOrigin + (int) xPos - xScreenPosition,
					yScreenOrigin + (int) yPos - yScreenPosition,
					scale, scale);
		
		
	}
	
	public boolean isActive() {
		return active;
	}
	
	public double getXPos() {
		return xPos;
	}

	@Override
	public double getYPos() {
		return yPos;
	}

	@Override
	public double getTheta() {
		return actualTheta;
	}

	@Override
	public double getSpeed() {
		return speed;
	}

	public int getScale() {
		return scale;
	}

	public int getHealth() {
		return health;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	@Override
	public int getXGrid() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getYGrid() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setGridLocation(int xIndex, int yIndex) {
		// TODO Auto-generated method stub
		
	}

	public void hasMouseFocus(boolean b) {
		hasMouseFocus = b;
	}

	public void dealDamage(int damage) {
		health -= damage;
	}

	@Override
	public void missileLock(Missile missle) {
		// TODO make this work. counter missiles sound interesting AF. small, low damage, high manuverability, low fuel.
		
	}

	public void setMoveTarget(Entity target) {
		this.target = target;
		
	}

	public double getDPS() {
		return this.damage;
	}
}
