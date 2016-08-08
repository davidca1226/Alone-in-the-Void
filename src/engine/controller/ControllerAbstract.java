package engine.controller;

import java.util.ArrayList;
import java.util.List;

import engine.grid.GridController;
import entity.Entity;

public abstract class ControllerAbstract {

	List<Entity> entities = new ArrayList<Entity>();
	
	Entity target;

	public void update() {
		for (int i = 0; i < this.entities.size(); i++) {
			this.entities.get(i).update();
		}
		
		for (int i = 0; i < this.entities.size(); i++) {
			if(this.entities.get(i).getHealth() <= 0)
				this.entities.remove(i);
		}
	
		if (target != null) {
			for (int i = 0; i < this.entities.size(); i++) {
				this.entities.get(i).setTarget(target);
			}
		}
	}

	public void add(Entity e) {
		this.entities.add(e);
	}

	public void remove(Entity e) {
		GridController.removeEntity(e);
		this.entities.remove(e);
	}
	
	public List<Entity> getControlledList() {
		return entities;
	}
	
	public void setTarget(Entity target) {
		this.target = target;
	}

}
