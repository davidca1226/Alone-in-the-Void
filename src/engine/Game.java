package engine;

import javax.swing.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JFrame;

import engine.controller.MainController;
import engine.grid.GridController;
import engine.gui.GuiController;
import engine.gui.MenuController;
import engine.input.Mouse;
import entity.station.Station;


public class Game extends JPanel implements Runnable{
	private static final long serialVersionUID = 1L; 
	public static final boolean debug = false; //add support for debug mode?
	public static final int tps = 60;

	public static int width = 900;
	public static int height = 600;
	public static int scale = 1;

	GuiController gui;

	public static String title = "Alone in the Void";

	private Thread thread;
	private JFrame frame;
	private boolean running = false;

	private MainController mainController;

	private Station station;

	private final double startCredits = 10;
	private final double interestAmount = 1.2;
	private final int interestPeriod = tps * 60;

	private boolean[] keys = new boolean[120];
	private boolean up, down, left, right;
	private boolean[] mouseButtons = new boolean[4];
	private int mouseXPos, mouseYPos;
	private boolean mouseClicked;


	public Game() {
		
		GridController.init();
	
		station = new Station(GridController.getMapSize() * GridController.getGridSize() / 2,
				GridController.getMapSize() * GridController.getGridSize() / 2);
		
		mainController = new MainController(station);
		
		Economy.Init(startCredits, interestAmount, interestPeriod);
		
		gui = new GuiController();
		gui.init(station, width, height);
		
		
		frame = new JFrame();

		frame.setSize(width, height);
		
		addMouseListener(new MouseAdapter() 
		{ 
			public void mousePressed(MouseEvent e) { 
				mouseButtons[e.getButton()] = true;
				if (e.getButton() == 1)
					mouseClicked = true;
			}
			
			public void mouseReleased(MouseEvent e) {
				mouseButtons[e.getButton()] = false;
			}
		}
		); 
		
		addMouseMotionListener(new MouseMotionAdapter()
		{
			public void mouseMoved(MouseEvent e) {
				mouseXPos = e.getX();
				mouseYPos = e.getY();
			}
			
			public void mouseDragged(MouseEvent e) {
				mouseXPos = e.getX();
				mouseYPos = e.getY();
			}
		}
				);
		
		frame.addKeyListener(new KeyListener() {

			public void keyPressed(KeyEvent e) {
				keys[e.getKeyCode()] = true;
				
			}

			public void keyReleased(KeyEvent e) {
				keys[e.getKeyCode()] = false;
				
			}

			public void keyTyped(KeyEvent e) {
				
			}
        });
	}

	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Display");
		System.out.println("You can play games.");
		thread.start();
	}

	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		double ns = 1000000000.0 / tps;
		double delta = 0;

		int frames = 0;
		int updates = 0;

		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();

		while (running) {
			long now = System.nanoTime();

			delta += (now - lastTime) / ns;
			lastTime = now;

			while(delta >= 1) {
				update();
				updates++;
				delta--;
			}
			frame.getContentPane().repaint();
			frames++;

			if(System.currentTimeMillis() - timer >= 1000) {
				timer += 1000;
				frame.setTitle(title + "  |  " + updates + " ups, " + frames + " fps");
				frames = 0;
				updates = 0;
			}
		}

		stop();
	}

	private void update() {

		Economy.update();
		
		mainController.update();
		GridController.updateAll();
		gui.update();
		MenuController.update();
		mainController.updateStation(station);
		
		up = (keys[87] || keys[38]);
		down = (keys[83] || keys[40]);
		left = (keys[65] || keys[37]);
		right = (keys[68] || keys[39]);

		if (up) gui.moveSpaceView(0,1);
		if (down) gui.moveSpaceView(0,-1);
		if (left) gui.moveSpaceView(1,0);
		if (right) gui.moveSpaceView(-1,0);
		
		station.update();
		
		Mouse.updateMouse(mouseXPos, mouseYPos, mouseButtons);
	}

	public void paintComponent(Graphics g) {

		super.paintComponent(g);  // paint background
		setBackground(Color.BLACK);
		
		Economy.render(g);
		gui.render(g);
		
		MenuController.render(g);
		
		g.setColor(Color.orange); 
		if (mouseButtons[1]) g.setColor(Color.blue);
		
		g.drawLine(0, mouseYPos, width, mouseYPos);
		g.drawLine(mouseXPos, 0, mouseXPos, height);
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.frame.setContentPane(game);
		game.frame.setResizable(false);
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);            // "this" JFrame packs its components
		game.frame.setLocationRelativeTo(null); // center the application window
		game.frame.setVisible(true); 
		game.frame.setFocusable(true);
		game.frame.requestFocus();

		game.start();    
	} 
	
	public static int getTPS() {
		return tps;
	}

}