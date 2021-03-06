package entity.ship;

import java.awt.Color;
import java.awt.Graphics;

import tech.Shield;
import tech.Warpdrive;
import engine.Game;
import entity.Entity;

public class Fighter extends Ship implements Entity {

	double xPosAct;
	double yPosAct;

	boolean asd;

	double rotationAmount;

	public Fighter(double xPos, double yPos) {
		
		warpdrive = new Warpdrive(10, 300, .01, 80, this);
		
		this.shieldRadius = 3;
		this.shieldRate = 3;
		this.maxShield = 100;
		this.shieldBoost = 1;
		this.maxAcceleration = .02;

		this.color = Color.red;

		this.enginePower = 10;
		this.maxSpeed = 1.25;
		this.maxHealth = 1000;
		this.health = this.maxHealth;
		this.maxRotation = 30;
		
		this.desiredTargetDistance = 300;

		this.scale = 1;
		this.age = 0;

		this.xPos = xPos;
		this.yPos = yPos;

		this.shield = new Shield(this.shieldRadius, this.shieldRate,
				this.maxShield);

		this.lock = new Object();
	}

	@Override
	public void update() {
		this.setMoveTarget(this.target);
		
		if (this.evasive
				&& ((this.incomingMissile.isActive() == false) || (this.incomingMissile == null))) {
			this.evasive = false;
		}

		this.move();
		this.changePos();
		this.shield.recharge(this.shieldBoost);
		this.shield.update((int) this.xPos, (int) this.yPos);
	}
	
	public void render(Graphics g, int xScreenOrigin, int yScreenOrigin,
			int xScreenPosition, int yScreenPosition, int screenSize) {
		g.setColor(Color.blue);
		if (xPos >= xScreenPosition && xPos <= xScreenPosition + screenSize &&
				yPos >= yScreenPosition && yPos <= yScreenPosition + screenSize) {
			g.drawRect(xScreenOrigin + (int) xPos - xScreenPosition,
					yScreenOrigin + (int) yPos - yScreenPosition,
					scale, scale);
		
			this.shield.render( g, xScreenOrigin, yScreenOrigin,
					xScreenPosition, yScreenPosition, screenSize,
					false); 	
		}
		if (moveTargetXPos >= xScreenPosition && moveTargetXPos <= xScreenPosition + screenSize &&
				moveTargetYPos >= yScreenPosition && moveTargetYPos <= yScreenPosition + screenSize) {
			g.drawRect(xScreenOrigin + (int) moveTargetXPos - xScreenPosition,
					yScreenOrigin + (int) moveTargetYPos - yScreenPosition,
					scale * 5, scale * 5);
		}
		if (hasMouseFocus) {
			g.drawString(Integer.toString(this.health),
					(int) Math.round(this.xPos), (int) Math.round(this.yPos));
		}
	}

}
