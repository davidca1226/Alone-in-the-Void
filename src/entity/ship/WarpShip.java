package entity.ship;

import java.awt.Color;
import java.awt.Graphics;

import tech.Shield;
import tech.Warpdrive;
import tech.Warpgate;
import engine.Game;
import engine.Utility;
import engine.controller.MainController;
import entity.Entity;

public class WarpShip  extends Ship implements Entity{
	
	private int[][] layout = new int[][] { //0 =empty space, 1 = weapon, 2 = module. 
			{0,1,2,2,1,0},
			{2,2,2,2,2,2},
			{2,2,2,2,2,2},
			{0,1,2,2,1,0}
	};
	
	private int moduleAmount;
	private int weaponAmount;
	
	private boolean deployed;

	private boolean setTarget = false;
	
	private Warpgate warpgate;

	public WarpShip(int xPos, int yPos) {
		
		warpdrive = new Warpdrive(10, 300, .01, 80, this);
		warpgate = new Warpgate(this);
		
		this.shieldRadius = 60;
		this.shieldRate = 20;
		this.maxShield = 4000;
		this.shieldBoost = 1;
		this.maxAcceleration = .005;

		this.color = Color.lightGray;

		this.enginePower = 100;
		this.maxSpeed = 1.25;
		this.maxHealth = 1000;
		this.health = this.maxHealth;
		this.maxRotation = 30;
		
		this.desiredTargetDistance = 300;

		this.scale = 3;
		this.age = 0;
		this.deployed = false;

		this.xPos = xPos;
		this.yPos = yPos;
	
		moduleAmount = Utility.getLayoutOccurences(layout, 2);
		weaponAmount = Utility.getLayoutOccurences(layout, 1);

		this.shield = new Shield(this.shieldRadius, this.shieldRate,
				this.maxShield);

		this.lock = new Object();
	}

	public void update() {
		if (target != null && !setTarget) {
			this.targetXPos = target.getXPos() + (Math.random() * 100 - 50);
			this.targetYPos = target.getYPos() + (Math.random() * 100 - 50);
			setTarget = true;
		}
		
		if (deployed) {
		} else {
			if (this.desiredTargetDistance * .98 <= this.totalDist &&
					this.desiredTargetDistance * 1.02 >= this.totalDist) {
				this.deploy();
			} else {
				this.move();
				this.changePos();
			}
		}
		warpgate.update();
		this.shield.recharge(this.shieldBoost);
		this.shield.update((int) this.xPos, (int) yPos);
	}
	
	private void deploy() {
		this.deployed = true;
		warpgate.activate();
	}

	public void render(Graphics g, int xScreenOrigin, int yScreenOrigin,
			int xScreenPosition, int yScreenPosition, int screenSize) {
		g.setColor(this.color);
		
		
		if (xPos >= xScreenPosition && xPos <= xScreenPosition + screenSize &&
				yPos >= yScreenPosition && yPos <= yScreenPosition + screenSize) 
			g.fillOval(xScreenOrigin + (int) xPos - xScreenPosition,
					yScreenOrigin + (int) yPos - yScreenPosition,
					2 * this.scale, 2 * this.scale);
		
	}
	
	public boolean getDeployed() {
		return deployed;
	}
	
	public Warpgate getWarpgate() {
		return warpgate;
	}
}


