package minigame;

import java.awt.image.BufferedImage;
import java.util.Random;

public class Enemy
{
	public int x_cord = 0;
	public int y_cord = 0;
	
	public int movementSpeed = 5;
	
	public int stopSpeed = 0;
	
	public int healthValue = 10;
	
	public Sprite sprite;
	
	public int rand_movement;
	
	public Boolean lineofSight;
	
	public Enemy () 
	{
		Random rand = new Random();
		int rand_y = rand.nextInt((700) + 1);
		int rand_x = rand.nextInt((1000) + 1);
		
		//rand_movement = rand.nextInt((5) + 3);
		
		this.x_cord = 1500 + rand_x;
		this.y_cord = rand_y;
		
		lineofSight = false;
		
		sprite = new Sprite(64, 64, "./res/enemy.png");
	}
	
	public int getHealthValue() 
	{
		return healthValue;
	}
	
	public BufferedImage getImage()
	{
		return sprite.getImage();
	}
	
	public int getX() 
	{
		return x_cord;
	}
	
	public int getY() 
	{
		return y_cord;
	}
	
	public Boolean hasSight()
	{
		return lineofSight;
	}
	
	public void setX() 
	{
		x_cord -= movementSpeed;
	}
	
	public void setUp() 
	{
		
		y_cord += 5;
	}
	
	public void setDown() 
	{
		y_cord -= 5;
	}
	
	public void setStop() 
	{
		y_cord -= stopSpeed;
	}
	
	public void setSight(Boolean value)
	{
		lineofSight = value;
	}
	
}
