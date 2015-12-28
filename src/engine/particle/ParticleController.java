package engine.particle;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class ParticleController {

		public static ArrayList<Particle> particles = new ArrayList<Particle>();
		
		

		
		
		
		public static void createNewParticle(double xPos, double yPos, int size, int duration,
			double movementSpeed, double movementDirection, Color color, Boolean darken) {
			
			Particle newParticle = new Particle(xPos, yPos, size, duration, movementSpeed, movementDirection, color, darken);
			particles.add(newParticle);
			return;
			
		}
		
		public static void update() {
			for (int i = 0; i < particles.size(); i++) {
				particles.get(i).update();
				if (particles.get(i).shouldBeRemoved())
					particles.remove(i);
			}
		}
		
		public static void createParticleField(double xPos, double yPos, int particleAmount, int duration, //inaccuracy is per side, so 15 degrees
				double movementMaxSpeed, double movementBaseAngle, double movementAngleInaccuracy, Color color, boolean darken ) { //is 30 potential degrees total
			int amountSQRT = (int) Math.sqrt(particleAmount);
			if (movementMaxSpeed == 0) {
				for (int  i = 0; i < particleAmount; i++) {
					createNewParticle(xPos - amountSQRT + (2 * amountSQRT * Math.random()),
							yPos - amountSQRT + (2 * amountSQRT * Math.random()),
							1, duration, 0, 0, color, darken); //last two zeros are movement and angle
				}
				return; 
			}
			
			for (int  i = 0; i < particleAmount; i++) {
				createNewParticle(xPos - amountSQRT + (2 * amountSQRT * Math.random()),
						yPos - amountSQRT + (2 * amountSQRT * Math.random()),
						1, duration,
						Math.random() * movementMaxSpeed,
						movementBaseAngle - movementAngleInaccuracy + (Math.random() * movementAngleInaccuracy * 2),//last two zeros are movement and angle
						color, darken); 
			}
			
			
			
		}
	
		public static ArrayList<Particle> getParticles() {
			
			return particles;
			
		}
}
