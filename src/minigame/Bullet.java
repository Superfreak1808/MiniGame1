package minigame;

import java.awt.image.BufferedImage;
import java.util.Random;

public class Bullet
{
	public int x_cord = 0;
	public int y_cord = 0;
	
	public int movementSpeed = 4;
	
	public int stopSpeed = 0;
	
	public int hitValue = 10;
	
	public Sprite sprite;
	
	public Bullet (int x, int y) 
	{
	
		this.x_cord = x;
		this.y_cord = y;
		
		sprite = new Sprite(5, 5, "./res/bullet.jpg");
	}
	
	public int getHealthValue() 
	{
		return hitValue;
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
	
	public void setX() 
	{
		x_cord += movementSpeed;
	}
}