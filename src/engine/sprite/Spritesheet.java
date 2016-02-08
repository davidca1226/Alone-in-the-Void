package engine.sprite;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Spritesheet {

	String filePath;
	int dimensions;
	
	Sprite[][] sprites;
	
	BufferedImage image;
	
	Spritesheet(String filePath, int dimensions) {
		this.filePath = filePath;
		this.dimensions = dimensions;
		
		try {
			BufferedImage image = ImageIO.read(new File(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		if (image == null) System.out.println("asd");
		
		sprites = new Sprite
				[(image.getWidth() / dimensions) - 1]
				[(image.getHeight() / dimensions) - 1];
		
		for (int x = 0; x < sprites.length / dimensions; x++)
			for (int y = 0; y < sprites[0].length; y++)
				sprites[x][y] =
				new Sprite(image.getSubimage(x * image.getWidth(), y * image.getHeight(),
						dimensions, dimensions));
	}

}
