package engine.sprite;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Spritesheet {

	String filePath;
	int dimensions;
	
	BufferedImage image;
	
	Spritesheet(String filePath, int dimensions) {
		this.filePath = filePath;
		this.dimensions = dimensions;
		
		try {
			BufferedImage image = ImageIO.read(new File(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
