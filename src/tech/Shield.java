package tech;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class Shield {

	double xOrigin;
	double yOrigin;
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

		this.xOrigin = xOrigin;
		this.yOrigin = yOrigin;

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
	public void render(Graphics g, boolean renderAll, int xPos, int yPos) {
		g.setColor(this.color);
		if (renderAll) {
			// renders the shield percentage bar
			g.drawRect((int) (this.xOrigin - (this.shieldScale * this.radius
					* .5 * this.strength)) + xPos,
					(int) (this.yOrigin + (this.radius * .8)) + yPos,
					(int) (this.shieldScale * this.radius * this.strength),
					(int) (this.radius * .1));
		}

		if (!(this.strength < .1)) {
		for (int i = 0; i < 16; i++) {
			g.drawArc((int) Math.round(this.xOrigin - (this.radius)) + xPos,
					(int) Math.round(this.yOrigin - (this.radius)) +yPos,
					2 * this.radius, 2 * this.radius,
					(int) Math.round((i * (90/4)) + 45 / 4 - (45 * this.strength / 4)),
					(int) Math.round(((45 * this.strength))*2 / 4));
		}
		}
	}

	public void addShield() {

	}

}
