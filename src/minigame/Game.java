package minigame;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable
{

	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	public static final String NAME = "Mini Game 1";
	
	private JFrame frame;
	
	public boolean running = false;
	
	public int tCount = 0;
	
	private BufferedImage background = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	
	private int playerx = 50;
	
	private int playery = 200;
	
	private double velocity = 1.0;
	private int velocity_counter = 1;

	
	private int[] pixels = ((DataBufferInt) background.getRaster().getDataBuffer()).getData();
	
	public Input input;
	
	public Sprite sprite = new Sprite();
	
	
	private int releaseEffect = 20;
	private String lastKeyDown = "";
	
	public Game()
	{
		setMinimumSize(new Dimension(WIDTH, HEIGHT));	
		setMaximumSize(new Dimension(WIDTH, HEIGHT));	
		setPreferredSize(new Dimension(WIDTH, HEIGHT));	
		
		frame = new JFrame(NAME);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		frame.add(this, BorderLayout.CENTER);
		frame.pack();
		
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		init();
		
	}
	
	public void init()
	{
		input = new Input(this);
		sprite.init();
	}
	
	public synchronized void start() 
	{
		running = true;
		new Thread(this).start();
		
	}
		
	
	public synchronized void stop() 
	{
		running = false;
		
	}

	public void run() 
	{
		long lastTime = System.nanoTime();
		double NanoTick = 1000000000D/60D;
		
		int ticks = 0;
		int frames = 0;
		long lastTimer = System.currentTimeMillis();
		double delta = 0;
		
		while(running)
		{
			long now = System.nanoTime();
			delta += (now - lastTime) / (NanoTick);
			lastTime = now;
			boolean nowRender = true;
			while(delta >= 1)
			{
				ticks++;
				tick();
				delta -= 1;
				nowRender = true;
			}
			//limit frames
			try {
					Thread.sleep(2);
				} 
			catch (InterruptedException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if(nowRender)
			{
				frames++;
				render();
			}
			frames++;
			render();
			
			if(System.currentTimeMillis() - lastTimer >= 1000)
			{
				lastTimer +=1000;
				frames = 0;
				ticks = 0;
			}
		}
		
	}
	
	public void velocityIncrease (int power)
	{
		 for(int i=0;i<power;i++)
		 {
			velocity += 0.2;
		 }
	}
	
	public void tick()
	{
		tCount++;
		Boolean isKeyDown = false;
		if(velocity_counter <= 12) 
		{
			velocityIncrease(velocity_counter);
		}
		else {
			velocity_counter--;
		}
		
		
		if(input.up.isPressed())
		{
			playery -= 1 + velocity;
			velocity_counter++;
			isKeyDown = true;
			lastKeyDown = "up";
			releaseEffect = 20;
		}
		if(input.down.isPressed())
		{
			playery += 1 + velocity;
			velocity_counter++;
			isKeyDown = true;
			lastKeyDown = "down";
			releaseEffect = 20;
		}
		if(input.left.isPressed())
		{
			playerx -= 1 + velocity;
			velocity_counter++;
			isKeyDown = true;
			lastKeyDown = "left";
			releaseEffect = 20;
		}
		
		if(input.right.isPressed())
		{
			playerx += 1 + velocity;
			velocity_counter++;
			isKeyDown = true;
			lastKeyDown = "right";
			releaseEffect = 20;
		}
		if(!isKeyDown) 
		{
			velocity_counter = 1;
			velocity = 1;
			
			
			if(releaseEffect > 0 && lastKeyDown != "") {
				switch(lastKeyDown) {
					case "up": 
						playery -= releaseEffect;
						break;
					case "left": 
						playerx -= releaseEffect;
						break;
					case "down":
						playery += releaseEffect;
						break;
					case "right":
						playerx += releaseEffect;
						break;
				}
				releaseEffect--;
				System.out.println(releaseEffect);
			}
		}
		
		
		
	}
	
	public void render()
	{
		BufferStrategy bs = getBufferStrategy();
		if(bs == null)
		{
			createBufferStrategy(3); //triple buffering
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		g.drawImage(background, 0, 0, getWidth(), getHeight(), null); 
		g.drawImage(sprite.getImage(), playerx, playery, 50, 50, null);
		
		g.dispose();
		bs.show(); 
	}

	public static void main(String[] args) 
	{
		new Game().start();
	}
}


