package engine.sprite;

public class SpriteController {
	final static String[] locations = {"/16x16ShipSprites.png"};
	final static int[] dimensions = {16};
	static Spritesheet[] sheets;
	public static void init() {
		sheets = new Spritesheet[dimensions.length];
		
		for (int i = 0; i < locations.length; i++) {
			sheets[i] = new Spritesheet(locations[i], dimensions[i]);
		}
		
	}
	
}