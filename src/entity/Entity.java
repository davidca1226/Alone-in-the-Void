package entity;

import java.awt.Graphics; //tasfajglkah

import weapons.ammo.Missile;

public interface Entity {

	double getXPos();
	double getYPos();
	int getScale();
	double getDPS();
	int getHealth();
	int getMaxHealth();
	int getXGrid();
	int getYGrid();

	void setXVelocity(double xVelocity);
	double getXVelocity();
	void setYVelocity(double yVelocity);
	double getYVelocity();
	
	double getActualTheta();
	
	void update();

	void render(Graphics g, int xScreenOrigin, int yScreenOrigin,
			int xScreenPosition, int yScreenPosition, int screenSize);

	void setGridLocation(int xIndex, int yIndex);
	void hasMouseFocus(boolean b);

	void dealDamage(int damage);
	void missileLock(Missile missle);
	
	void setTarget(Entity target);
}
