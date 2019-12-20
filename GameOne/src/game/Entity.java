package game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import game.interfaces.Movable;

public class Entity implements Movable {
	
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	
	protected int velocity;
	protected int gravity;
	protected int depth = 2;
	
	protected Map<String, BufferedImage[]> animations;
	protected String currentAnimation;
	
	protected boolean isJumping = false;
	protected boolean isWalking = false;
	private int frames = 0;
	public int maxFrames = 10;
	public int currentSprite = 0;
	public int maxSprites = 0;
	
	public Entity(int x, int y, int width, int height, int velocity, String animation,
			BufferedImage[] sprites) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.velocity = velocity;
		addNewAnimation(animation, sprites);
		currentAnimation = animation;
	}
	
	public void update () {
		maxSprites = animations.get(currentAnimation).length;
		frames++;
		if (frames > maxFrames) {
			frames = 0;
			currentSprite++;
			if (currentSprite >= maxSprites) {
				currentSprite = 0;
			}
		}
		
		addVelocity();
		addGravity();
	}
	
	public void addVelocity() {
		this.x += this.velocity;
	}
	
	public void addGravity() {
		this.y += this.gravity;
	}
	
	public void render(Graphics2D g) {
		maxSprites = animations.get(currentAnimation).length;
		if (currentSprite >= maxSprites) {
			currentSprite = 0;
		}
		g.drawImage(obterAnimation(currentAnimation)[currentSprite], this.getX(), this.getY(), null);
	}
	
	public void addNewAnimation(String animation, BufferedImage[] sprites) {
		if (this.animations == null) {
			this.animations = new HashMap<>();
		}
		
		this.animations.put(animation, sprites);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public BufferedImage[] obterAnimation(String animation) {
		return this.animations.get(animation);
	}

	@Override
	public void moveRight() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveLeft() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveUp() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveDown() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveJump() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveStop() {
		velocity = 0;
		gravity = 0;
		isWalking = false;
		currentAnimation = "iddle";
	}
}
