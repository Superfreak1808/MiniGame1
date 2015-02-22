package minigame;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable
{

	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	public static final String NAME = "Mini Game 1";
	
	private JFrame frame;
	
	public int bulletTimer = 0;
	
	public boolean running = false;
	
	public int tCount = 0;
	
	private BufferedImage background = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	
	private int playerx = 50;
	
	private int playery = 200;
	
	private int difficulty = 20;
	
	private int enemycount = 5;
	
	private double velocity = 1.0;
	private int velocity_counter = 1;

	private Boolean firstTime = true;
	
	public Input input;
	public Player player = new Player();
	
	private int releaseEffect = 20;
	private String lastKeyDown = "";
	
	private Collision collision = new Collision();
	
	public ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	
	public ArrayList<Bullet> enemybullets = new ArrayList<Bullet>();
	
	public ArrayList<Item> items = new ArrayList<Item>();
	
	public ArrayList<Enemy> enemys = new ArrayList<Enemy>();
	
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
				if(items.size() < difficulty) 
				{
					addItem();
				}
				
				if(enemys.size() < enemycount) 
				{
					addEnemy();
				}
				for(int i = 0; i < items.size(); i++) 
				{
					if(collision.testCollision(items.get(i).getX(), items.get(i).getY(), 50, 20, playerx, playery, 50, 50))
					{
						player.addHealth(items.get(i).getHealthValue());
						player.setScore(player.getScore() + 500);
						items.remove(i);
					}
					else if(items.get(i).getX() < 0) 
					{
						items.remove(i);
					}
				}

				for(int i = 0; i < bullets.size(); i++) 
				{
					if(bullets.get(i).getX() > 1280) 
					{
						bullets.remove(i);
					}
						
				}
				
				for(int j = 0; j < enemys.size(); j++) 
				{
					if(enemys.get(j).getX() < 0) 
					{
						enemys.remove(j);
					}
					for(int i = 0; i < bullets.size(); i++) {
						if(collision.testCollision(bullets.get(i).getX(), bullets.get(i).getY(), 5, 5, enemys.get(j).getX(), enemys.get(j).getY(), 64, 64))
						{
							bullets.remove(i);
							enemys.remove(j);
						}
					}
				}
				

				

				if(player.getHealth() <= 0 ) 
				{
					// end game
					running = false;
					delta = -1;
				}
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
		
		
		
		bulletTimer++;
		
		if(tCount % 30 == 0) 
		{
			player.removeHealth(5);
			player.setScore(player.getScore() + 100);
		}
		
		if(tCount % 200 == 0) 
		{
			if(difficulty > 5)
			{
				difficulty --;
			}
		}
		
		
		if (playery <= 0)
		{
			playery = 0;
		}
		
		if (playery >= 670)
		{
			playery = 670;
		}
		
		for(int i =0; i<items.size(); i++) 
		{
			items.get(i).setX();
		}
		
		for(int i =0; i<enemys.size(); i++) 
		{
			enemys.get(i).setX();
			if(enemys.get(i).getY() < playery - 10)
			{
				enemys.get(i).setUp();

				
			}
			else if(enemys.get(i).getY() > playery + 10) 
			{
				enemys.get(i).setDown();
			}	
			//else if(enemys.get(i).getY() == playery )
			else 
			{
				//shoot
			}
		}
		
		for(int i =0; i<bullets.size(); i++) 
		{
			bullets.get(i).setX();
		}
		

		
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
		if(input.space.isPressed())
		{
			if(bulletTimer > 40)
			{
			Bullet bullet = new Bullet(playerx + 50, playery + 20);
			bullets.add(bullet);
			bulletTimer = 0;
			}
		}
		if(!isKeyDown) 
		{
			velocity_counter = 1;
			velocity = 1;
			
			
			if(releaseEffect > 0 && lastKeyDown != "") 
			{
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
				//System.out.println(releaseEffect);
			}
		}
		
		
		
	}
	
	public void addItem() 
	{
		Item item = new Item();
		items.add(item);
	}
	
	public void addEnemy() 
	{
		Enemy enemy = new Enemy();
		enemys.add(enemy);
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
		
		g.drawImage(background, 0, 0, getWidth(), getHeight(), null); 
		g.drawImage(player.getImage(), playerx, playery, 50, 50, null);
		
		for(int i =0; i<items.size(); i++) 
		{
			g.drawImage(items.get(i).getImage(), items.get(i).getX(), items.get(i).getY(), 20, 20, null);
		}
		
		for(int i =0; i<enemys.size(); i++) 
		{
			g.drawImage(enemys.get(i).getImage(), enemys.get(i).getX(), enemys.get(i).getY(), 64, 64, null);
		}
		
		for(int i =0; i<bullets.size(); i++) 
		{
			g.drawImage(bullets.get(i).getImage(), bullets.get(i).getX(), bullets.get(i).getY(), 5, 5, null);
		}
		
		
		g.setColor(Color.RED);
		g.fillRect(getWidth() - (player.getHealth()+20), getHeight() - 30, player.getHealth(), 20);
		
		g.setColor(Color.WHITE);
		g.drawString("Fuel",getWidth() - 50 , getHeight() - 16); 
		
		g.drawString("Score: " + player.getScore(), 0 , getHeight() - 16); 
		
		g.dispose();

		bs.show(); 

	}

	public static void main(String[] args) 
	{
		new Game().start();
	}
}


