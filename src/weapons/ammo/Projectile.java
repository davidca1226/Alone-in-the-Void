package weapons.ammo;

import entity.Entity;

public interface Projectile  extends Entity { //Yo david, this is a big deal. ALL PROJECTILES MUST HAVE ALL THE ENTITY STUFF
	public double getXPos();                 //AND BE ADDED TO GRID
	public double getYPos();
	public double getTheta();
	public double getSpeed();
}
