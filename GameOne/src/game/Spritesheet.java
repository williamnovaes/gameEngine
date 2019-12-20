package game;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Spritesheet {
	
	private BufferedImage spritesheet;
	
	public Spritesheet(String path) {
		try {
			spritesheet = ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public BufferedImage getSprite(int x, int y, int width, int height) {
		return spritesheet.getSubimage(x, y, width, height);
	}
	
	public BufferedImage[] getSpritesAnimation(Integer arraySize) {
		try {
			BufferedImage[] array = new BufferedImage[arraySize];
			for (int i = 0; i < array.length; i++) {
				array[i] = getSprite(32*i, 0, 32, 32);
			}
			return array;
		} catch (Exception e) {
			return null;
		}
	}
}
