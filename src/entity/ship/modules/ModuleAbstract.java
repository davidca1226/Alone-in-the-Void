package entity.ship.modules;

import java.awt.Graphics;

public abstract class ModuleAbstract implements ModuleInterface{
	
	double xPos, yPos;
	double orientation;
	
	ModuleInterface coreModule;

	public int moduleHealth = 100; //this is out of 100
	
	public void update(double xCorePos, double yCorePos, double angle) {
		doTypeSpecificTasks();
	}
	
	public boolean shouldBeRemoved() {
		if (moduleHealth <= 0) return true;
		else return false;
	}
	
	public void doTypeSpecificTasks() { //OVERIDE THE CRAP OUT OF THIS, DAVID
		
	}
	
	public void dealModuleDamage(int damage) {
		moduleHealth -= damage;
	}
	
	public void render(Graphics g, int xScreenOrigin, int yScreenOrigin,
			int xScreenPosition, int yScreenPosition, int screenSize) {
		
	}
	
}
