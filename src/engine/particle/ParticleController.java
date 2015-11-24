package engine.particle;

import java.awt.Graphics;
import java.util.ArrayList;

public class ParticleController {

		public static ArrayList<Particle> particles = new ArrayList<Particle>();
		
		

		
		
		
		public static void createNewParticle(double xPos, double yPos, int size, int duration,
			double movementSpeed, double movementDirection) {
			
			Particle newParticle = new Particle(xPos, yPos, size, duration, movementSpeed, movementDirection);
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
		
		public static void createParticleField(double xPos, double yPos, int particleAmount, //inaccuracy is per side, so 15 degrees
				double movementMaxSpeed, double movementBaseAngle, double movementAngleInaccuracy ) { //is 30 potential degrees total
			int amountSQRT = (int) Math.sqrt(particleAmount);
			if (movementMaxSpeed == 0) {
				for (int  i = 0; i < particleAmount; i++) {
					createNewParticle(xPos - amountSQRT + (2 * amountSQRT * Math.random()),
							yPos - amountSQRT + (2 * amountSQRT * Math.random()),
							1, 120, 0, 0); //last two zeros are movement and angle
				}
				return; 
			}
			
			for (int  i = 0; i < particleAmount; i++) {
				createNewParticle(xPos - amountSQRT + (2 * amountSQRT * Math.random()),
						yPos - amountSQRT + (2 * amountSQRT * Math.random()),
						1, 120,
						Math.random() * movementMaxSpeed,
						movementBaseAngle - movementAngleInaccuracy + (Math.random() * movementAngleInaccuracy * 2)); //last two zeros are movement and angle
			}
			
			
			
		}
	
		public static ArrayList<Particle> getParticles() {
			
			return particles;
			
		}
}
