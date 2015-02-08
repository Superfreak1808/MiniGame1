package minigame;

import java.awt.image.BufferedImage;
import java.util.Random;

public class Item 
{
	public int x_cord = 0;
	public int y_cord = 0;
	
	public int movementSpeed = 5;
	
	public int healthValue = 10;
	
	public Sprite sprite;
	
	public Item () {
		Random rand = new Random();
		int rand_y = rand.nextInt((700) + 1);
		int rand_x = rand.nextInt((1000) + 1);
		
		this.x_cord = 1500 + rand_x;
		this.y_cord = rand_y;
		
		sprite = new Sprite(50, 20, "./res/fuel.jpg");
	}
	
	public int getHealthValue() {
		return healthValue;
	}
	
	public BufferedImage getImage() {
		return sprite.getImage();
	}
	
	public int getX() {
		return x_cord;
	}
	
	public int getY() {
		return y_cord;
	}
	
	public void setX() {
		x_cord -= movementSpeed;
	}
	
}
