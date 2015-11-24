package weapons;

import java.awt.Graphics;

import entity.Entity;

public interface Weapon {

	void update();

	void render(Graphics g, int xScreenOrigin, int yScreenOrigin,
			int xScreenPosition, int yScreenPosition, int screenSize);

	void setTarget(Entity target);

	void fire();

	boolean isLoaded();

	boolean targetDestroyed();
	boolean verifyEffectiveness();

	double getRange();
	double getTargetValue();
	String getName();
	String getAbbreviation();
}
