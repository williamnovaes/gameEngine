package game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	
	//constantes
	private final int WIDTH = 240;
	private final int HEIGHT = 200;
	private final int SCALE = 3;
	private static double fpsLimiter = 60.0;
	private final double ns = 1000000000 / fpsLimiter;
	
	private Thread thread;
	private BufferedImage image;
	public static JFrame frame;
	
	private boolean isRunning = false;
	
	private Spritesheet sheet;
//	private BufferedImage player;
	private BufferedImage[] player;
	private int frames = 0;
	private int maxFrames = 8;
	private int curAnimation = 0;
	
	public Game() {
		sheet = new Spritesheet("/astronauta_iddle_32x32.png");
		//coordenadas do sprite (posicao x e y, e tamanho width height)
//		player = sheet.getSprite(0, 0, 32, 32);
		player = new BufferedImage[5];
		player = getSpritesAnimation(player, sheet);
		this.setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		initFrame();
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	}
	
	public void initFrame() {
		frame = new JFrame("Game One");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public synchronized void start () {
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}
	
	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		frames++;
		if (frames > maxFrames) {
			frames = 0;
			curAnimation++;
			if (curAnimation > player.length-1) {
				curAnimation = 0;
			}
		}
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = image.getGraphics();
		//render background (fillrect - rectangle)
		g.setColor(new Color(255, 255, 255));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		//render circle (fillOval - circle)
//		g.setColor(Color.RED);
//		g.fillOval(0, 0, 20, 20);
		
		
		//rener strings
//		g.setFont(new Font("Arial", Font.BOLD, 40));
//		g.setColor(Color.RED);
//		g.drawString("Olá", 200, 200);
		
		//g.drawLine();
		
		//render sprite
		Graphics2D g2 = (Graphics2D) g;
		//rotate: 1- grau de rotacao em radiano, 2- posicao eixo x onde ocorre a rotacao, 3- posicao eixo y onde ocorre a rotacao
//		g2.rotate(Math.toRadius(45), 90+16, 90+16);
		g2.drawImage(player[curAnimation], 0, 20, null);
		
//		g.drawImage(player, 0, 20, null);
		
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
		bs.show();
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}

	public void run() {
		long lastTick = System.nanoTime();
		double delta = 0;
		long now = 0l;
		int frames = 0;
		long timer = System.currentTimeMillis();
		while (isRunning) {
			now = System.nanoTime();
			delta += (now - lastTick) / ns;
			lastTick = now;
			
			if (delta >= 1) {
				update();
				render();
				delta--;
				frames++;
			}
			
			if (System.currentTimeMillis() - timer >= 1000) {
				System.out.println("FPS: " + frames);
				frames = 0;
				timer += 1000;
			}
			
		}
		
		stop();
	}
	
	public static double getFps() {
		return fpsLimiter;
	}
	
	public static void setFps(double fpsLimiter) {
		Game.fpsLimiter = fpsLimiter;
	}
	
	public BufferedImage[] getSpritesAnimation(BufferedImage[] player, Spritesheet sheet) {
		try {
			for (int i = 0; i < player.length; i++) {
				player[i] = sheet.getSprite(32*i, 0, 32, 32);
			}
			return player;
		} catch (Exception e) {
			return null;
		}
	}

}
