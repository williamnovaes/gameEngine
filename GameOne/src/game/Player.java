package game;

import java.awt.image.BufferedImage;

public class Player extends Entity {
	
	public Player(int x, int y, int width, int height, int velocidade, String animation,
			BufferedImage[] sprites) {
		super(x, y, width, height, velocidade, animation, sprites);
	}
	
	@Override
	public void moveRight() {
		velocity = 1;
		isWalking = true;
		currentAnimation = "walk";
	}
	
	@Override
	public void moveLeft() {
		velocity = -1;
		isWalking = true;
		currentAnimation = "walk";
	}
	
	@Override
	public void moveUp() {
		gravity = -1;
		isWalking = true;
		currentAnimation = "walk";
	}
	
	@Override
	public void moveDown() {
		gravity = 1;
		isWalking = true;
		currentAnimation = "walk";
	}
}
