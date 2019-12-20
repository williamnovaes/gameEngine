package game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable, KeyListener {

	private static final long serialVersionUID = 1L;
	
	//constantes
	private final int WIDTH = 240;
	private final int HEIGHT = 200;
	private final int SCALE = 4;
	private static double fpsLimiter = 60.0;
	private final double ns = 1000000000 / fpsLimiter;
	
	private Thread thread;
	private BufferedImage image;
	public static JFrame frame;
	
	public List<Entity> entities;
	
	private boolean isRunning = false;
	
	private Map<Integer,  EMove> inputsMoves;
	
	public Game() {
		addKeyListener(this);
		popularInputsMoves();
		entities = new ArrayList<>();
		//coordenadas do sprite (posicao x e y, e tamanho width height)
//		player = sheet.getSprite(0, 0, 32, 32);
		this.setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		initFrame();
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		
		Player player = new Player(0, 0, 32, 32, 0, "iddle", new Spritesheet("/astronauta_iddle_32x32.png").getSpritesAnimation(5));
		player.addNewAnimation("walk", new Spritesheet("/astronauta_walk_32x32.png").getSpritesAnimation(4));
//		Player player = new Player(0, 0, 32, 32, 0, "walk", new Spritesheet("/astronauta_walk_32x32.png").getSpritesAnimation(4));
//		player.addNewAnimation("iddle", new Spritesheet("/astronauta_iddle_32x32.png").getSpritesAnimation(5));
		entities.add(player);
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
		for (Entity entity : entities) {
			entity.update();
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
		
		Graphics2D g2D = (Graphics2D) g;
		
		for (Entity entity : entities) {
			entity.render(g2D);
		}
		
		//render circle (fillOval - circle)
//		g.setColor(Color.RED);
//		g.fillOval(0, 0, 20, 20);
		
		
		//rener strings
//		g.setFont(new Font("Arial", Font.BOLD, 40));
//		g.setColor(Color.RED);
//		g.drawString("Olá", 200, 200);
		
		//g.drawLine();
		
		//render sprite
		//rotate: 1- grau de rotacao em radiano, 2- posicao eixo x onde ocorre a rotacao, 3- posicao eixo y onde ocorre a rotacao
//		g2.rotate(Math.toRadius(45), 90+16, 90+16);
//		if (isWalking) {
//			g2.rotate(Math.toRadians(0), 16, 16);
//			g2.drawImage(playerWalk[curAnimation], 0, 0, null);
//		} else {
//			g2.drawImage(playerIddle[curAnimation], 0, 20, null);
//			
//		}
		
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
	
	private void popularInputsMoves() {
		this.inputsMoves = new HashMap<>();
		for (EMove move : EMove.values()) {
			this.inputsMoves.put(move.getInput(), move);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		for (Entity entity : entities) {
			EMove move = null;
			move = inputsMoves.get(e.getExtendedKeyCode());
			if (move != null) {
				move.action(entity);
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		for (Entity entity : entities) {
			EMove move = null;
			move = inputsMoves.get(e.getExtendedKeyCode());
			if (move != null) {
				move.actionReverse(entity);
			}
		}
	}
}