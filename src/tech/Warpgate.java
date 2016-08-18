package tech;

import java.awt.Color;
import java.awt.Graphics;

import engine.particle.ParticleController;
import entity.Entity;

public class Warpgate {
	
	private boolean active = false;
	private Entity mount;
	
	public Warpgate(Entity mount) {
		this.mount = mount;
	}
	
	public void update() {
		if (this.active = true) {
			ParticleController.createParticleField(mount.getXPos(), mount.getYPos(), 1, 60,
					4, 0, 180, Color.blue, true);
		}
	}
	
	public void activate() {
		this.active = true;
	}

}
