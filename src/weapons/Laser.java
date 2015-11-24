package weapons;

import java.awt.Color;
import java.awt.Graphics;

import engine.Utility;
import entity.Entity;

public class Laser extends AbstractWeapon implements Weapon {

	private int damage;
	private double range;

	private int reloadProgress = 0;
	private boolean firing;
	/** (mount, damange/tick, reload unites per tick, reload units required to shoot **/
	public Laser(Entity mount, int damage, int reloadRate, int reloadTime) {
		name = "Laser";
		abbreviation = "LSR";
		
		this.mount = mount;
		this.reloadTime = reloadTime;
		this.damage = damage;
		this.reloadRate = reloadRate;
		this.range = reloadTime;
	}
	
	public String getAbbreviation() {
		return "LSR";
	}

	@Override
	public void update() {
		timeSinceLastFire++;
		if (this.firing) {
			this.fire();
			timeSinceLastFire = 0;
			return;
		}

		if (this.reloadProgress < this.reloadTime) {
			this.reloadProgress += this.reloadRate;
		}
		if (this.target == null)
			return;
		if (Utility.getDistance(this.mount.getXPos(), this.mount.getYPos(),
				this.target.getXPos(), this.target.getYPos()) < this.range)
			if (this.reloadProgress >= this.reloadTime) {
				this.firing = true;
			}
	}

	@Override
	public void fire() {
		if (this.target.getHealth() <= 0) {
			this.firing = false;
		}
		if (Utility.getDistance(this.mount.getXPos(), this.mount.getYPos(),
				this.target.getXPos(), this.target.getYPos()) > this.range) {
			this.firing = false;
		}
		this.target.dealDamage(this.damage);
		this.reloadProgress--;
		if (this.target.getHealth() <= 0) {
			this.firing = false;
		}
		if (this.reloadProgress <= 0) {
			this.firing = false;
		}
	}

	@Override
	public void render(Graphics g, int xScreenOrigin, int yScreenOrigin,
			int xScreenPosition, int yScreenPosition, int screenSize) {
		if (this.target == null)
			return;
		g.setColor(Color.BLUE);
		g.drawOval((int) (this.mount.getXPos() - this.range) + xScreenOrigin,
				(int) (this.mount.getYPos() - this.range) + yScreenOrigin,
				(int) (2 * this.range), (int) (2 * this.range));
		if (!this.firing)
			return;
		if (this.target.getHealth() <= 0)
			return;
		g.drawLine((int) this.mount.getXPos() + (this.mount.getScale() / 2) + xScreenOrigin,
				(int) this.mount.getYPos() + (this.mount.getScale() / 2) + yScreenOrigin,
				(int) this.target.getXPos() + (this.target.getScale() / 2) + xScreenOrigin,
				(int) this.target.getYPos() + (this.target.getScale() / 2) + yScreenOrigin);

	}

}
