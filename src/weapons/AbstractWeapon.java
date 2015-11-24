package weapons;

import engine.Game;
import entity.Entity;

public abstract class AbstractWeapon {

	protected String name;
	protected String abbreviation;
	
	protected boolean fired = false;
	protected boolean targetValueReturned = true;
	protected int reloadProgress = 0;
	protected double latestTargetValue = 0;
	protected int timeSinceLastFire = 0;

	protected Entity mount;
	protected Entity target;

	protected int reloadTime;
	protected int reloadRate;
	protected int damage;
	protected double range;

	public void setTarget(Entity t) {
		if(t == null)
			return;
		
		this.target = t;

		this.targetValueReturned = false;
		this.latestTargetValue = (this.target.getMaxHealth() * this.target.getDPS()) / 1000;
	}

	public boolean verifyEffectiveness() {
		if (timeSinceLastFire > (reloadProgress/reloadRate) + (Game.getTPS() / 2))
				return false;
		return true;
	}
	
	
	
	public boolean targetDestroyed() {
		if (this.target == null)
			return true;
		if (this.target.getHealth() <= 0)
			return true;
		else
			return false;
	}

	public boolean isLoaded() {
		if (this.reloadProgress > this.reloadTime)
			return true;
		else
			return false;
	}

	public double getRange() {
		return this.range;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getAbbreviation() {
		return abbreviation;
	}

	/** nope */
	public double getTargetValue() {
		if (this.targetValueReturned)
			return 0;
		else {
			this.targetValueReturned = true;
		}
		return this.latestTargetValue;
	}

}
