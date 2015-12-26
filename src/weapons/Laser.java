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
		if (!this.firing)
			return;
		if (this.target.getHealth() <= 0)
			return;
		if (mount.getXPos() >= xScreenPosition && mount.getXPos() <= xScreenPosition + screenSize &&
				mount.getYPos() >= yScreenPosition && mount.getYPos() <= yScreenPosition + screenSize &&
				target.getXPos() >= xScreenPosition && target.getXPos() <= xScreenPosition + screenSize &&
				target.getYPos() >= xScreenPosition && target.getYPos() <= xScreenPosition + screenSize)
		g.drawLine(xScreenOrigin + (int) mount.getXPos() - xScreenPosition - mount.getScale(),
				yScreenOrigin + (int) mount.getYPos() - yScreenPosition - mount.getScale(),
				xScreenOrigin + (int) target.getXPos() - xScreenPosition - target.getScale(),
				yScreenOrigin + (int) target.getYPos() - yScreenPosition - target.getScale());

	}

}
