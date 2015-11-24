package engine.grid;

import java.util.ArrayList;
import java.util.List;

import weapons.ammo.Projectile;
import engine.Utility;
import entity.Entity;

public class GridTile {

	List<Entity> entities = new ArrayList<Entity>();

	private int index;

	private int xIndex, yIndex, size;

	private Entity bestEntity;
	private double bestDistance;

	GridTile(int xPos, int yPos, int size) {
		this.xIndex = xPos;
		this.yIndex = yPos;
		this.size = size;
	}

	void update() {
		for (int i = 0; i < this.entities.size(); i++) {
			if ((this.entities.get(i).getXPos() > ((this.xIndex + 1) * this.size))
					|| (this.entities.get(i).getXPos() < (this.xIndex * this.size))
					|| (this.entities.get(i).getYPos() > ((this.yIndex + 1) * this.size))
					|| (this.entities.get(i).getYPos() < (this.yIndex * this.size))) {
				GridController.addEntity(this.entities.get(i));
				this.entities.remove(i);
			}
		}
	}

	void addEntity(Entity e) {
		e.setGridLocation(this.xIndex, this.yIndex);
		this.entities.add(e);
	}

	void removeEntity(Entity e) {
		this.entities.remove(e);
	}

	public Entity returnClosest(int targetXPos, int targetYPos) {
		for (this.index = 0; this.index < this.entities.size(); this.index++) {
			if (this.bestEntity == null) {
				this.bestEntity = this.entities.get(this.index);
				this.bestDistance = Utility.getDistance(
						this.entities.get(this.index).getXPos(), this.entities
								.get(this.index).getYPos(), targetXPos,
						targetYPos);
			}

			if (Utility.getDistance(this.entities.get(this.index).getXPos(),
					this.entities.get(this.index).getYPos(), targetXPos,
					targetYPos) < this.bestDistance) {
				this.bestEntity = this.entities.get(this.index);
				this.bestDistance = Utility.getDistance(
						this.entities.get(this.index).getXPos(), this.entities
								.get(this.index).getYPos(), targetXPos,
						targetYPos);
			}

		}
		if (this.entities.size() == 0)
			return null;

		return this.bestEntity;
	}

	int getXIndex() {
		return this.xIndex;
	}

	int getYIndex() {
		return this.yIndex;
	}
}
