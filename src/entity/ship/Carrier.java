package entity.ship;

import java.awt.Color;
import java.awt.Graphics;

import tech.Shield;
import engine.Game;
import engine.Utility;
import engine.controller.MainController;
import entity.Entity;

public class Carrier extends Ship implements Entity{
	
	private int[][] layout = new int[][] { //0 =empty space, 1 = weapon, 2 = module. 
			{0,1,2,2,2,1,0},
			{2,2,2,2,2,2,2},
			{2,2,2,2,2,2,2},
			{2,2,2,2,2,2,2},
			{0,1,2,2,2,1,0}
	};
	
	private int[] xPoly = new int[] {
			
	};
	
	private int moduleAmount;
	private int weaponAmount;

	public Carrier (int xPos, int yPos) {
		
		this.shieldRadius = 60;
		this.shieldRate = 20;
		this.maxShield = 4000;
		this.shieldBoost = 1;
		this.maxAcceleration = .002;

		this.color = Color.red;

		this.enginePower = 100;
		this.maxSpeed = 1.25;
		this.maxHealth = 1000;
		this.health = this.maxHealth;
		this.maxRotation = 30;

		this.scale = 40;
		this.age = 0;

		this.xPos = xPos;
		this.yPos = yPos;

		this.xLimit = Game.width - (this.scale + 1);
		this.yLimit = Game.height - (this.scale + 1);
	
		moduleAmount = Utility.getLayoutOccurences(layout, 2);
		weaponAmount = Utility.getLayoutOccurences(layout, 1);
		System.out.println(weaponAmount);

		this.shield = new Shield(this.shieldRadius, this.shieldRate,
				this.maxShield);

		this.lock = new Object();
	}
	
	@Override
	public void update() {
		
		this.setMoveTarget(this.target);
		this.move();
		
		if (this.xPos > this.xLimit) {
			this.xPos = 0;
		}
		if (this.yPos > this.yLimit) {
			this.yPos = 0;
		}
		if (this.xPos < 0) {
			this.xPos = this.xLimit;
		}
		if (this.yPos < 0) {
			this.yPos = this.yLimit;
		}
		
		this.shield.recharge(this.shieldBoost);
		this.shield.update((int) this.xPos, (int) yPos);
		
		if (Math.random() < .002) {
			MainController.createNewShip(1, (int) xPos, (int) yPos); //first param is for fighter 
		}
	}

	public void render(Graphics g, int xScreenStart, int yScreenStart, int i, int i2, int i3) {
		g.setColor(this.color);
		
		
		
		g.fillRect(((int) Math.round(xPos) + xScreenStart) - (scale / 2),
				((int) Math.round(yPos) + yScreenStart) - (scale  / 2),
				scale, scale);
		
		this.shield.render(g, true, xScreenStart, yScreenStart); 
	}

}
