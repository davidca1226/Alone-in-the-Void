package entity.station;

import java.awt.Color;
import java.awt.Graphics;

import tech.Shield;
import weapons.Laser;
import weapons.Weapon;
import weapons.ammo.Missile;
import engine.Game;
import engine.controller.MainController;
import entity.Entity;

public class Station implements Entity {
	
	private int[][] layout = new int[][] { //0 =empty space, 1 = weapon, 2 = module. 
			{0,0,1,1,1,0,0},
			{0,1,2,2,2,1,0},
			{1,2,2,2,2,2,1},
			{1,2,2,2,2,2,1},
			{1,2,2,2,2,2,1},
			{0,1,2,2,2,1,0},
			{0,0,1,1,1,0,0}
	};

	private double xPos; // center
	private double yPos; // center
	private int xIndex;
	private int yIndex;
	int stationRadius = 20; // number itself should always be an int
	private int radius = 10;
	private int weaponEffectiveTimer = 0;

	private Shield shield;

	private Weapon[] weapons;
	// Stats
	int shieldRadius = 30;
	int shieldRate = 199;
	int maxShield = 10000;
	double shieldBoost = 1;

	Color color = Color.decode("#121212");

	int health = 1000;
	int maxHealth = 1000;

	public Station(int xPos, int yPos) {
		this.shield = new Shield(this.shieldRadius, this.shieldRate,
				this.maxShield);
		this.xPos = xPos;
		this.yPos = yPos;
		
		
		int weaponAmount = 0;
		int moduleAmount = 0;
		for (int x = 0; x < layout.length; x++) {
			for (int y = 0; y < layout[x].length; y++) {
				if (layout[x][y] == 1)
					weaponAmount++;
				if (layout[x][y] == 2)
					moduleAmount++;
			}
			
		}
		weapons = new Weapon[weaponAmount];

		
	}

	public void updateWeapons() {
		weaponEffectiveTimer++;
		
		for (int i = 0; i < weapons.length; i++) {
			if(weapons[i] != null) {	
				weapons[i].update();
			
				if (weapons[i].targetDestroyed())
					weapons[i].setTarget(MainController.getNearestEntity((int) xPos, (int) yPos));
				if (weaponEffectiveTimer == Game.getTPS())
					weaponEffectiveTimer = 0;
					if (!weapons[i].verifyEffectiveness())
						weapons[i].setTarget(MainController.getNearestEntity((int) xPos, (int) yPos));
			}
		}
	}

	public void update() {
		System.out.println(xPos);
		this.shield.recharge(this.shieldBoost);
		this.shield.update((int) this.xPos, (int) this.yPos);
		
		updateWeapons();
		
		for (int i = 0; i < weapons.length; i++) {
			
		}
	}

	public void render(Graphics g, int xScreenOrigin, int yScreenOrigin,
			int xScreenPosition, int yScreenPosition, int screenSize) {
		for (int i = 0; i < weapons.length; i++) {
			if(weapons[i] != null) 
				weapons[i].render(g, xScreenOrigin, yScreenOrigin, xScreenPosition, yScreenPosition, screenSize);
		}
		if (xPos >= xScreenPosition && xPos <= xScreenPosition + screenSize &&
				yPos >= yScreenPosition && yPos <= yScreenPosition + screenSize) {
			g.setColor(this.color);
			g.fillOval(xScreenOrigin + (int) xPos - xScreenPosition,
					yScreenOrigin + (int) yPos - yScreenPosition,
					2 * this.radius, 2 * this.radius);
		}
		//}
		/*
		for (int i = 0; i < weapons.length; i++) {
			weapons[i].render(g);
		}
		*/
//		this.shield.render(g, true, xScreenStart, yScreenStart);
		
	}
	
	public void addWeapon(Weapon weapon, int pos) {
		weapons[pos] = weapon;
	}

	public double getXPos() {
		return this.xPos;
	}

	public double getYPos() {
		return this.yPos;
	}

	public int getRadius() {
		return this.radius;
	}

	public Color getColor() {
		return this.color;
	}

	public int getHealth() {
		return this.health;
	}

	public int getScale() {
		return 2 * this.radius;
	}

	public void dealDamage(int damage) {
		int remainingDamage = this.shield.damage(damage);
		this.health -= remainingDamage;
	}

	public void missileLock(Missile missile) {

	}

	public int getMaxHealth() {
		return 0;
	}

	public double getDPS() {
		return 0;
	}

	public void setGridLocation(int xIndex, int yIndex) {
		this.xIndex = xIndex;
		this.yIndex = yIndex;
	}

	public int getXGrid() {
		return this.xIndex;
	}

	public int getYGrid() {
		return this.yIndex;
	}

	public int[][] getLayout() {
		return layout;
	}
	
	public int getWeaponAmount() {
		return (weapons.length + 1);
	}
	
	public Weapon[] getWeapons() {
		return weapons;
	}
	
	public void hasMouseFocus(boolean b) {
		// TODO Auto-generated method stub
		
	}
	
	public void setMoveTarget(Entity target) {};

}
