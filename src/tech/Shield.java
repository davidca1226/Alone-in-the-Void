package tech;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class Shield {

	double xPos, yPos;
	int radius;
	int rechargeRate;
	int capacity;
	Color color = Color.BLUE;

	int currentCharge;
	int chargePercent;
	double shieldScale = .5;// width of indicator bar in percent of radius
	double strength;

	static List<Integer> shieldXPos = new ArrayList<Integer>();
	static List<Integer> shieldYPos = new ArrayList<Integer>();
	static List<Integer> shieldRadius = new ArrayList<Integer>();

	public Shield(int radius, int rechargeRate, int capacity) {
		this.radius = radius;
		this.rechargeRate = rechargeRate;
		this.capacity = capacity;

		// shieldXPos.add((int) Math.round(xPos));
		// shieldYPos.add((int) Math.round(yPos));
		// shieldRadius.add(radius);

	}

	public void update(int xOrigin, int yOrigin) {

		this.xPos = xOrigin;
		this.yPos = yOrigin;

	}

	public void recharge(double rechargeBoost) {
		this.currentCharge += this.rechargeRate;

		if (this.currentCharge > this.capacity) {
			this.currentCharge = this.capacity;
		}
		this.chargePercent = Math.round((this.currentCharge * 100)
				/ this.capacity);
		this.strength = (double) this.currentCharge / (double) this.capacity;

	}

	public int damage(int damage) {
		this.currentCharge -= damage;
		if (this.currentCharge < 0) {
			this.currentCharge = 0;
		}
		
		if (this.currentCharge < damage)
			return damage - currentCharge;
		else return 0;
	}

	/** boolean is whether to render everything. smaller stuff should be false **/
	public void render(Graphics g, int xScreenOrigin, int yScreenOrigin,
			int xScreenPosition, int yScreenPosition, int screenSize, boolean renderAll) {
		double percentCharge = (double) currentCharge / (double) capacity;
		g.setColor(this.color);
		
		for (int i = 0; i < percentCharge * 30; i++) {
			g.drawArc(xScreenOrigin + (int) xPos - xScreenPosition - radius, 
					yScreenOrigin + (int) yPos - yScreenPosition - radius, 
					radius * 2, radius * 2,
					(int) (Math.random() * 360), (int) (percentCharge * 30));
			//TODO Make this work.
		}
		
		
	}

	public void addShield() {

	}

}
