package weapons;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import weapons.ammo.Missile;
import engine.grid.GridController;
import entity.Entity;

public class MissileLauncher extends AbstractWeapon implements Weapon {

	private List<Missile> missiles = new ArrayList<Missile>();;
	private Object lock = new Object();
	
	/** (mount, damage, reload units per tick, reload units required to shoot**/
	public MissileLauncher(Entity mount, int damage, int reloadRate,
			int reloadTime) {
		this.name = "Missile";
		abbreviation = "MSL";
		
		this.mount = mount;
		this.damage = damage;
		this.reloadRate = reloadRate;
		this.reloadTime = reloadTime;
		
		this.range = damage * 10;
	}

	@Override
	public void update() {
		timeSinceLastFire++;
		if ((this.reloadProgress >= this.reloadTime) && (this.target != null)
				&& (this.target.getHealth() >= 0)) {
			timeSinceLastFire = 0;
			this.fire();
		}
		this.reloadProgress += this.reloadRate;

		synchronized (this.lock) {
			for (int i = 0; i < this.missiles.size(); i++) {
				if (!this.missiles.get(i).isActive()) {
					this.missiles.remove(i);
				} else {
					this.missiles.get(i).update();
				}
			}
		}
	}

	@Override
	public void render(Graphics g, int xScreenOrigin, int yScreenOrigin,
			int xScreenPosition, int yScreenPosition, int screenSize) {
		synchronized (this.lock) {
			for (int i = 0; i < this.missiles.size(); i++) {
				this.missiles.get(i).render(g, xScreenOrigin, yScreenOrigin, xScreenPosition, yScreenPosition, screenSize);
			}
		}
	}

	@Override
	public void fire() {
		this.reloadProgress = 0;
		synchronized (this.lock) {
			Missile newMissile = new Missile(this.target, this.mount.getXPos(),
					this.mount.getYPos(), this.damage);
			this.missiles.add(newMissile);
			GridController.addEntity(newMissile);
		}
	}
}
